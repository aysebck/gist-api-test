package com.payconiq.gist.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class Gist {
    private String description;
    @JsonProperty("public")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isPublic;
    Map<String,Content> files;

}
