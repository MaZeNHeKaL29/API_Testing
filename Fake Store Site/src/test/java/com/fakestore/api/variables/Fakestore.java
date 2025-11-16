package com.fakestore.api.variables;

import io.restassured.http.Header;

public class Fakestore {

    public static String url = "https://fakestoreapi.com";

    public static Header accept = new Header("Accept","application/json");

    public static Header content_type = new Header("Content-Type", "application/json");
}
