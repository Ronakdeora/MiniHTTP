package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {
        System.out.println("Listening to port : " + port);
        serverSocket = new ServerSocket(port);
        start();
    }

    private void start() {
        try {
            System.out.println("Waiting For Client");
            Socket clientSocket = serverSocket.accept();
//            This Returns the IP of client
            System.out.println("Client connected: " + clientSocket.getInetAddress());
            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            HttpRequest requestParams = new HttpRequest(reader);
            System.out.println(requestParams.getRequest());
            Router route = new Router(requestParams,clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
