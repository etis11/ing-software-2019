package exceptions;

public class PickableNotPresentException extends RuntimeException {
    public PickableNotPresentException() {
        super();
    }

    public PickableNotPresentException(String message) {
        super(message);
    }

    public PickableNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PickableNotPresentException(Throwable cause) {
        super(cause);
    }

    protected PickableNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
