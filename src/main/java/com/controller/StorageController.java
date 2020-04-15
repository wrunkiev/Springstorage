package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Storage;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.service.StorageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;

@Controller
public class StorageController {
    private BufferedReader bufferedReader;

    @Autowired
    private StorageService storageService;

    @RequestMapping(method = RequestMethod.POST, value = "/saveStorage", produces = "text/plain")
    public void save(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            Storage storage = requestRead(req);
            storageService.save(storage);
        }catch (IllegalArgumentException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
        }catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }finally {
            bufferedReader.close();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getStorage", produces = "text/plain")
    public void findById(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            String idString = req.getParameter("id");
            long id = Long.parseLong(idString);
            Storage storage = storageService.findById(id);
            resp.getWriter().println(storage.toString());
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

    @RequestMapping(method = RequestMethod.PUT, value = "/putStorage", produces = "text/plain")
    public void update(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        try{
            Storage storage = requestRead(req);
            storageService.update(storage);
        }catch (IllegalArgumentException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
        }catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }finally {
            bufferedReader.close();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteStorage", produces = "text/plain")
    public void delete(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            Storage storage = requestRead(req);
            storageService.delete(storage.getId());
        }catch (IllegalArgumentException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
        }catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }finally {
            bufferedReader.close();
        }
    }

    private Storage getStorage(String response) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            Storage storage = mapper.readValue(response, Storage.class);
            return storage;
        }catch (Exception e){
            return null;
        }
    }

    private String bodyContent(BufferedReader reader) throws IOException {
        String input;
        StringBuilder requestBody = new StringBuilder();
        while((input = reader.readLine()) != null) {
            requestBody.append(input);
        }
        return requestBody.toString();
    }

    private Storage requestRead(HttpServletRequest req)throws IllegalArgumentException, IOException{
        bufferedReader = req.getReader();
        String str = bodyContent(bufferedReader);
        Storage storage = getStorage(str);
        if(storage == null){
            throw new IllegalArgumentException("Request is empty");
        }
        return storage;
    }


}