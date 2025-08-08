package org.mi.server;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Router {

    Map<String, Function<HttpRequest, String>> routes = new HashMap<>();

    public void register(String method, String path, Function<HttpRequest, String> handler){
        routes.put(method+" "+path, handler);
    }

    public Function<HttpRequest, String> getMethod(String method, String path){
        try {
            return routes.get( method + " " + path);
        }catch (Exception e){
            throw new RuntimeException("NO SUCH ROUTE FOUND");
        }
    }

}
