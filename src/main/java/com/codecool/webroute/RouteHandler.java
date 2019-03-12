package com.codecool.webroute;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RouteHandler implements HttpHandler {

    private RouteHandlerMethod routeHandlerMethod;
    private Map<String, Method> pathHandlerMethodMap = new HashMap<>();

    public RouteHandler(RouteHandlerMethod routeHandlerMethod) {
        this.routeHandlerMethod = routeHandlerMethod;
        Method[] routeHandlerMethods = RouteHandlerMethod.class.getMethods();
        Arrays.stream(routeHandlerMethods)
                .filter(method -> method.isAnnotationPresent(WebRoute.class))
                .forEach(method -> pathHandlerMethodMap.put(method.getAnnotation(WebRoute.class).value(), method));
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String route = httpExchange.getRequestURI().getPath();
        String response;
        int status;

        try {
            Method methodToCall = pathHandlerMethodMap.get(route);
            if (methodToCall == null) {
                status = 400;
                response = "Requested page doesn't exist.";
            } else {
                response = (String) methodToCall.invoke(routeHandlerMethod, null);
                status = 200;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            response = "Sorry, we encountered a problem, but we are working on it.";
            status = 500;
        }

        httpExchange.sendResponseHeaders(status, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}