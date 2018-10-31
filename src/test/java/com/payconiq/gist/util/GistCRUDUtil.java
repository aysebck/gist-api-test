package com.payconiq.gist.util;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

import com.payconiq.gist.pojo.Gist;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.core.Every;

public class GistCRUDUtil {

    public static void listAllPublicGists() {
        when()
                .get("/gists/public")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public static ExtractableResponse<Response> listAUsersGists(String username) {
        return given()
                .pathParam("username", username)
                .when()
                .get("/users/{username}/gists")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("owner.login", Every.everyItem(equalTo(username)))
                .extract();
    }

    public static ExtractableResponse<Response> getAGistById(String gistId) {
          return given()
                  .pathParam("gistId", gistId)
                  .when()
                  .get("/gists/{gistId}")
                  .then()
                  .statusCode(HttpStatus.SC_OK)
                  .body("id", equalTo(gistId))
                  .extract();
    }

    public static ExtractableResponse<Response> createAGist(String token, Gist gist) {
        return given()
                .header("Authorization", "token " + token)
                .body(gist)
                .when()
                .post("/gists")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue())
                .and().body("created_at", notNullValue())
                .and().body("updated_at", notNullValue())
                .extract();
    }

    public static void editAGist(String token, String gistId, Gist gist) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .body(gist)
                .when()
                .patch("/gists/{gistId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("description", equalTo(gist.getDescription()))
                .extract()
                .response()
                .prettyPrint();
    }

    public static void deleteAGist(String token, String gistId) {
        given()
                .header("Authorization", "token " + token)
                .pathParam("gistId", gistId)
                .when()
                .delete("/gists/{gistId}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    public static void checkGistNotExistInUserGists(String username, String gistId) {
        given()
                .pathParam("username", username)
                .when()
                .get("/users/{username}/gists")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", not(hasItem(gistId)));
    }

    public static void listGistCommits(String gistId) {
        given()
                .pathParam("gistId", gistId)
                .when()
                .get("/gists/{gistId}/commits")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }


}
