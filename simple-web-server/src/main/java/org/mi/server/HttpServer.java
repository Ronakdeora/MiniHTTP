package org.mi.server;

import org.mi.controllers.HomeController;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class HttpServer {
    private final ServerSocket serverSocket;
    HomeController controller = new HomeController();
    Router router = new Router();
    Boolean on = true;

    public HttpServer(int port) throws IOException {
        System.out.println("Listening to port : " + port);
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        registerRoutes();

        try {
            while (on){
                try {
                    System.out.println("Waiting For Client");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
//            This Returns the IP of client

                    HttpRequest requestParams = new HttpRequest(clientSocket);
                    System.out.println(requestParams.getRequest());

//            based on the HttpRequest We need to have Routing Logic
                    handleRequest(requestParams, clientSocket.getOutputStream());

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        }finally {
            System.out.println("Closing the Server");
            serverSocket.close();
        }
    }

    private void handleRequest(HttpRequest request, OutputStream outputStream) throws IOException {
        Function<HttpRequest, String> handler = router.getMethod(request.getMethod(), request.getPath());

        if (handler != null) {
            String body = handler.apply(request);
            respond200(outputStream, body);
        } else {
            respond404(outputStream);
        }
    }

    private void registerRoutes() {
        router.register("GET", "/", controller::home);
        router.register("GET", "/about", controller::about);
        router.register("GET", "/end", this::end);
    }

    public String end(HttpRequest request){
        this.on = false;
        return "<h1>Good By Server Turned Off Server!</h1>";
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
