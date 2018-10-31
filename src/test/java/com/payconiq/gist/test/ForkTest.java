package com.payconiq.gist.test;

import com.payconiq.gist.base.BaseAPITest;
import com.payconiq.gist.pojo.Content;
import com.payconiq.gist.pojo.Gist;
import com.payconiq.gist.util.ForkUtil;
import com.payconiq.gist.util.GistCRUDUtil;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ForkTest extends BaseAPITest {

    /*
     * Creates a gist, then lists the gist's forks.
     * API should return status OK and result.
     */
    @Test
    public void listGistForks() {
        Map<String, Content> fileMap = new HashMap<>();
        fileMap.put("createGist.java", Content.builder().content("my code").build());
        Gist gist = Gist.builder()
                .description("List gist forks")
                .isPublic(true)
                .files(fileMap)
                .build();
        ExtractableResponse<Response> createAGistResponse = GistCRUDUtil.createAGist(token, gist);

        ForkUtil.listGistForks(createAGistResponse.path("id"));
    }
}
