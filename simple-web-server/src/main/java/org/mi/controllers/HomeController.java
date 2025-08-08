package org.mi.controllers;

import org.mi.server.HttpRequest;

public class HomeController {

    public String home(HttpRequest request){
        return "<h1>Welcome to My Java Web Server!</h1>";
    }

    public String about(HttpRequest request){
        return "<p>This server was handcrafted in Java</p>";
    }

}
