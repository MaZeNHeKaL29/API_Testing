package org.example.apis;

import pojo.CreateTokenPojo;

public class CreateToken {
    private static final  String endPoint = "/auth";

    private CreateTokenPojo tokenPojo = new CreateTokenPojo();

    private String expectedToken;

    public CreateTokenPojo getTokenBody(String username, String password)
    {
        tokenPojo.setUsername(username);
        tokenPojo.setPassword(password);
        return tokenPojo;
    }


    public static String getEndpoint()
    {
        return endPoint;
    }

    public void setToken(String token)
    {
        this.expectedToken = token;
    }

    public String getToken()
    {
        return expectedToken;
    }
}
