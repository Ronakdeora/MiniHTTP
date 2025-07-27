package server;

import java.io.IOException;
import java.io.OutputStream;

public class Router {


    public Router(HttpRequest request, OutputStream outputStream ) throws IOException {

        String path = request.getPath();

        if (path.equals("/")) {
            respond200(outputStream, "<h1>Welcome to My Java Web Server!</h1>");
        } else if (path.equals("/about")) {
            respond200(outputStream, "<p>This server was handcrafted in Java</p>");
        } else {
            respond404(outputStream);
        }
    }

    private void respond200(OutputStream out, String body) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;
        out.write(response.getBytes());
    }

    private void respond404(OutputStream out) throws IOException {
        String body = "<h1>404 Not Found</h1>";
        String response = "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;
        out.write(response.getBytes());
    }
}
