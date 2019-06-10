package exceptions;

public class NotValidActionException extends Exception {

    public NotValidActionException() {
        super();
    }

    public NotValidActionException(String message) {
        super(message);
    }

    public NotValidActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidActionException(Throwable cause) {
        super(cause);
    }

    protected NotValidActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
