package com.service;

import com.dao.StorageDAO;
import com.model.File;
import com.model.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StorageService {
    @Autowired
    private StorageDAO storageDAO;

    public Storage save(Storage storage)throws Exception{
        return storageDAO.save(storage);
    }

    public Storage findById(long id)throws NoSuchElementException {
        if(storageDAO.findById(id) == null){
            throw new NoSuchElementException();
        }
        return storageDAO.findById(id);
    }

    public Storage update(Storage storage)throws Exception {
        return storageDAO.update(storage);
    }

    public void delete(long id)throws Exception{
        storageDAO.delete(id);
    }

    public List<File> getFiles(long id)throws Exception{
        return storageDAO.getFiles(id);
    }
}
