package org.mi;

import server.HttpServer;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            new HttpServer(8080);
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }
}
