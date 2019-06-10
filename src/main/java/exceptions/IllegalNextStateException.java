package exceptions;

public class IllegalNextStateException extends Exception {

    public IllegalNextStateException() {
        super();
    }

    public IllegalNextStateException(String message) {
        super(message);
    }

    public IllegalNextStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNextStateException(Throwable cause) {
        super(cause);
    }

    protected IllegalNextStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
