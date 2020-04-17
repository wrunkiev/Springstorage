package com.controller;

import com.model.File;
import com.model.Storage;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.FileService;
import com.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;


public class TransferController {
    //@Autowired
    private FileService fileService = new FileService();
    //@Autowired
    private StorageService storageService = new StorageService();

    public void put(Storage storage, File file)throws Exception{
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

    public void delete(Storage storage, File file) throws Exception{
        checkStorage(storage);
        checkFile(file);

        if(checkFileId(storage, file))
            throw new Exception("Exception in method TransferController.delete. Storage '" +
                    storage.getId() + "' does not have File '" + file.getId() + "'.");

        file.setStorage(null);
        fileService.update(file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception{
        /*checkStorageFromNull(storageFrom);
        checkStorageToNull(storageTo);

        long sizeStorageTo = getRealStorageSize(storageTo);

        if(storageFrom.getFiles() != null){
            for(int i = 0; i < storageFrom.getFiles().size(); i++){

                if(!checkFileFormat(storageTo, storageFrom.getFiles().get(i)))
                    throw new Exception("Format of File '" + storageFrom.getFiles().get(i).getId() +
                            "' is not support in Storage '" + storageTo.getId() + "'.");

                if(!checkFileId(storageTo, storageFrom.getFiles().get(i)))
                    throw new Exception("Storage '" + storageTo.getId() + "' has file with such ID: " +
                            storageFrom.getFiles().get(i).getId());

                sizeStorageTo += storageFrom.getFiles().get(i).getSize();
            }
        }


        if(sizeStorageTo > storageTo.getStorageSize())
            throw new Exception("Size of Storage '" + storageTo.getId() + "' is more then valid.");

        int indexFrom = 0;
        for(File element : storageFrom.getFiles()){
            if(element != null)
                indexFrom++;
        }

        int indexTo = 0;
        for(File element : storageTo.getFiles()){
            if(element == null)
                indexTo++;
        }

        if(indexFrom > indexTo)
            throw new Exception("Count elements in Storage '" + storageTo.getId() + "' are more then valid." );

        for(int i = 0; i < storageFrom.getFiles().length; i++){
            for(int j = 0; j < storageTo.getFiles().length; j++){
                if(storageFrom.getFiles()[i] != null){
                    if(storageTo.getFiles()[j] == null && !storageFrom.getFiles()[i].equals(storageTo.getFiles()[j])){
                        storageTo.getFiles()[j] = storageFrom.getFiles()[i];
                        storageFrom.getFiles()[i] = null;
                    }
                }
            }
        }*/
    }













    private void checkStorage(Storage storage)throws Exception{
        if(storage == null)
            throw new NullPointerException("Exception in method TransferController.checkStorageNull. Storage can't null.");

        Storage s = storageService.findById(storage.getId());
        if(s == null)
            throw new Exception("Exception in method TransferController.put. This storage + " +
                    storage.getId() +" is not defined inDB");
    }



    private void checkFile(File file)throws Exception{
        if(file == null)
            throw new NullPointerException("Exception in method TransferController.checkFileNull. File can't null.");

        File f = fileService.findById(file.getId());
        if(f == null)
            throw new Exception("Exception in method TransferController.put. This file + " +
                    file.getId() +" is not defined inDB");
    }

    private boolean checkFileFormat(Storage storage, File file){
        Storage s = storageService.findById(storage.getId());
        File f = fileService.findById(file.getId());
        String[] storageFormat = s.getFormatsSupported().split(",");
        for(String format : storageFormat){
            if(f != null && f.getFormat().equals(format)){
                return true;
            }
        }
        return false;
    }

    private long getRealStorageSize(Storage storage){
        Storage s = storageService.findById(storage.getId());
        long size = 0;
        for(File element : s.getFiles()){
            if(element != null){
                size += element.getSize();
            }
        }
        return size;
    }

    private boolean checkFileId(Storage storage, File file){
        for(File element : storage.getFiles()){
            if(element != null && file != null && file.getId() == element.getId()) {
                    return false;
            }
        }
        return true;
    }

    private void checkStorageFromNull(Storage storage){
        if(storage == null)
            throw new NullPointerException("Exception in method TransferController.checkStorageFromNull. StorageFrom is null.");
    }

    private void checkStorageToNull(Storage storage){
        if(storage == null)
            throw new NullPointerException("Exception in method TransferController.checkStorageToNull. StorageTo is null.");
    }
}
