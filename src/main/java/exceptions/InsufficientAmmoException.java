package exceptions;

public class InsufficientAmmoException extends Exception {
    public InsufficientAmmoException() {
        super();
    }

    public InsufficientAmmoException(String message) {
        super(message);
    }

    public InsufficientAmmoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientAmmoException(Throwable cause) {
        super(cause);
    }

    protected InsufficientAmmoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
