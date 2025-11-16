package org.example.tests;

import org.example.apis.Booking;
import org.example.base.BaseAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import org.example.apis.CreateToken;
import pojo.BookingPojo;
import pojo.CreateTokenPojo;

public class EndToEndFlow {

    public CreateToken createTokenApi = new CreateToken();

    public Booking bookingAPI = new Booking();


    private final String url = "https://restful-booker.herokuapp.com";

    @BeforeClass
    public void setProxyConnection()
    {

    }

    @Test(priority = 1)
    public void getToken()
    {
        CreateTokenPojo body = createTokenApi.getTokenBody("admin","password123");

        String endPoint = CreateToken.getEndpoint();

        // 🧪 Send POST request
        Response response =
        given()
                .spec(BaseAPI.getRequestSpecs())
                .baseUri(url)
                .body(body).
        when().
                post(endPoint).
        then().
                statusCode(200).log().body().extract().response();

        createTokenApi.setToken(response.jsonPath().get("token"));
        System.out.println(createTokenApi.getToken());
    }

    @Test
    public void getBooking()
    {
        String endPoint = Booking.getEndpointBooking();

        Response response =
        given()
                .spec(BaseAPI.getRequestSpecs())
                .baseUri(url).
        when().
                get(endPoint).
        then().
                statusCode(200).log().body().extract().response();


        int bookingId = response.jsonPath().getInt("[0].bookingid");

        String bookingIdstr = response.jsonPath().getString("[0].bookingid");

        System.out.println(bookingIdstr);

        Booking.setValidBookingID(bookingId);

        System.out.println("First Booking ID: " + bookingId);
    }

    @Test
    public void getBookingID()
    {
        String endPoint = Booking.getEndpointBookingID();

        System.out.println(endPoint);

        Response response =
                given()
                        .spec(BaseAPI.getRequestSpecs())
                        .baseUri(url).
                        when().
                        get(endPoint).
                        then().
                        statusCode(200).log().body().extract().response();


    }

    @Test
    public void createBooking()
    {
        String endPoint = Booking.getEndpointBooking();

        Booking bookingAPI = new Booking();

        BookingPojo body = bookingAPI.getBookingBody("Mazen","Hekal",1029,true,"2025-01-09","2026-01-09","Breakfast");

        Response response =
                given()
                        .spec(BaseAPI.getRequestSpecs())
                        .baseUri(url)
                        .body(body).
                        when().
                        post(endPoint).
                        then().
                        statusCode(200).log().body().extract().response();

        bookingAPI.setBookingIDCreated(response.jsonPath().getInt("bookingid"));
        System.out.println(bookingAPI.getBookingIDCreated());
    }

    @Test(dependsOnMethods = {"getToken", "createBooking"})
    public void changeBookingPut()
    {

        String endPoint = bookingAPI.getBookingIDCreatedEndpoint();

        BookingPojo body = bookingAPI.getBookingBody("Mazen","Hekal",1030,true,"2025-01-09","2026-01-09","Breakfast");

        Response response =
                given()
                        .spec(BaseAPI.getRequestSpecs())
                        .header("Cookie","token="+createTokenApi.getToken())
                        .baseUri(url)
                        .body(body).
                        when().
                        put(endPoint).
                        then().
                        statusCode(200).log().body().extract().response();


    }

    @Test(dependsOnMethods = {"getToken", "createBooking","changeBookingPut"})
    public void deleteBooking()
    {

        String endPoint = bookingAPI.getBookingIDCreatedEndpoint();

        Response response =
                given()
                        .spec(BaseAPI.getRequestSpecs())
                        .header("Cookie","token="+createTokenApi.getToken())
                        .baseUri(url).
                        when().
                        delete(endPoint).
                        then().
                        statusCode(201).log().body().extract().response();

    }
}
