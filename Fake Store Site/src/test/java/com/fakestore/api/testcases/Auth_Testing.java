package com.fakestore.api.testcases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import com.fakestore.api.pojo.*;

import java.util.HashMap;
import java.util.Map;

import static com.fakestore.api.variables.Fakestore.*;

public class Auth_Testing {

    String userEndpoint = "users";

    String authEndpoint = "auth/login";

    int userIDRandom;

    String userRandomUsername;

    String userRandompassword;

    String userRandomEmail;


    String userCreatedUsername = "Mazen_29";

    String userCreatedEmail = "mazen.tech29@gmail.com";

    String userCreatedPassword = "2912001";

    int userIDCreated;

    String tokenRandom;

    String tokenCreated;

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
    public void createTokenFromUserRandom()
    {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", userRandomUsername);
        requestBody.put("password", userRandompassword);

        Response res =
        given().baseUri(url)
                .header(accept)
                .header(content_type)

                .body(requestBody)

                .when().post(authEndpoint)

                .then().log().all()
                .assertThat().statusCode(201)
                .extract().response();


        try {
            tokenRandom = res.path("token");

            System.out.println(tokenRandom);
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }

    @Test(priority = 3)
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

    @Test(priority = 4,dependsOnMethods = {"getAllUsers"})
    public void createTokenFromUserCreated()
    {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", userCreatedUsername);
        requestBody.put("password", userCreatedPassword);

        Response res =
                given().baseUri(url)
                        .header(accept)
                        .header(content_type)

                        .body(requestBody)

                        .when().post(authEndpoint)

                        .then().assertThat().statusCode(201)
                        .extract().response();


        try {
            tokenCreated = res.path("token");

            System.out.println(tokenCreated);
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }


}
