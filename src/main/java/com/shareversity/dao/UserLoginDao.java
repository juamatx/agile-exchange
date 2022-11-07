package com.shareversity.dao;

import com.shareversity.restModels.Students;

import java.sql.*;

public class UserLoginDao {
    Connection c = null;

    public UserLoginDao() {
        try {
            //Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:mariadb://localhost:3306/demo",
                            "mehaverma", "password");
        } catch (Exception exception) {
            System.out.println("Exception: " + exception);
        }
    }

    public void addUser(Students students) {
        try {

            Statement stmt = c.createStatement();

            // executeUpdate() is used for INSERT, UPDATE,
            // DELETE statements.It returns number of rows
            // affected by the execution of the statement
            String firstName = students.getFirstName();
            String lastName = students.getLastName();
            String emailPassword = students.getPassword();
            String email = students.getEmail();
            String secretCode = "";

            final String SQL_INSERT = "INSERT INTO USER_REGISTRATION" +
                    " (FIRST_NAME, LAST_NAME, EMAIL_PASSWORD, EMAIL, SECRET_CODE, CODE_VERIFIED) VALUES (?,?,?, ?,?,?)";

            PreparedStatement preparedStatement = c.prepareStatement(SQL_INSERT);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, emailPassword);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, secretCode);
            preparedStatement.setBoolean(6, false);

            int result = preparedStatement.executeUpdate();


            if (result > 0) {
                System.out.println("successfully inserted");
            } else {
                System.out.println("unsucessful insertion ");
            }

            // closing connection
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public boolean findUserLoginsByEmail(String userEmail) throws SQLException {
        final String SELECT_QUERY = "SELECT * FROM USER_REGISTRATION WHERE email = " + userEmail;

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(SELECT_QUERY);


        while(rs.next()){
           return true;
        }

        return false;
    }
}

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

//    /**
//     * Create a student login. This will only be successful if a student
//     * profile has been created in the student table with corresponding student
//     * email and profile.
//     *
//     * @param studentLogin object that wants to be created.
//     * @return newly created Student Login if sucessfull, throw a hibernate exception
//     * otherwise.
//     */
//    public synchronized StudentLogins createStudentLogin(StudentLogins studentLogin) {
//        Session session = factory.openSession();
//        Transaction tx = null;
//        if (findStudentLoginsByEmail(studentLogin.getEmail()) != null) {
//            throw new HibernateException("Student Login already exists.");
//        }
//        try {
//            tx = session.beginTransaction();
//            session.save(studentLogin);
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) tx.rollback();
//            throw new HibernateException(e);
//        } finally {
//            session.close();
//        }
//
//        return studentLogin;
//    }

//}