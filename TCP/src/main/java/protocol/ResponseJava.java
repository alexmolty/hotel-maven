package protocol;

import java.io.Serializable;

public class ResponseJava implements Serializable {
    public TCPResponseCode code;
    public Serializable responseData;

    public ResponseJava(TCPResponseCode code, Serializable responseData) {
        this.responseData = responseData;
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseJava{" +
                "code=" + code +
                ", responseData=" + responseData +
                '}';
    }
}
