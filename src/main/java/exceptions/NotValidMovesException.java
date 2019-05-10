package exceptions;

public class NotValidMovesException extends IllegalArgumentException {
    public NotValidMovesException() {
        super();
    }

    public NotValidMovesException(String s) {
        super(s);
    }

    public NotValidMovesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidMovesException(Throwable cause) {
        super(cause);
    }
}
