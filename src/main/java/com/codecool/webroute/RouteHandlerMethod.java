package com.codecool.webroute;

public class RouteHandlerMethod {

    @WebRoute("/")
    public String indexHandler() {
        return "It worked";
    }

    @WebRoute("/test")
    public String testHandler() {
        return "The test worked too!";
    }
}
