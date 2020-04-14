package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.File;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;


public class FileController {
    private BufferedReader bufferedReader;

    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.POST, value = "/saveFile", produces = "text/plain")
    public void save(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            File file = requestRead(req);
            fileService.save(file);
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

    @RequestMapping(method = RequestMethod.GET, value = "/getFile", produces = "text/plain")
    public void findById(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            String idString = req.getParameter("id");
            long id = Long.parseLong(idString);
            File file = fileService.findById(id);
            resp.getWriter().println(file.toString());
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

    @RequestMapping(method = RequestMethod.PUT, value = "/putFile", produces = "text/plain")
    public void update(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        try{
            File file = requestRead(req);
            fileService.update(file);
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

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteFile", produces = "text/plain")
    public void delete(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            File file = requestRead(req);
            fileService.delete(file.getId());
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

    private File getFile(String response) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            File file = mapper.readValue(response, File.class);
            return file;
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

    private File requestRead(HttpServletRequest req)throws IllegalArgumentException, IOException{
        bufferedReader = req.getReader();
        String str = bodyContent(bufferedReader);
        File file = getFile(str);
        if(file == null){
            throw new IllegalArgumentException("Request is empty");
        }
        return file;
    }
}
