package com.service;

import com.DAO.StorageDAO;
import com.model.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;

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
}
