package com.payconiq.gist.util;

import com.payconiq.gist.pojo.Gist;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GistCRUDNegativeUtil {

    public static void getANonexistentGistById(String gistId) {
        given()
                .pathParam("gistId", gistId)
                .when()
                .get("/gists/{gistId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void deleteANonexistentGist(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .delete("/gists/{gistId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void editANonexistentGist(String token, String gistId, Gist gist) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .body(gist)
                .when()
                .patch("/gists/{gistId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void createAGistWithoutAuthorization(Gist gist) {
        given()
                .body(gist)
                .when()
                .post("/gists")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", equalTo("Requires authentication"));
    }

    public static void editAGistWithoutAuthorization(String gistId, Gist gist) {
        given()
                .pathParam("gistId", gistId)
                .body(gist)
                .when()
                .patch("/gists/{gistId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    public static void deleteAGistWithoutAuthorization(String gistId) {
        given()
                .pathParam("gistId", gistId)
                .when()
                .delete("/gists/{gistId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

}
