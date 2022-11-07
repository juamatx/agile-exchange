package com.shareversity;

import com.shareversity.dao.StudentDao;
import com.shareversity.resource.StudentResource;
import com.shareversity.restModels.EmailVerification;
import com.shareversity.restModels.LoginObject;
import com.shareversity.restModels.Students;
import jakarta.ws.rs.core.Response;
import org.junit.*;

public class StudentResourceTest {

    private static StudentResource studentResource;
    public StudentResourceTest(){

    }
    @BeforeClass
    public static void init() {
        studentResource = new StudentResource();
    }
    @Test
    public void testSendRegistrationEmailInvalidEmail(){
        Students students = new Students();
        students.setEmail("tomcat@gmail.com");
        Response response = studentResource.sendRegistrationCode(students);
        String result = (String) response.getEntity();
        Assert.assertEquals("Please enter a valid student email Id with edu domain" , result);
    }

    @Test
    public void testSendRegistrationEmailNullEmailId(){
        Students students = new Students();
        students.setEmail(null);
        Response response = studentResource.sendRegistrationCode(students);
        String result = (String) response.getEntity();
        Assert.assertEquals("Email Id can't be null or empty" , result);
    }

    @Test
    public void testSendRegistrationEmailEmptyEmailId(){
        Students students = new Students();
        students.setEmail("");
        Response response = studentResource.sendRegistrationCode(students);
        String result = (String) response.getEntity();
        Assert.assertEquals("Email Id can't be null or empty" , result);
    }
    @Test
    public void testLoginInvalidUser(){
        LoginObject loginObject = new LoginObject();
        loginObject.setUserEmail("tomcat@husky.edu");
        loginObject.setPassword("123");
        Response response = studentResource.loginUser(loginObject);
        String result = (String) response.getEntity();
        Assert.assertEquals("Invalid User tomcat@husky.edu" , result);
    }

    @Test
    public void testVerificationCodeInvalidUser(){
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail("xyz@husky.edu");
        emailVerification.setSecurityCode("xyz");
        Response response = studentResource.verifySecurityCode(emailVerification);
        String result = (String) response.getEntity();
        Assert.assertEquals("Invalid User" , result);
    }

    @Test
    public void sendRegistrationEmailValidEmail(){
        Students students = new Students();
        students.setFirstName("Tomcat");
        students.setLastName("Server");
        students.setEmail("tomcat1@husky.edu");
        students.setPassword("password");
        Response response = studentResource.sendRegistrationCode(students);
        String result = (String) response.getEntity();
        Assert.assertEquals("Security Code is sent Successfully!" , result);
    }


    @Test
    public void testVerificationCodeValidUser(){
        Students students = new Students();
        students.setFirstName("Tomcat");
        students.setLastName("Server");
        students.setEmail("tomcat2@husky.edu");
        students.setPassword("password");
        studentResource.sendRegistrationCode(students);

        StudentDao studentDao = new StudentDao();
        Students userByEmailId = studentDao.findUserByEmailId(students.getEmail());
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setSecurityCode(userByEmailId.getSecretCode());
        emailVerification.setEmail(userByEmailId.getEmail());
        Response response = studentResource.verifySecurityCode(emailVerification);
        String result = (String) response.getEntity();
        Assert.assertEquals("You have successfully confirmed your account with the " +
                "email " + emailVerification.getEmail() + ". You will use this email address to log in.!" , result);
    }

    @Test
    public void testVerificationCodeValidUserButInvalidCode(){
        Students students = new Students();
        students.setFirstName("Tomcat");
        students.setLastName("Server");
        students.setEmail("tomcat5@husky.edu");
        students.setPassword("password");
        studentResource.sendRegistrationCode(students);

        StudentDao studentDao = new StudentDao();
        Students userByEmailId = studentDao.findUserByEmailId(students.getEmail());
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setSecurityCode("xyz");
        emailVerification.setEmail(userByEmailId.getEmail());
        Response response = studentResource.verifySecurityCode(emailVerification);
        String result = (String) response.getEntity();
        Assert.assertEquals("Please enter the correct security code" , result);
    }
    @AfterClass
    public static void deleteForDuplicateDatabase() {
        StudentDao studentDao = new StudentDao();
        studentDao.deleteStudent("tomcat1@husky.edu");
        studentDao.deleteStudent("tomcat2@husky.edu");
        studentDao.deleteStudent("tomcat5@husky.edu");
    }
}
