package com.shareversity.dao;

import com.shareversity.restModels.UserRegistrationDetails;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

}

