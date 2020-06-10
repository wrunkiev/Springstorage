package com.controller;

import com.model.File;
import com.model.Storage;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.FileService;
import com.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class TransferController {
    @Autowired
    private FileService fileService;
    @Autowired
    private StorageService storageService;

    @RequestMapping(method = RequestMethod.GET, value = "/saveFileToStorage", produces = "text/plain")
    public void saveFileToStorage(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            File file = fileService.findById(Long.parseLong(req.getParameter("idFile")));
            Storage storage = storageService.findById(Long.parseLong(req.getParameter("idStorage")));
            put(storage, file);
            resp.getWriter().println(resp.getStatus());
        }catch (NoSuchElementException e){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(resp.getStatus());
        }
        catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }
    }

    private void put(Storage storage, File file)throws Exception{
        checkStorage(storage);
        checkFile(file);

        if(!checkFileFormat(storage, file)){
            throw new Exception("Exception in method TransferController.put. Format of File '" +
                    file.getId() +  "' is not support in Storage '" + storage.getId() + "'.");
        }

        if(getRealStorageSize(storage) + file.getSize() > storage.getStorageSize())
            throw new Exception("Exception in method TransferController.put. Size of Storage '" +
                    storage.getId() + "' is more then valid.");

        if(!checkFileId(storage, file))
            throw new Exception("Exception in method TransferController.put. Storage '" +
                    storage.getId() + "' has File with such ID '" + file.getId() + "'.");

        file.setStorage(storage);
        fileService.update(file);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteFileFromStorage", produces = "text/plain")
    public void deleteFileFromStorage(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            File file = fileService.findById(Long.parseLong(req.getParameter("idFile")));
            Storage storage = storageService.findById(Long.parseLong(req.getParameter("idStorage")));
            delete(storage, file);
            resp.getWriter().println(resp.getStatus());
        }catch (NoSuchElementException e){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(resp.getStatus());
        }
        catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }
    }

    private void delete(Storage storage, File file) throws Exception{
        checkStorage(storage);
        checkFile(file);

        if(checkFileId(storage, file))
            throw new Exception("Exception in method TransferController.delete. Storage '" +
                    storage.getId() + "' does not have File '" + file.getId() + "'.");

        file.setStorage(null);
        fileService.update(file);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transferAllFiles", produces = "text/plain")
    public void transferAllFiles(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            Storage storageFrom = storageService.findById(Long.parseLong(req.getParameter("idStorageFrom")));
            Storage storageTo = storageService.findById(Long.parseLong(req.getParameter("idStorageTo")));
            transferAll(storageFrom, storageTo);
            resp.getWriter().println(resp.getStatus());
        }catch (NoSuchElementException e){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(resp.getStatus());
        }
        catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }
    }

    private void transferAll(Storage storageFrom, Storage storageTo) throws Exception{
        checkStorage(storageFrom);
        checkStorage(storageTo);

        long sizeStorageTo = getRealStorageSize(storageTo);
        List<File> filesFrom = storageFrom.getFiles();

        if(filesFrom == null)
            throw new Exception("Exception in method TransferController.transferAll. StorageFrom "
                    + storageFrom.getId() + "can't be empty.");

        for (File file : filesFrom) {

            if (!checkFileFormat(storageTo, file))
                throw new Exception("Format of File '" + file.getId() +
                        "' is not support in Storage '" + storageTo.getId() + "'.");

            sizeStorageTo += file.getSize();
        }

        if(sizeStorageTo > storageTo.getStorageSize())
            throw new Exception("Size of Storage '" + storageTo.getId() + "' is more then valid.");

        for (File file : filesFrom) {
            if (file != null) {
                file.setStorage(storageTo);
                fileService.update(file);
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transferFileFromToStorage", produces = "text/plain")
    public void transferFileFromToStorage(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        try{
            Storage storageFrom = storageService.findById(Long.parseLong(req.getParameter("idStorageFrom")));
            Storage storageTo = storageService.findById(Long.parseLong(req.getParameter("idStorageTo")));
            long idFile = Long.parseLong(req.getParameter("idFile"));
            transferFile(storageFrom, storageTo, idFile);
            resp.getWriter().println(resp.getStatus());
        }catch (NoSuchElementException e){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(resp.getStatus());
        }
        catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }
    }

    private void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception{
        checkStorage(storageFrom);
        checkStorage(storageTo);
        File file = fileService.findById(id);
        checkFile(file);

        if(checkFileId(storageFrom, file)){
            throw new Exception("Exception in method TransferController.transferFile. File :" +
                    file.getId() + " didn't find in storage " + storageFrom.getId());
        }

        put(storageTo, file);
    }

    private void checkStorage(Storage storage)throws Exception{
        if(storage == null)
            throw new NullPointerException("Exception in method TransferController.checkStorageNull. Storage can't null.");
    }

    private void checkFile(File file)throws Exception{
        if(file == null)
            throw new NullPointerException("Exception in method TransferController.checkFileNull. File can't null.");
    }

    private boolean checkFileFormat(Storage storage, File file){
        String[] storageFormat = storage.getFormatsSupported().split(",");
        for(String format : storageFormat){
            if(file != null && file.getFormat().equals(format)){
                return true;
            }
        }
        return false;
    }

    private long getRealStorageSize(Storage storage){
        long size = 0;
        for(File element : storage.getFiles()){
            if(element != null){
                size += element.getSize();
            }
        }
        return size;
    }

    private boolean checkFileId(Storage storage, File file)throws Exception{
        List <File> files = storageService.getFiles(storage.getId());
        for(File element : files){
            if(element != null && file != null && file.getId() == element.getId()) {
                return false;
            }
        }
        return true;
    }
}