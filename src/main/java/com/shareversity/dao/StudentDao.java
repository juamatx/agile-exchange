package com.shareversity.dao;

import com.shareversity.restModels.LoginObject;
import com.shareversity.restModels.Students;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDao {

    private SessionFactory factory;

    /**
     * Default Constructor.
     */
    public StudentDao() {
        // it will check the hibernate.cfg.xml file and load it
        // next it goes to all table files in the hibernate file and loads them
        this.factory = StudensSessionFactory.getFactory();
    }

    public synchronized Students createNewStudent(Students students, boolean exists) {
        Session session = factory.openSession();
        Transaction tx = null;
//        if (findStudentLoginsByEmail(studentLogin.getEmail()) != null) {
//            throw new HibernateException("Student Login already exists.");
//        }
        try {
            tx = session.beginTransaction();
            if(exists){
                session.save(students);
            }else {
                session.save(students);
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new HibernateException(e);
        } finally {
            session.close();
        }

        return students;
    }


    public Students findUserByEmailId(String userEmail) {
        Session session = factory.openSession();
        try {
            org.hibernate.query.Query query = session.createQuery("FROM Students WHERE email = :email");
            query.setParameter("email", userEmail);
            List list = query.list();
            if (list.isEmpty()) {
                return null;
            }
            return (Students) list.get(0);
        } finally {
            if (session != null) { session.close(); }
        }
    }

    public void updateUserCodeVerifyDidNotWork(boolean isCodeVerified, String email) {
        Session session = factory.openSession();
        try {
            org.hibernate.query.Query query = session.createQuery
                    ("UPDATE Students u SET u.isCodeVerified = :isCodeVerified" +
                            " WHERE email = :userEmail");
            query.setParameter("isCodeVerified", isCodeVerified);
            query.setParameter("userEmail", email);
            query.executeUpdate();
        } finally {
            if (session != null) { session.close(); }
        }
    }

    public boolean updateUserCodeVerified(Students students) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(students);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new HibernateException(e);
        } finally {
            session.close();
        }
        return true;
    }

    public Students findUserByEmailIdAndPassword(LoginObject userEmail) {
        Session session = factory.openSession();
        try {
            org.hibernate.query.Query query =
                    session.createQuery("FROM Students" +
                            " WHERE email = :email AND password = :password AND isCodeVerified = :isCodeVerified");
            query.setParameter("email", userEmail.getUserEmail());
            query.setParameter("password", userEmail.getPassword());
            query.setParameter("isCodeVerified", true);
            List list = query.list();
            if (list.isEmpty()) {
                return null;
            }
            return (Students) list.get(0);
        } finally {
            if (session != null) { session.close(); }
        }
    }

    public synchronized boolean deleteStudent(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email Id argument cannot be null or empty.");
        }

        Students userByEmailId = findUserByEmailId(email);

        if (userByEmailId == null) {
            throw new HibernateException("Student cannot be found.");
        }

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(userByEmailId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new HibernateException(e);
        } finally {
            session.close();
        }

        return true;
    }
}

