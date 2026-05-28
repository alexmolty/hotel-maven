package client;

import protocol.RequestJava;
import protocol.ResponseJava;
import protocol.TCPResponseCode.*;

import java.io.*;
import java.net.Socket;

import static protocol.TCPResponseCode.OK;

public class TCPClientJava implements Closeable {
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    protected Socket socket;

    public TCPClientJava(String hostName, int port) throws IOException {
        this.socket = new Socket(hostName, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.out.flush();
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    @SuppressWarnings("unchecked")
    protected <T> T sendRequest(String requestType, Serializable requestData){
        try {
            RequestJava request = new RequestJava(requestType, requestData);
            out.writeObject(request);
            ResponseJava response = (ResponseJava)  in.readObject();
            if(response.code!= OK) {
                throw new Exception(response.code.toString());
            }
            return (T) response.responseData;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
