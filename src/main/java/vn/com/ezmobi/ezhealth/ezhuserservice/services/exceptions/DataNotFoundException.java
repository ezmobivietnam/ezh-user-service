package vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions;

/**
 * Created by ezmobivietnam on 2021-01-06.
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }
}
