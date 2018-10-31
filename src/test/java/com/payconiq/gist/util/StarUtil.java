package com.payconiq.gist.util;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.core.Every;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StarUtil {

    public static void starAGist(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .put("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    public static void unstarAGist(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .delete("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    public static void checkGistIsStarred(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .get("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    public static void checkGistIsUnstarred(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .get("/gists/{gistId}/star")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    public static ExtractableResponse<Response> listAuthenticatedUsersStarredGists(String token, String username) {
        return given()
                .header("Authorization", "token " + token)
                .when()
                .get("/gists/starred")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("owner.login", Every.everyItem(equalTo(username)))
                .extract();
    }

}
