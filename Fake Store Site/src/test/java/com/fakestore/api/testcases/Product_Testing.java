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

public class Product_Testing {

    String productEndpoint = "products";

    String productRandomTitle;

    int productIDRandom;

    double productPriceRandom;

    int productPriceUpdate = 30;

    String productTitleCreated = "Playstation 5";

    int productPriceCreated = 500;

    int productIDCreated;


    @Test(priority = 0)
    public void getAllProducts() {

        Product[] products =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(productEndpoint)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Product[].class);


        int randomIndex = (int) (Math.random() * products.length);

        productRandomTitle = products[randomIndex].getTitle();
        productIDRandom = products[randomIndex].getId();
        productPriceRandom = products[randomIndex].getPrice();

        System.out.println("ProductID Random :" + productIDRandom);
        System.out.println("Product Title :" + productRandomTitle);
        System.out.println("Product Price : " + productPriceRandom);

    }

    @Test(priority = 1,dependsOnMethods = {"getAllProducts"})
    public void getProductRandomID()
    {
        Product productRandom =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(productEndpoint + "/" + productIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Product.class);


        Assert.assertEquals(productRandom.getTitle(),productRandomTitle,"Product Title Check");
        Assert.assertEquals(productRandom.getPrice(),productPriceRandom,"Product Price Check");
    }

    @Test(priority = 2,dependsOnMethods = {"getAllProducts"})
    public void updateProductRandom()
    {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", productRandomTitle + "_updated");
        requestBody.put("price", productPriceUpdate);

        Product product =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .body(requestBody).log().all()

                        .when().put(productEndpoint + "/" + productIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Product.class);


        try
        {
            Assert.assertEquals(product.getTitle(),productRandomTitle + "_updated","Product Title Check");
            Assert.assertEquals(product.getPrice(),productPriceUpdate,"Product Price Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }


    }

    @Test(priority = 3,dependsOnMethods = {"getAllProducts","updateProductRandom"})
    public void getProductRandomIDAfterUpdate()
    {
        Product product =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(productEndpoint + "/" + productIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Product.class);


        try
        {
            Assert.assertEquals(product.getTitle(),productRandomTitle + "_updated","Product Title Check");
            Assert.assertEquals(product.getPrice(),productPriceUpdate,"Product Price Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }

    @Test(priority = 4,dependsOnMethods = {"getAllProducts"})
    public void deleteProductRandomID()
    {
        Product product =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().delete(productEndpoint + "/" + productIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Product.class);


    }

    @Test(priority = 5,dependsOnMethods = {"getAllProducts","deleteProductRandomID"})
    public void getProductRandomIDAfterDelete()
    {
        Product product =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(productEndpoint + "/" + productIDRandom)

                        .then().log().all()
                        .assertThat().statusCode(404)
                        .extract().as(Product.class);


    }

    @Test(priority = 6)
    public void createProduct()
    {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", productTitleCreated);
        requestBody.put("price", productPriceCreated);

        Product product =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .body(requestBody).log().all()

                        .when().post(productEndpoint)

                        .then().log().all()
                        .assertThat().statusCode(201)
                        .extract().as(Product.class);


        try
        {
            Assert.assertEquals(product.getTitle(),productTitleCreated,"Product Title Check");
            Assert.assertEquals(product.getPrice(),productPriceCreated,"Product Price Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

        productIDCreated = product.getId();


    }

    @Test(priority = 7,dependsOnMethods = {"createProduct"})
    public void getProductCreatedID()
    {
        try
        {
            Product product =

                    given().baseUri(url)

                            .header(accept)
                            .header(content_type)

                            .when().get(productEndpoint + "/" + productIDCreated)

                            .then().log().all()
                            .assertThat().statusCode(200)
                            .extract().as(Product.class);



                Assert.assertEquals(product.getTitle(),productTitleCreated + "_updated","Product Title Check");
                Assert.assertEquals(product.getPrice(),productPriceCreated,"Product Price Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }


    @Test(priority = 8,dependsOnMethods = {"createProduct"})
    public void updateProductCreated()
    {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", productTitleCreated);
        requestBody.put("price", productPriceCreated + 55);

        Product product =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .body(requestBody).log().all()

                        .when().put(productEndpoint)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Product.class);


        try
        {
            Assert.assertEquals(product.getTitle(),productTitleCreated + "_updated","Product Title Check");
            Assert.assertEquals(product.getPrice(),productPriceCreated + 55,"Product Price Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }


    }

    @Test(priority = 9,dependsOnMethods = {"createProduct","updateProductCreated"})
    public void getProductCreatedIDAfterUpdate()
    {
        Product product =

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(productEndpoint + "/" + productIDCreated)

                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().as(Product.class);


        try
        {
            Assert.assertEquals(product.getTitle(),productTitleCreated + "_updated","Product Title Check");
            Assert.assertEquals(product.getPrice(),productPriceCreated + 55,"Product Price Check");
        } catch (Exception e) {
            Assert.assertFalse(true,e.getMessage());
        }

    }

    @Test(priority = 10,dependsOnMethods = {"createProduct"})
    public void deleteProductCreatedID()
    {

                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().delete(productEndpoint + "/" + productIDCreated)

                        .then().log().all()
                        .assertThat().statusCode(200);


    }

    @Test(priority = 11,dependsOnMethods = {"createProduct","deleteProductCreatedID"})
    public void getProductCreatedIDAfterDelete()
    {
                given().baseUri(url)

                        .header(accept)
                        .header(content_type)

                        .when().get(productEndpoint + "/" + productIDCreated)

                        .then().log().all()
                        .assertThat().statusCode(404);


    }

}
