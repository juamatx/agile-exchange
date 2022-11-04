//package com.shareversity.dao;
//
//import com.shareversity.restModels.UserLogin;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.mehaexample.asdDemo.model.alignprivate.StudentLogins;
//
//import java.util.List;
//
//public class UserLoginDao {
//    private SessionFactory factory;
//
//    /**
//     * Default Constructor.
//     */
//    public UserLoginDao() {
//        // it will check the hibernate.cfg.xml file and load it
//        // next it goes to all table files in the hibernate file and loads them
//        this.factory = StudentSessionFactory.getFactory();
//    }
//
//    public UserLoginDao(boolean test) {
//        if (test) {
//            this.factory = StudentTestSessionFactory.getFactory();
//        }
//    }
//
//    /**
//     * Find a student login based on their email.
//     *
//     * @param email Student's email.
//     * @return the Student login object if found; null otherwise.
//     */
//    public UserLogin findStudentLoginsByEmail(String email) {
//        Session session = factory.openSession();
//        try {
//            org.hibernate.query.Query query = session.createQuery("FROM StudentLogins WHERE email = :email ");
//            query.setParameter("email", email);
//            List list = query.list();
//            if (list.isEmpty()) {
//                return null;
//            }
//            return (StudentLogins) list.get(0);
//        } finally {
//            if (session != null) { session.close(); }
//        }
//    }
//
////    /**
////     * Create a student login. This will only be successful if a student
////     * profile has been created in the student table with corresponding student
////     * email and profile.
////     *
////     * @param studentLogin object that wants to be created.
////     * @return newly created Student Login if sucessfull, throw a hibernate exception
////     * otherwise.
////     */
////    public synchronized StudentLogins createStudentLogin(StudentLogins studentLogin) {
////        Session session = factory.openSession();
////        Transaction tx = null;
////        if (findStudentLoginsByEmail(studentLogin.getEmail()) != null) {
////            throw new HibernateException("Student Login already exists.");
////        }
////        try {
////            tx = session.beginTransaction();
////            session.save(studentLogin);
////            tx.commit();
////        } catch (HibernateException e) {
////            if (tx != null) tx.rollback();
////            throw new HibernateException(e);
////        } finally {
////            session.close();
////        }
////
////        return studentLogin;
////    }
//
//}