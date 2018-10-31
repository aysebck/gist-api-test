package com.payconiq.gist.test;

import com.payconiq.gist.base.BaseAPITest;
import com.payconiq.gist.util.GistCRUDUtil;
import com.payconiq.gist.util.HeaderUtil;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HeaderTestLimit extends BaseAPITest {

    /*
     * Uses "list a user's gists" service to check response header rateLimitRemaining changed.
     * Makes first service call, gets the rateLimitRemaining
     * Then makes second service call.
     * rateLimitRemaining should decrease 1.
     */
    @Test
    public void rateLimitRemainingShouldDecrease() {
        ExtractableResponse<Response> response1 = GistCRUDUtil.listAUsersGists(username);
        int rateLimitRemaining1 = Integer.parseInt(response1.header("X-RateLimit-Remaining"));

        ExtractableResponse<Response> response2 = GistCRUDUtil.listAUsersGists(username);
        int rateLimitRemaining2 = Integer.parseInt(response2.header("X-RateLimit-Remaining"));

        assertThat(rateLimitRemaining2, equalTo(rateLimitRemaining1 - 1));
    }

    /*
     * Uses "list a user's gists" service to check response header rateLimitRemaining count exceeded.
     * Tries to reach max rate limit. So rateLimitRemaining count should be 0.
     * Unauthenticated services like this has the min rate limit.
     * Makes first service call, gets the rateLimitRemaining
     * Then makes service call as rateLimitRemaining count.
     * After rate limit reached to 0, API should return status FORBIDDEN and error message.
     */
   // @Test
    public void rateLimitRemainingShouldBeZero() {
        ExtractableResponse<Response> response1 = GistCRUDUtil.listAUsersGists(username);
        int rateLimitRemaining1 = Integer.parseInt(response1.header("X-RateLimit-Remaining"));
        System.out.println(rateLimitRemaining1);

        for (int i = 0; i < rateLimitRemaining1; i++) {
            GistCRUDUtil.listAUsersGists(username);
        }

        HeaderUtil.rateLimitRemainingShouldBeZeroAtListAUsersGists(username);
    }
}
