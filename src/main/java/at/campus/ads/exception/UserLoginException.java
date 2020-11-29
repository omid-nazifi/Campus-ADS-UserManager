package at.campus.ads.exception;

public class UserLoginException extends Exception {

    public UserLoginException() {
        super();
    }

    public UserLoginException(String message) {
        super(message);
    }

    public UserLoginException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
