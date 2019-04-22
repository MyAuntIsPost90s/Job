package com.bs.job.service;

import lingshi.valid.StringValid;

public class ServiceConstant {

    private static final String baseUrl = "http://localhost:8084";

    //private static final String baseUrl = "http://39.105.212.215:8080/JobGateway";

    public static String getUrl(String path) {
        if (StringValid.isNullOrEmpty(path)) {
            return null;
        }
        return baseUrl + path;
    }

}
