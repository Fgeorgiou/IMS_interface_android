package com.filippos.ims_interface.util;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public String getHttpMethod() {
        return method;
    }
}
