package exceptions;

public class PickableNotPresent extends  RuntimeException{
    public PickableNotPresent() {
        super();
    }

    public PickableNotPresent(String message) {
        super(message);
    }

    public PickableNotPresent(String message, Throwable cause) {
        super(message, cause);
    }

    public PickableNotPresent(Throwable cause) {
        super(cause);
    }

    protected PickableNotPresent(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
