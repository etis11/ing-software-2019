package exceptions;

public class IllegalHavingException extends Exception{
    public IllegalHavingException() {
        super();
    }

    public IllegalHavingException(String message) {
        super(message);
    }

    public IllegalHavingException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalHavingException(Throwable cause) {
        super(cause);
    }

    protected IllegalHavingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
