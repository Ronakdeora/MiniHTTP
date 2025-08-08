package org.mi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest{
    private String method;
    private String path;
    private String request;
    private Map<String,String> headers;

    public HttpRequest(Socket clientSocket) throws IOException {

//      this is our request in an input stream which has multiple lines
        InputStream input = clientSocket.getInputStream();
        System.out.println(input.toString());
//      we created a reader to that inputStream And Passed into HttpRequest to create the HttpRequest
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        handleRequestParams(reader.readLine());
        handleRequestHeaders(reader);
    }

    private void handleRequestHeaders(BufferedReader reader) throws IOException {
        headers = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            System.out.println("Request: " + line);
            String[] parts = line.split(":", 2);
            if(parts.length == 2){
                headers.put(parts[0].trim(),parts[1].trim());
            }
        }
    }

    private void handleRequestParams(String request) {
        if( request == null || request.isEmpty() ){
            throw new RuntimeException("BAD_REQUEST");
        }
        String[] requestLine = request.split("\\s+");
//        System.out.println(Arrays.toString(requestLine));
        if( requestLine.length < 3 ){
            throw new RuntimeException("BAD_REQUEST");
        }
        this.method = requestLine[0];
        this.path = requestLine[1];
        this.request = requestLine[2];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getRequest() {
        return request;
    }

    public String getHeader(String key){
        return headers.getOrDefault(key,"NO_VALID_VALUE_FOUND");
    }

}
