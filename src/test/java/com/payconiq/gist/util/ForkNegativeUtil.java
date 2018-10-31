package com.payconiq.gist.util;

import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ForkNegativeUtil {

    public static void forkYourOwnGist(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .post("/gists/{gistId}/forks")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("You cannot fork your own gist."));
    }

    public static void forkNonexistentGist(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .post("/gists/{gistId}/forks")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));

    }

    public static void listNonexistentGistForks(String gistId) {
        given()
                .pathParam("gistId", gistId)
                .when()
                .get("/gists/{gistId}/forks")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void forkGistWithoutAuthorization(String gistId) {
        given()
                .pathParam("gistId", gistId)
                .when()
                .post("/gists/{gistId}/forks")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }
}
