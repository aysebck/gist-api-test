package com.payconiq.gist.util;

import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.startsWith;

public class HeaderUtil {

    public static void rateLimitRemainingShouldBeZeroAtListAUsersGists(String username) {
        given()
                .pathParam("username", username)
                .when()
                .get("/users/{username}/gists")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", startsWith("API rate limit exceeded"))
                .log().headers();
    }

}
