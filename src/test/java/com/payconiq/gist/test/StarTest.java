package com.payconiq.gist.test;

import com.payconiq.gist.base.BaseAPITest;
import com.payconiq.gist.pojo.Content;
import com.payconiq.gist.pojo.Gist;
import com.payconiq.gist.util.GistCRUDUtil;
import com.payconiq.gist.util.StarUtil;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class StarTest extends BaseAPITest {

    /*
     * Creates a gist, then stars it.
     * API should return status NO_CONTENT.
     * Then checks if gist is starred.
     * API should return status NO_CONTENT.
     */
    @Test
    public void starAGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("starGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Star a gist test")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);
        StarUtil.starAGist(token, createAGistResponse.path("id"));
        StarUtil.checkGistIsStarred(token, createAGistResponse.path("id"));
    }

    /*
     * Creates a gist. Stars the created gist, then unstars it.
     * API should return status NO_CONTENT.
     * Then checks if gist is unstarred.
     * API should return status NOT_FOUND.
     */
    @Test
    public void unstarAGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("unstarredGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Unstar a gist test")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);
        StarUtil.starAGist(token, createAGistResponse.path("id"));

        StarUtil.unstarAGist(token, createAGistResponse.path("id"));
        StarUtil.checkGistIsUnstarred(token, createAGistResponse.path("id"));
    }

    /*
     * Creates a gist. Stars the created gist.
     * Then lists the user's starred gists.
     * API should return status OK and result.
     * Result should contain the starred gist.
     */
    @Test
    public void starredGistShouldBeInUsersStarredGists() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("starGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Starred gist should be in the user's starred gists")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);
        StarUtil.starAGist(token, createAGistResponse.path("id"));

        ExtractableResponse<Response> starredGists = StarUtil.listAuthenticatedUsersStarredGists(token, GistCRUDTest.username);
        List<String> starredGistIds = starredGists.path("id");
        assertThat(starredGistIds, hasItem(createAGistResponse.path("id").toString()));
    }


}
