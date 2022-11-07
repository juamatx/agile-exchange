package com.shareversity.resource;

import com.shareversity.dao.StudentDao;
import com.shareversity.restModels.EmailVerification;
import com.shareversity.restModels.LoginObject;
import com.shareversity.restModels.Students;
import com.shareversity.utils.MailClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.UUID;

@Path("/shareversity")
public class StudentResource {
    StudentDao studentDao = new StudentDao();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello Shareversity Website!";
    }

    @POST
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendRegistrationCode(Students students){

        // UserRegistrationDetails in java application is same as the table User_Registration
        String studentEmail = students.getEmail();
        String firstName = students.getFirstName();

        // check if the email string is null or empty
        if (studentEmail == null || studentEmail.trim().length() == 0){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Email Id can't be null or empty").build();
        }

        // check if the email entered is a valid email id and also must be
        // a student email id with edu in it
        // TODO: check if email is valid and exists and also check for .edu
        if(!isValidEmailId(studentEmail)){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Please enter a valid student email Id with edu domain").build();
        }

        Students studentObject = studentDao.findUserByEmailId(studentEmail);

        // check if the student is an already registered student
        // if student record exist but not registration is not confirmed then we allow to update
        // new information
        if(studentObject!=null && studentObject.getIsCodeVerified()){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Student already exists").build();
        }

        //todo: update createSecretCode function get a smaller code
        // todo: add security code constraint to be valid only for 15 minutes
        String secretCode = createSecretCode();
        students.setSecretCode(secretCode);

        //todo: create a test email id and update in the MailClient
        MailClient.sendRegistrationEmail(firstName,studentEmail, secretCode);

        students.setCreateDate(new Date());

        Students newStudent = null;
        if(studentObject==null){
            newStudent = studentDao.createNewStudent(students,false);
        }else {
            newStudent = studentDao.createNewStudent(students,true);
        }


        if(newStudent!=null){
            return Response.status(Response.Status.OK).
                    entity("Security Code is sent Successfully!").build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity("Something Went Wrong" + studentEmail).build();
    }

    //API to request another security code
    @POST
    @Path("/verification")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response verifySecurityCode(EmailVerification emailVerification) {
        String securityCode = emailVerification.getSecurityCode();
        String email = emailVerification.getEmail();

        Students userByEmailId = studentDao.findUserByEmailId(email);

        if(userByEmailId==null){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Invalid User").build();
        }

        if (userByEmailId.getIsCodeVerified()) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Security code is already verified").build();
        }

        if (!securityCode.equals(userByEmailId.getSecretCode())) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Please enter the correct security code").build();
        }

        userByEmailId.setIsCodeVerified(true);
        //todo: change date to timestamp
        userByEmailId.setCreateDate(new Date());
        // set code verified to true
        boolean codeVerifiedUpdated = studentDao.updateUserCodeVerified(userByEmailId);

        if (!codeVerifiedUpdated) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Something Went Wrong. Please retry.").build();
        }

        return Response.status(Response.Status.OK).
                entity("You have successfully confirmed your account with the " +
                        "email " + email + ". You will use this email address to log in.!").build();

    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginUser(LoginObject loginInput){

        Students students =
                studentDao.findUserByEmailIdAndPassword(loginInput);

        if(students == null){
            return Response.status(Response.Status.NOT_FOUND).
                    entity("Invalid User " + loginInput.getUserEmail()).build();
        }


        return Response.status(Response.Status.OK).
                entity("You have successfully logged in!").build();
    }

    private boolean isValidEmailId(String userEmail) {
        if(userEmail.contains(".edu")){
            return true;
        }
        return false;
    }

    private String createSecretCode() {
        return UUID.randomUUID().toString().substring(30);
    }
}