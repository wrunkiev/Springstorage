package com.DAO;

import com.model.File;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import static com.util.HibernateSessionFactory.createSessionFactory;

@Repository
public class FileDAO {
    public File save(File file)throws Exception{
        checkFileNull(file);

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.save(file);

            tr.commit();

            return file;
        } catch (HibernateException e) {
            System.err.println("Exception in method FileDAO.save. Save file with ID: " +
                    file.getId() + " is failed");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    public File findById(long id){
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            File file = session.get(File.class, id);

            tr.commit();
            return file;
        } catch (HibernateException e) {
            System.err.println("Exception in method FileDAO.findById. File with ID: " +
                    id + " is not defined in DB.");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    public File update(File file)throws Exception{
        checkFileNull(file);

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.update(file);

            tr.commit();
            return file;
        } catch (HibernateException e) {
            System.err.println("Exception in method FileDAO.update. Update file with ID: " +
                    file.getId() + " is failed");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    public void delete(long id)throws Exception{
        File file = findById(id);

        if(file == null){
            throw new Exception("Exception in method FileDAO.delete. File with ID: " +
                    id + " is not defined in DB.");
        }

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.delete(file);

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Exception in method FileDAO.delete. Delete file with ID: " +
                    file.getId() + " is failed.");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    private static void checkFileNull(File file)throws Exception{
        if(file == null){
            throw new Exception("Exception in method FileDAO.checkFileNull. File can't be null.");
        }
    }
}
