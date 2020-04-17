package com.demo;

import com.DAO.FileDAO;
import com.controller.FileController;
import com.controller.TransferController;
import com.model.File;
import com.model.Storage;
import com.service.FileService;
import com.service.StorageService;

public class Demo {
    public static void main(String[] args) throws Exception{
        FileService fileService = new FileService();

        StorageService storageService = new StorageService();
        File file = fileService.findById(1);
        Storage storage = storageService.findById(1);
        TransferController transferController = new TransferController();
        transferController.put(storage, file);

    }
}
