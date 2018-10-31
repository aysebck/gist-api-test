package com.payconiq.gist.base;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

public class BaseAPITest {
    public static String username;
    public static String token;

    static {
        token = System.getProperty("gistToken");
        username = System.getProperty("gistUserName");
        System.out.println("username = " + username);
        System.out.println("token = " + token);

    }

    @Before
    public void beforeAll() {
        RestAssured.baseURI = "https://api.github.com";
    }

    @After
    public void afterAll() {
        RestAssured.reset();
    }
}
