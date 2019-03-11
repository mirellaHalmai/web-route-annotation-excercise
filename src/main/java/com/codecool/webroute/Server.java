package com.codecool.webroute;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) throws Exception {
        RouteHandlerMethod routeHandlerMethod = new RouteHandlerMethod();
        RouteHandler routeHandler = new RouteHandler(routeHandlerMethod);
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", routeHandler);
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
