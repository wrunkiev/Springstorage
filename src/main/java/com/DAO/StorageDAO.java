package com.DAO;

import com.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import static com.util.HibernateSessionFactory.createSessionFactory;

@Repository
public class StorageDAO {

    public Storage save(Storage storage)throws Exception{
        checkStorageNull(storage);

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.save(storage);

            tr.commit();

            return storage;
        } catch (HibernateException e) {
            System.err.println("Exception in method StorageDAO.save. Save storage with ID: " +
                    storage.getId() + " is failed");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    public Storage findById(long id){
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            Storage storage = session.get(Storage.class, id);

            tr.commit();
            return storage;
        } catch (HibernateException e) {
            System.err.println("Exception in method StorageDAO.findById. Storage with ID: " +
                    id + " is not defined in DB.");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    public Storage update(Storage storage)throws Exception{
        checkStorageNull(storage);

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.update(storage);

            tr.commit();
            return storage;
        } catch (HibernateException e) {
            System.err.println("Exception in method StorageDAO.update. Update storage with ID: " +
                    storage.getId() + " is failed");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    public void delete(long id)throws Exception{
        Storage storage = findById(id);

        if(storage == null){
            throw new Exception("Exception in method StorageDAO.delete. Storage with ID: " +
                    id + " is not defined in DB.");
        }

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.delete(storage);

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Exception in method StorageDAO.delete. Delete storage with ID: " +
                    storage.getId() + " is failed.");
            System.err.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        }
    }

    private static void checkStorageNull(Storage storage)throws Exception{
        if(storage == null){
            throw new Exception("Exception in method StorageDAO.checkStorageNull. Storage can't be null.");
        }
    }
}
