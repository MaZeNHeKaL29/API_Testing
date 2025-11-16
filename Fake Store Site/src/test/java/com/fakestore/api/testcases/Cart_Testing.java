package com.fakestore.api.testcases;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fakestore.api.pojo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fakestore.api.variables.Fakestore.*;

public class Cart_Testing {

    String cartEndpoint = "carts";

    int userIDRandom;

    int cartIDRandom;

    int userIDCartUpdate = 3;

    int productIDCartUpdate = 1;

    int productQuantityUpdate = 20;

    int cartIDCreated;


    @Test(priority = 0)
    public void getAllCarts() {

        Cart[] carts =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(cartEndpoint)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Cart[].class);


        int randomIndex = (int) (Math.random() * carts.length);

        userIDRandom = carts[randomIndex].getUserId();
        cartIDRandom = carts[randomIndex].getId();

        System.out.println("CartID Random :" + cartIDRandom);
        System.out.println("UserId Random :" + userIDRandom);

    }

    @Test(priority = 1,dependsOnMethods = {"getAllCarts"})
    public void getCartRandomID()
    {
        Cart cartRandom =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(cartEndpoint + "/" + cartIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Cart.class);


        Assert.assertEquals(cartRandom.getId(),cartIDRandom,"Cart ID Check");
        Assert.assertEquals(cartRandom.getUserId(),userIDRandom,"User ID Check");
    }

    @Test(priority = 2,dependsOnMethods = {"getAllCarts"})
    public void updateCartRandom()
    {
        Cart.Product product = new Cart.Product(productIDCartUpdate, productQuantityUpdate);

        List<Cart.Product> products = new ArrayList<>();
        products.add(product);


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", userIDCartUpdate);
        requestBody.put("products", products);

        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .body(requestBody).log().all()

                        .when().put(cartEndpoint + "/" + cartIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Cart.class);


        Assert.assertEquals(cart.getUserId(), userIDCartUpdate,"UserID Update Check");

        Cart.Product productUpdate = cart.getProducts()
                .stream()
                .filter(p -> p.getProductId() == 1)
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(productUpdate,"Product ID 1 exists");
        Assert.assertEquals(productUpdate.getQuantity(), 20,"Product ID 1 has quantity = 20");


    }

    @Test(priority = 3,dependsOnMethods = {"getAllCarts","updateCartRandom"})
    public void getCartRandomIDAfterUpdate()
    {
        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(cartEndpoint + "/" + cartIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Cart.class);


        Assert.assertEquals(cart.getId(),cartIDRandom,"Cart ID Check");
        Assert.assertEquals(cart.getUserId(),userIDCartUpdate,"User ID Check");

        Cart.Product product = cart.getProducts()
                .stream()
                .filter(p -> p.getProductId() == 1)
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(product,"Product ID 1 exists");
        Assert.assertEquals(product.getQuantity(), 20,"Product ID 1 has quantity = 20");

    }

    @Test(priority = 4,dependsOnMethods = {"getAllCarts"})
    public void deleteCartRandomID()
    {
        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().delete(cartEndpoint + "/" + cartIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Cart.class);


    }

    @Test(priority = 5,dependsOnMethods = {"getAllCarts","deleteCartRandomID"})
    public void getCartRandomIDAfterDelete()
    {
        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(cartEndpoint + "/" + cartIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(404)
                        .extract().as(Cart.class);


    }

    @Test(priority = 6)
    public void createCart()
    {
        Cart.Product product = new Cart.Product(productIDCartUpdate, productQuantityUpdate);

        List<Cart.Product> products = new ArrayList<>();
        products.add(product);


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", userIDCartUpdate);
        requestBody.put("products", products);

        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .body(requestBody).log().all()

                        .when().post(cartEndpoint)

                        .then().log().all()
                        .assertThat().statusCode(201)
                        .extract().as(Cart.class);


        Assert.assertEquals(cart.getUserId(), userIDCartUpdate,"UserID Update Check");

        Cart.Product productUpdate = cart.getProducts()
                .stream()
                .filter(p -> p.getProductId() == 1)
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(productUpdate,"Product ID 1 exists");
        Assert.assertEquals(productUpdate.getQuantity(), 20,"Product ID 1 has quantity = 20");

        cartIDCreated = cart.getId();


    }

    @Test(priority = 7,dependsOnMethods = {"createCart"})
    public void getCartCreatedID()
    {
        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(cartEndpoint + "/" + cartIDCreated)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Cart.class);


        try{
            Assert.assertEquals(cart.getId(),cartIDCreated,"Cart ID Check");
            Assert.assertEquals(cart.getUserId(),userIDCartUpdate,"User ID Check");

            Cart.Product product = cart.getProducts()
                    .stream()
                    .filter(p -> p.getProductId() == 1)
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(product,"Product ID 1 exists");
            Assert.assertEquals(product.getQuantity(), 20,"Product ID 1 has quantity = 20");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }


    @Test(priority = 8,dependsOnMethods = {"createCart"})
    public void updateCartCreated()
    {
        Cart.Product product = new Cart.Product(productIDCartUpdate, productQuantityUpdate + 5);

        List<Cart.Product> products = new ArrayList<>();
        products.add(product);


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", userIDCartUpdate);
        requestBody.put("products", products);

        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .body(requestBody).log().all()

                        .when().put(cartEndpoint + "/" + cartIDCreated)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Cart.class);



        try {
            Assert.assertEquals(cart.getUserId(), userIDCartUpdate, "UserID Update Check");

            Cart.Product productUpdate = cart.getProducts()
                    .stream()
                    .filter(p -> p.getProductId() == 1)
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(productUpdate, "Product ID 1 exists");
            Assert.assertEquals(productUpdate.getQuantity(), productQuantityUpdate + 5, "Product ID 1 has quantity = 20");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }


    }

    @Test(priority = 9,dependsOnMethods = {"createCart","updateCartCreated"})
    public void getCartCreatedIDAfterUpdate()
    {
        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(cartEndpoint + "/" + cartIDCreated)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Cart.class);


        try
        {
            Assert.assertEquals(cart.getId(),cartIDCreated,"Cart ID Check");
            Assert.assertEquals(cart.getUserId(),userIDCartUpdate,"User ID Check");

            Cart.Product product = cart.getProducts()
                    .stream()
                    .filter(p -> p.getProductId() == 1)
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(product,"Product ID 1 exists");
            Assert.assertEquals(product.getQuantity(), productQuantityUpdate + 5,"Product ID 1 has quantity = 20");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }

    @Test(priority = 10,dependsOnMethods = {"createCart"})
    public void deleteCartCreatedID()
    {
        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().delete(cartEndpoint + "/" + cartIDCreated)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Cart.class);


    }

    @Test(priority = 11,dependsOnMethods = {"createCart","deleteCartCreatedID"})
    public void getCartCreatedIDAfterDelete()
    {
        Cart cart =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(cartEndpoint + "/" + cartIDCreated)

                        .then().log().all()
                        .assertThat().statusCode(404)
                        .extract().as(Cart.class);


    }

}
