package com.codecool.webroute;

import com.sun.net.httpserver.HttpServer;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws Exception {
        RouteHandlerMethod routeHandlerMethod = new RouteHandlerMethod();
        RouteHandler routeHandler = new RouteHandler(routeHandlerMethod);
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        Method[] handlerMethods = RouteHandlerMethod.class.getMethods();
        Arrays.stream(handlerMethods)
                .filter(method -> method.isAnnotationPresent(WebRoute.class))
                .map(method -> method.getAnnotation(WebRoute.class).value())
                .forEach(path -> server.createContext(path, routeHandler));
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
