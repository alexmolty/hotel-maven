package server;

import protocol.ProtocolJava;
import protocol.RequestJava;
import protocol.ResponseJava;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static protocol.TCPResponseCode.UNKNOWN;
import static protocol.TCPResponseCode.WRONG_REQUEST;

public class ServerClientJava implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private ProtocolJava protocol;

    public ServerClientJava(Socket socket, ProtocolJava protocol) {
        this.socket = socket;
        this.protocol = protocol;
    }

    @Override
    public void run() {
        try(Socket s = this.socket;
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
            out.flush();
            while(true) {
                Object obj = in.readObject();
                ResponseJava response;
                if(!(obj instanceof RequestJava request)){
                    response = new ResponseJava(WRONG_REQUEST, null);
                } else  {
                    try {
                        response = protocol.getResponse(request);
                        if (response == null) {
                            response = new ResponseJava(UNKNOWN, null);
                        }
                    } catch (Exception e) {
                        response = new ResponseJava(WRONG_REQUEST, null);
                    }
                }
                out.writeObject(response);
                out.flush();
            }
        } catch (EOFException e) {
            System.out.println("Client closed connection");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
