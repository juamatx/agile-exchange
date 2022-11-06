package com.shareversity.dao;

import com.shareversity.restModels.LoginObject;
import com.shareversity.restModels.UserRegistrationDetails;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserLoginDaoHibernate {

    private SessionFactory factory;

    /**
     * Default Constructor.
     */
    public UserLoginDaoHibernate() {
        // it will check the hibernate.cfg.xml file and load it
        // next it goes to all table files in the hibernate file and loads them
        this.factory = UserDetailsSessionFactory.getFactory();
    }

    public synchronized UserRegistrationDetails createStudentLogin(UserRegistrationDetails userRegistrationDetails) {
        Session session = factory.openSession();
        Transaction tx = null;
//        if (findStudentLoginsByEmail(studentLogin.getEmail()) != null) {
//            throw new HibernateException("Student Login already exists.");
//        }
        try {
            tx = session.beginTransaction();
            session.save(userRegistrationDetails);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new HibernateException(e);
        } finally {
            session.close();
        }

        return userRegistrationDetails;
    }


    public UserRegistrationDetails findUserByEmailId(String userEmail) {
        Session session = factory.openSession();
        try {
            org.hibernate.query.Query query = session.createQuery("FROM UserRegistrationDetails WHERE email = :email");
            query.setParameter("email", userEmail);
            List list = query.list();
            if (list.isEmpty()) {
                return null;
            }
            return (UserRegistrationDetails) list.get(0);
        } finally {
            if (session != null) { session.close(); }
        }
    }

    public void updateUserCodeVerifyDidNotWork(boolean isCodeVerified, String email) {
        Session session = factory.openSession();
        try {
            org.hibernate.query.Query query = session.createQuery
                    ("UPDATE UserRegistrationDetails  u SET u.isCodeVerified = :isCodeVerified" +
                            " WHERE email = :userEmail");
            query.setParameter("isCodeVerified", isCodeVerified);
            query.setParameter("userEmail", email);
            query.executeUpdate();
        } finally {
            if (session != null) { session.close(); }
        }
    }

    public boolean updateUserCodeVerified(UserRegistrationDetails userRegistrationDetails) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(userRegistrationDetails);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new HibernateException(e);
        } finally {
            session.close();
        }
        return true;
    }

    public UserRegistrationDetails findUserByEmailIdAndPassword(LoginObject userEmail) {
        Session session = factory.openSession();
        try {
            org.hibernate.query.Query query =
                    session.createQuery("FROM UserRegistrationDetails" +
                            " WHERE email = :email AND password = :password AND isCodeVerified = :isCodeVerified");
            query.setParameter("email", userEmail.getUserEmail());
            query.setParameter("password", userEmail.getPassword());
            query.setParameter("isCodeVerified", true);
            List list = query.list();
            if (list.isEmpty()) {
                return null;
            }
            return (UserRegistrationDetails) list.get(0);
        } finally {
            if (session != null) { session.close(); }
        }

    }
}

