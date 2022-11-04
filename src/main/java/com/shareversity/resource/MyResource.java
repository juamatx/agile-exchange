package com.shareversity.resource;

import com.shareversity.restModels.UserRegistrationDetails;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

//        String userEmail = userRegistrationDetails.getEmail();
//
//        // check if the email string is null or empty
//        if (userEmail == null || userEmail.trim().length() == 0){
//            return Response.status(Response.Status.BAD_REQUEST).
//                    entity("Email Id can't be null or empty").build();
//        }

//        if(!isValidEmailId(userEmail)){
//            return Response.status(Response.Status.BAD_REQUEST).
//                    entity("Please enter a valid student email Id with edu domain").build();
//        }

//        UserLoginDao userLoginDao = new UserLoginDao();
//        // check if the user is already registered
//        UserLogin studentLogin = userLoginDao.findStudentLoginsByEmail(userEmail);

        // check if studenLogin either has  record for given email and "confirmed" is true
//        if(studentLogin != null && (studentLogin.isConfirmed() == true)) {
//
//            return Response.status(Response.Status.NOT_ACCEPTABLE).
//                    entity("User is Already Registered!" + userEmail).build();
//        }


        return Response.status(Response.Status.OK).
                entity("Registration working").build();
    }

    private boolean isValidEmailId(String userEmail) {
        if(userEmail.contains("edu")){
            return true;
        }
        return false;
    }

}