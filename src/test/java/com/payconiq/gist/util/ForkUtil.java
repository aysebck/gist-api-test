package com.payconiq.gist.util;

import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class ForkUtil {

    public static void listGistForks(String gistId) {
        given()
                .pathParam("gistId", gistId)
                .when()
                .get("/gists/{gistId}/forks")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
