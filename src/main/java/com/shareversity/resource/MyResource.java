package com.shareversity.resource;

import com.shareversity.dao.UserLoginDaoHibernate;
import com.shareversity.restModels.EmailVerification;
import com.shareversity.restModels.LoginObject;
import com.shareversity.restModels.UserRegistrationDetails;
import com.shareversity.utils.MailClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Path("/shareversity")
public class MyResource {

    UserLoginDaoHibernate userLoginDaoHibernate = new UserLoginDaoHibernate();


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello Shareversity student Website";
    }

//    // output text with argument
//    @Path("/{name}")
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String hello(@PathParam("name") String name) {
//        return "Jersey: hello " + name;
//    }
    /*
        Send verification code to the user's email address
     */

    @POST
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendRegistrationCode(UserRegistrationDetails userRegistrationDetails){

        // UserRegistrationDetails in java application is same as the table User_Registration
        String userEmail = userRegistrationDetails.getEmail();
        String firstName = userRegistrationDetails.getFirstName();

        // check if the email string is null or empty
        if (userEmail == null || userEmail.trim().length() == 0){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Email Id can't be null or empty").build();
        }

//        if(!isValidEmailId(userEmail)){
//            return Response.status(Response.Status.BAD_REQUEST).
//                    entity("Please enter a valid student email Id with edu domain").build();
//        }

        UserRegistrationDetails userByEmailId = userLoginDaoHibernate.findUserByEmailId(userEmail);

        if(userByEmailId!=null){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("User already exists").build();
        }

        String secretCode = createSecretCode();
        userRegistrationDetails.setSecretCode(secretCode);

        MailClient.sendRegistrationEmail(firstName,userEmail, secretCode);

        userLoginDaoHibernate.createStudentLogin(userRegistrationDetails);

        return Response.status(Response.Status.OK).
                entity("Registered Successfully!").build();
    }

    private boolean isValidEmailId(String userEmail) {
        if(userEmail.contains("edu")){
            return true;
        }
        return false;
    }

    @POST
    @Path("/verification")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response verifySecurityCode(EmailVerification emailVerification) {
        String securityCode = emailVerification.getSecurityCode();
        String email = emailVerification.getEmail();

        UserLoginDaoHibernate userLoginDaoHibernate = new UserLoginDaoHibernate();

        UserRegistrationDetails userByEmailId = userLoginDaoHibernate.findUserByEmailId(email);

        if(securityCode.equals(userByEmailId.getSecretCode())){

            userByEmailId.setIsCodeVerified(true);
            // set code verified to true
            userLoginDaoHibernate.updateUserCodeVerified(userByEmailId);

            return Response.status(Response.Status.OK).
                    entity("You have successfully confirmed your account with the " +
                            "email "+ email + ". You will use this email address to log in.!").build();
        }

        return Response.status(Response.Status.BAD_REQUEST).
                entity("Please enter the correct security code").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginUser(LoginObject loginInput){


        UserRegistrationDetails userRegistrationDetails =
                userLoginDaoHibernate.findUserByEmailIdAndPassword(loginInput);
        if(userRegistrationDetails == null){

            return Response.status(Response.Status.NOT_FOUND).
                    entity("Invalid User " + loginInput.getUserEmail()).build();
        }


        return Response.status(Response.Status.OK).
                entity("You have successfully logged in!").build();
    }

    private String createSecretCode() {
        return UUID.randomUUID().toString();
    }

}