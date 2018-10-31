package com.payconiq.gist.test;

import com.payconiq.gist.base.BaseAPITest;
import com.payconiq.gist.pojo.Content;
import com.payconiq.gist.pojo.Gist;
import com.payconiq.gist.util.GistCRUDUtil;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GistCRUDTest extends BaseAPITest {


    /*
     * Lists all public gists.
     * API should return status OK and result.
     */
    @Test
    public void listAllPublicGists() {
        GistCRUDUtil.listAllPublicGists();
    }

    /*
     * Lists a user's gists by username.
     * API should return status OK and result.
     */
    @Test
    public void listAUsersGists() {
        GistCRUDUtil.listAUsersGists(username);
    }

    /*
     * Creates a gist.
     * API should return status CREATED and result.
     */
    @Test
    public void createAGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("createGist.java", Content.builder().content("my code").build());
        fileMap.put("ayse.txt", Content.builder().content("my content").build());
        Gist gist = Gist.builder()
                .description("Create a gist")
                .isPublic(true)
                .files(fileMap)
                .build();
        GistCRUDUtil.createAGist(token, gist);
    }

    /*
     * Creates a gist, then gets it by it's id.
     * API should return status OK and result.
     */
    @Test
    public void getAGistById() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("getAGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Get a gist by id")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        ExtractableResponse<Response> gistById = GistCRUDUtil.getAGistById(createAGistResponse.path("id"));
        assertThat("Description should be same with created gist's description",
                gistById.path("description"), equalTo("Get a gist by id"));
    }

    /*
     * Creates a gist, then edits it by it's id.
     * API should return status OK and result.
     */
    @Test
    public void editAGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("first.java", Content.builder().content("first content code code").build());
        fileMap.put("second.txt", Content.builder().content("second content").build());
        Gist gist = Gist.builder()
                .description("Edit a gist")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);


        Map<String, Content> fileMapEdit = new HashMap<>();
        fileMapEdit.put("first.java", Content.builder()
                .content("edited content code code more code")
                .filename("firstEdited").build());
        fileMapEdit.put("second.txt", null);
        fileMapEdit.put("new.txt", Content.builder().content("freshly created").build());
        Gist gistEdit = Gist.builder()
                .description("Edited: Edit a gist")
                .files(fileMapEdit)
                .build();
        GistCRUDUtil.editAGist(token, createAGistResponse.path("id"), gistEdit);
    }

    /*
     * Creates a gist, then deletes it by it's id.
     * API should return status NO_CONTENT.
     * Then gets user's gists and checks that list hasn't got the deleted gist.
     */
    @Test
    public void deleteAGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("delete.txt", Content.builder().content("This content will be deleted").build());
        Gist gist = Gist.builder()
                .description("Delete a gist")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        GistCRUDUtil.deleteAGist(token, createAGistResponse.path("id"));
        GistCRUDUtil.checkGistNotExistInUserGists(username, createAGistResponse.path("id"));
    }

    /*
     * Creates a gist, then lists it's commits.
     * API should return status OK and result.
     */
    @Test
    public void listGistCommits() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("gistCommits.txt", Content.builder().content("my content").build());
        Gist gist = Gist.builder()
                .description("List gist commits")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        GistCRUDUtil.listGistCommits(createAGistResponse.path("id"));
    }


}
