package com.payconiq.gist.test;

import com.payconiq.gist.base.BaseAPITest;
import com.payconiq.gist.pojo.Content;
import com.payconiq.gist.pojo.Gist;
import com.payconiq.gist.util.GistCRUDNegativeUtil;
import com.payconiq.gist.util.GistCRUDUtil;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class GistCRUDNegativeTest extends BaseAPITest {

    /*
     * Creates a gist, then deletes it.
     * Tries to get deleted gist by it's id.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void getANonexistentGistById() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("getAGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Get a nonexistent gist by id")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        GistCRUDUtil.deleteAGist(token, createAGistResponse.path("id"));
        GistCRUDNegativeUtil.getANonexistentGistById(createAGistResponse.path("id"));
    }

    /*
     * Creates a gist, then deletes it.
     * Tries to delete already deleted gist by it's id.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void deleteANonexistentGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("delete.txt", Content.builder().content("This content will be deleted").build());
        Gist gist = Gist.builder()
                .description("Delete a nonexistent gist")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        GistCRUDUtil.deleteAGist(token, createAGistResponse.path("id"));
        GistCRUDNegativeUtil.deleteANonexistentGist(token, createAGistResponse.path("id"));
    }

    /*
     * Creates a gist, then deletes it.
     * Tries to edit deleted gist by it's id.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void editANonexistentGist() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("hi.txt", Content.builder().content("My content").build());
        Gist gist = Gist.builder()
                .description("Edit a nonexistent gist")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        GistCRUDUtil.deleteAGist(token, createAGistResponse.path("id"));

        Map<String, Content> fileMapEdit = new HashMap<>();
        fileMapEdit.put("hiEdited.txt", Content.builder().content("This content wont be edited").build());
        Gist gistEdit = Gist.builder()
                .description("Edited: Edit a nonexistent gist")
                .isPublic(true)
                .files(fileMapEdit)
                .build();
        GistCRUDNegativeUtil.editANonexistentGist(token, createAGistResponse.path("id"), gistEdit);
    }

    /*
     * Tries to create a gist without "Authorization" header.
     * API should return status UNAUTHORIZED and error message.
     */
    @Test
    public void createAGistWithoutAuthorization() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("createGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("Create a gist without authorization")
                .isPublic(true)
                .files(fileMap)
                .build();

        GistCRUDNegativeUtil.createAGistWithoutAuthorization(gist);
    }

    /*
     * Creates a gist, then tries to edit it without "Authorization" header.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void editAGistWithoutAuthorization() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("hi.txt", Content.builder().content("my content").build());
        Gist gist = Gist.builder()
                .description("Edit a gist without authorization")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        Map<String, Content> fileMapEdit = new HashMap<>();
        fileMapEdit.put("hiEdited.txt", Content.builder().content("This content wont be edited").build());
        Gist gistEdit = Gist.builder()
                .description("Edited gist without authorization")
                .isPublic(true)
                .files(fileMapEdit)
                .build();
        GistCRUDNegativeUtil.editAGistWithoutAuthorization(createAGistResponse.path("id"), gistEdit);
    }

    /*
     * Creates a gist, then tries to delete it without "Authorization" header.
     * API should return status NOT_FOUND and error message.
     */
    @Test
    public void deleteAGistWithoutAuthorization() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("delete.txt", Content.builder().content("This content wont be deleted").build());
        Gist gist = Gist.builder()
                .description("Delete a gist without authorization")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        GistCRUDNegativeUtil.deleteAGistWithoutAuthorization(createAGistResponse.path("id"));
    }

}
