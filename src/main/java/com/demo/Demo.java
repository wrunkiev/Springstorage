package com.demo;

import com.controller.Controller;
import com.controller.FileController;
import com.controller.StorageController;
import com.model.File;
import com.model.Storage;
import com.service.FileService;
import com.service.StorageService;

public class Demo {
    public static void main(String[] args) throws Exception{
        FileService fileService = new FileService();
        File file = fileService.findById(1);
        StorageService storageService = new StorageService();
        Storage storage = storageService.findById(1);

        Controller controller = new Controller();
        controller.put(storage, file);
    }
}
