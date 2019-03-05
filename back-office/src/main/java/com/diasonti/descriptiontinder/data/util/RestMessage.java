package com.diasonti.descriptiontinder.data.util;

import java.util.Arrays;
import java.util.List;

public class RestMessage {

    private final static String OK = "ok";
    private final static String ERROR = "error";

    private String status;

    private List<Object> content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getContent() {
        return content;
    }

    public void setContent(List<Object> content) {
        this.content = content;
    }

    public static RestMessage ok(List<Object> content) {
        final RestMessage message = new RestMessage();
        message.setStatus(OK);
        message.setContent(content);
        return message;
    }

    public static RestMessage ok(Object... content) {
        return ok(Arrays.asList(content));
    }

    public static RestMessage error(List<Object> content) {
        final RestMessage message = new RestMessage();
        message.setStatus(ERROR);
        message.setContent(content);
        return message;
    }

    public static RestMessage error(Object... content) {
        return error(Arrays.asList(content));
    }
}
