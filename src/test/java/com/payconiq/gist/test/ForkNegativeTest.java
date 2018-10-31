package com.payconiq.gist.test;

import com.payconiq.gist.base.BaseAPITest;
import com.payconiq.gist.pojo.Content;
import com.payconiq.gist.pojo.Gist;
import com.payconiq.gist.util.ForkNegativeUtil;
import com.payconiq.gist.util.GistCRUDUtil;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ForkNegativeTest extends BaseAPITest {

    /*
     * Creates a gist, then tries to fork it.
     * API should return status UNPROCESSABLE_ENTITY and error message
     */
    @Test
    public void forkYourOwnGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("createGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Fork your own gist")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        ForkNegativeUtil.forkYourOwnGist(token, createAGistResponse.path("id"));
    }

    /*
     * Creates a gist, then deletes it.
     * Tries to fork the deleted gist.
     * API should return status NOT_FOUND and error message
     */
    @Test
    public void forkNonexistentGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("createGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Fork nonexistent gist")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        GistCRUDUtil.deleteAGist(token, createAGistResponse.path("id"));

        ForkNegativeUtil.forkNonexistentGist(token, createAGistResponse.path("id"));

    }

    /*
     * Creates a gist, then deletes it.
     * Tries to list the deleted gist's forks.
     * API should return status NOT_FOUND and error message
     */
    @Test
    public void listNonexistentGistForks() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("createGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("List nonexistent gist forks")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        GistCRUDUtil.deleteAGist(token, createAGistResponse.path("id"));

        ForkNegativeUtil.listNonexistentGistForks(createAGistResponse.path("id"));
    }

    /*
     * Creates a gist, then tries to fork without "Authorization" header.
     * API should return status NOT_FOUND and error message
     */
    @Test
    public void forkGistWithoutAuthorization() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("createGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Fork gist without authorization")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        ForkNegativeUtil.forkGistWithoutAuthorization(createAGistResponse.path("id"));
    }
}
