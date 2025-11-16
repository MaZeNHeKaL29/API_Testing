package com.fakestore.api.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import com.fakestore.api.pojo.*;

import java.util.HashMap;
import java.util.Map;

import static com.fakestore.api.variables.Fakestore.*;

public class User_Testing {

    String userEndpoint = "users";

    int userIDRandom;

    String userRandomUsername;

    String userRandompassword;

    String userRandomEmail;

    String userUpdatedEmail = "updated@gmail.com";

    String userCreatedUsername = "Mazen_29";

    String userCreatedEmail = "mazen.tech29@gmail.com";

    String userCreatedPassword = "2912001";

    int userIDCreated;


    @Test(priority = 0)
    public void getAllUsers() {

        User[] users =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(userEndpoint)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(User[].class);


        int randomIndex = (int) (Math.random() * users.length);

        userRandomUsername = users[randomIndex].getUsername();
        userRandompassword = users[randomIndex].getPassword();
        userRandomEmail = users[randomIndex].getEmail();
        userIDRandom = users[randomIndex].getId();

        System.out.println("User Username : " + userRandomUsername);
        System.out.println("User Email : "+ userRandomEmail);
        System.out.println("User Password : " + userRandompassword);

    }

    @Test(priority = 1,dependsOnMethods = {"getAllUsers"})
    public void getUserRandomID()
    {
        User userRandom =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(userEndpoint + "/" + userIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(User.class);


        Assert.assertEquals(userRandom.getUsername(),userRandomUsername,"User Username Check");
        Assert.assertEquals(userRandom.getEmail(),userRandomEmail,"User Email Check");
        Assert.assertEquals(userRandom.getPassword(),userRandompassword,"User Password Check");
    }

    @Test(priority = 2,dependsOnMethods = {"getAllUsers"})
    public void updateUserRandom()
    {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", userRandomUsername + "_updated");
        requestBody.put("email", userUpdatedEmail);
        requestBody.put("password", userRandompassword + "_updated");

        User user =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .body(requestBody).log().all()

                        .when().put(userEndpoint + "/" + userIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(User.class);


        try
        {
            Assert.assertEquals(user.getUsername(),userRandomUsername + "_updated","User Username Check");
            Assert.assertEquals(user.getEmail(),userUpdatedEmail,"User Email Check");
            Assert.assertEquals(user.getPassword(),userRandompassword + "_updated","User Password Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }


    }

    @Test(priority = 3,dependsOnMethods = {"getAllUsers","updateUserRandom"})
    public void getUserRandomIDAfterUpdate()
    {
        User user =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(userEndpoint + "/" + userIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(User.class);


        try
        {
            Assert.assertEquals(user.getUsername(),userRandomUsername + "_updated","User Username Check");
            Assert.assertEquals(user.getEmail(),userUpdatedEmail,"User Email Check");
            Assert.assertEquals(user.getPassword(),userRandompassword + "_updated","User Password Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }

    @Test(priority = 4,dependsOnMethods = {"getAllUsers"})
    public void deleteUserRandomID()
    {
        User user =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().delete(userEndpoint + "/" + userIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(User.class);


    }

    @Test(priority = 5,dependsOnMethods = {"getAllUsers","deleteUserRandomID"})
    public void getUserRandomIDAfterDelete()
    {
        User user =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(userEndpoint + "/" + userIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(404)
                        .extract().as(User.class);


    }

    @Test(priority = 6)
    public void createUser()
    {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", userCreatedUsername);
        requestBody.put("email", userCreatedEmail);
        requestBody.put("password", userCreatedPassword);

        User user =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .body(requestBody).log().all()

                        .when().post(userEndpoint)

                        .then().log().all()
                        .assertThat().statusCode(201)
                        .extract().as(User.class);


        try
        {
            userIDCreated = user.getId();
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }


    }

    @Test(priority = 7,dependsOnMethods = {"createUser"})
    public void getUserCreatedID()
    {
        try
        {
            User user =

                    given().baseUri(url)

                            .header(accept)
                            .header(content_type)

                            .when().get(userEndpoint + "/" + userIDCreated)

                            .then().log().all()
                            .assertThat().statusCode(200)
                            .extract().as(User.class);



            Assert.assertEquals(user.getUsername(),userCreatedUsername,"User Username Check");
            Assert.assertEquals(user.getEmail(),userCreatedEmail,"User Email Check");
            Assert.assertEquals(user.getPassword(),userCreatedPassword,"User Password Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }


    @Test(priority = 8,dependsOnMethods = {"createUser"})
    public void updateUserCreated()
    {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", userCreatedUsername + "_updated");
        requestBody.put("email", userCreatedEmail);
        requestBody.put("password", userCreatedPassword + "_updated");

        User user =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .body(requestBody).log().all()

                        .when().put(userEndpoint)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(User.class);


        try
        {
            Assert.assertEquals(user.getUsername(),userCreatedUsername + "_updated","User Username Check");
            Assert.assertEquals(user.getEmail(),userCreatedEmail,"User Email Check");
            Assert.assertEquals(user.getPassword(),userCreatedPassword + "_updated","User Password Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }


    }

    @Test(priority = 9,dependsOnMethods = {"createUser","updateUserCreated"})
    public void getUserCreatedIDAfterUpdate()
    {
        User user =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(userEndpoint + "/" + userIDCreated)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(User.class);


        try
        {
            Assert.assertEquals(user.getUsername(),userCreatedUsername + "_updated","User Username Check");
            Assert.assertEquals(user.getEmail(),userCreatedEmail,"User Email Check");
            Assert.assertEquals(user.getPassword(),userCreatedPassword + "_updated","User Password Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }

    @Test(priority = 10,dependsOnMethods = {"createUser"})
    public void deleteUserCreatedID()
    {

        given().baseUri(url)

                .header(accept)
                .header(content_type)

                .when().delete(userEndpoint + "/" + userIDCreated)

                .then().log().all()
                .assertThat().statusCode(200);


    }

    @Test(priority = 11,dependsOnMethods = {"createUser","deleteUserCreatedID"})
    public void getUserCreatedIDAfterDelete()
    {
        given().baseUri(url)

                .header(accept)
                .header(content_type)

                .when().get(userEndpoint + "/" + userIDCreated)

                .then().log().all()
                .assertThat().statusCode(404);


    }

}
