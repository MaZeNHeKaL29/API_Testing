package org.example.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseAPI {

    public static RequestSpecification getRequestSpecs()
    {
        return new RequestSpecBuilder().
                setContentType(ContentType.JSON).addHeader("Accept","application/json").build();


    }

}
