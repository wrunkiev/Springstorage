package com.service;

import com.DAO.FileDAO;
import com.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FileService {
    @Autowired
    private FileDAO fileDAO;

    public File save(File file)throws Exception{
        return fileDAO.save(file);
    }

    public File findById(long id)throws NoSuchElementException {
        if(fileDAO.findById(id) == null){
            throw new NoSuchElementException();
        }
        return fileDAO.findById(id);
    }

    public File update(File file)throws Exception {
        return fileDAO.update(file);
    }

    public void delete(long id)throws Exception{
        fileDAO.delete(id);
    }
}
