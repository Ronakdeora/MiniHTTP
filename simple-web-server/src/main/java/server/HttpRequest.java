package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest{
    private String method;
    private String path;
    private String request;
    private Map<String,String> headers;

    public HttpRequest(BufferedReader reader) throws IOException {
//        InputStream input = clientSocket.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        handleRequestParams(reader.readLine());
//
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
