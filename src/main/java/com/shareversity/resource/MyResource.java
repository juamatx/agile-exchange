package com.shareversity.resource;

import com.shareversity.dao.UserLoginDaoHibernate;
import com.shareversity.restModels.UserRegistrationDetails;
import com.shareversity.utils.MailClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/shareversity")
public class MyResource {

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

        // check if the email string is null or empty
        if (userEmail == null || userEmail.trim().length() == 0){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Email Id can't be null or empty").build();
        }

//        if(!isValidEmailId(userEmail)){
//            return Response.status(Response.Status.BAD_REQUEST).
//                    entity("Please enter a valid student email Id with edu domain").build();
//        }

        UserLoginDaoHibernate userLoginDaoHibernate = new UserLoginDaoHibernate();
        UserRegistrationDetails userByEmailId = userLoginDaoHibernate.findUserByEmailId(userEmail);

        if(userByEmailId!=null){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("User already exists").build();
        }

        String secretCode = createSecretCode();
        userRegistrationDetails.setSecretCode(secretCode);

        MailClient.sendRegistrationEmail(userEmail, secretCode);

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

    private String createSecretCode() {
        return UUID.randomUUID().toString();
    }

}