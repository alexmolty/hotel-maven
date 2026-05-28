package protocol;

import java.io.Serializable;

public class RequestJava implements Serializable {
    public String requestType;
    public Serializable requestData;
    public RequestJava(String requestType, Serializable requestData) {
        this.requestType = requestType;
        this.requestData = requestData;
    }

    @Override
    public String toString() {
        return "RequestJava{" +
                "requestType='" + requestType + '\'' +
                ", requestData=" + requestData +
                '}';
    }
}
