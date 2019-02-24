package com.codecool.webroute;

public class NoSuchRouteException extends RuntimeException {

    public NoSuchRouteException() {
        this("No such route");
    }

    public NoSuchRouteException(String message) {
        super(message);
    }
}
