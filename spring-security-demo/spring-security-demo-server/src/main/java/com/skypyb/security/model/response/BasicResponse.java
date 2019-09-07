package com.skypyb.security.model.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * 基本的响应
 */
public class BasicResponse {

    @JsonIgnore
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private int code;
    private String message;


    public BasicResponse() {
    }

    public BasicResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String toJson() throws IOException {
        return MAPPER.writeValueAsString(this);
    }
}
