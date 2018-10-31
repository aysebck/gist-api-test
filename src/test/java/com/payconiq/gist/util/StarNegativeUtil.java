package com.payconiq.gist.util;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.core.Every;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StarNegativeUtil {

    public static void starAGistWithoutAuthorization(String gistId) {
        given()
                .pathParam("gistId", gistId)
                .when()
                .put("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void unstarAGistWithoutAuthorization(String gistId) {
        given()
                .pathParam("gistId", gistId)
                .when()
                .delete("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void checkGistIsStarredWithoutAuthorization(String gistId) {
        given()
                .pathParam("gistId", gistId)
                .when()
                .get("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void listUsersStarredGistsWithoutAuthorization() {
        given()
                .when()
                .get("/gists/starred")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", equalTo("Requires authentication"));
    }

    public static void starANonexistentGist(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .put("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void unstarANonexistentGist(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .delete("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void checkNonexistentGistIsStarred(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .get("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }


}
