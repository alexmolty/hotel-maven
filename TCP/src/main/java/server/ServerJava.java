package server;

import protocol.ProtocolJava;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerJava implements Runnable {
    ServerSocket serverSocket;
    ProtocolJava protocol;
    int port;

    public ServerJava(ProtocolJava protocol, int port) throws Exception {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.protocol = protocol;
    }
    @Override
    public void run() {
        System.out.println("Listening on port " + port);
        try {
            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    new ServerClientJava(socket, protocol).run();
                } catch (Exception e) {
                    System.out.println("Client error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            if(!serverSocket.isClosed()) {
                System.out.println("Server error: " + e.getMessage());
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (Exception _) {

        }
    }
}
