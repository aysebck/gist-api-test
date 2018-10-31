package com.payconiq.gist.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
public class Content {
    private String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String filename;
}
