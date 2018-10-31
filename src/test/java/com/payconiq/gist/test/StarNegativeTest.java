package com.payconiq.gist.test;

import com.payconiq.gist.base.BaseAPITest;
import com.payconiq.gist.pojo.Content;
import com.payconiq.gist.pojo.Gist;
import com.payconiq.gist.util.GistCRUDUtil;
import com.payconiq.gist.util.StarNegativeUtil;
import com.payconiq.gist.util.StarUtil;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StarNegativeTest extends BaseAPITest {

    /*
     * Creates a gist, then tries to star it without "Authorization" header.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void starAGistWithoutAuthorization() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("starGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Star a gist without authorization")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);
        StarNegativeUtil.starAGistWithoutAuthorization(createAGistResponse.path("id"));
    }

    /*
     * Creates a gist, then stars it.
     * Tries to unstar it without "Authorization" header.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void unstarAGistWithoutAuthorization() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("starGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Unstar a gist without authorization")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);
        StarUtil.starAGist(token, createAGistResponse.path("id"));

        StarNegativeUtil.unstarAGistWithoutAuthorization(createAGistResponse.path("id"));
    }

    /*
     * Creates a gist, then stars it.
     * Tries to check if gist is starred without "Authorization" header.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void checkGistIsStarredWithoutAuthorization() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("starGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Check gist is starred without authorization")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);
        StarUtil.starAGist(token, createAGistResponse.path("id"));

        StarNegativeUtil.checkGistIsStarredWithoutAuthorization(createAGistResponse.path("id"));
    }

    /*
     * Tries to list user's starred gists without "Authorization" header.
     * API should return status UNAUTHORIZED and error message.
     */
    @Test
    public void listUsersStarredGistsWithoutAuthorization() {
        StarNegativeUtil.listUsersStarredGistsWithoutAuthorization();
    }

    /*
     * Creates a gist, then deletes it.
     * Tries to star deleted gist.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void starNonexistentGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("starGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Star a nonexistent gist")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);
        GistCRUDUtil.deleteAGist(token, createAGistResponse.path("id"));

        StarNegativeUtil.starANonexistentGist(token, createAGistResponse.path("id"));
    }

    /*
     * Creates a gist, then deletes it.
     * Tries to unstar deleted gist.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void unstarNonexistentGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("starGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Unstar a nonexistent gist")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);
        GistCRUDUtil.deleteAGist(token, createAGistResponse.path("id"));

        StarNegativeUtil.unstarANonexistentGist(token, createAGistResponse.path("id"));
    }

    /*
     * Creates a gist, then deletes it.
     * Tries to check if deleted gist is starred.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void checkNonexistentGistIsStarred() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("starGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Check a nonexistent gist is starred")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);
        GistCRUDUtil.deleteAGist(token, createAGistResponse.path("id"));

        StarNegativeUtil.checkNonexistentGistIsStarred(token, createAGistResponse.path("id"));
    }
}
