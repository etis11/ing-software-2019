package exceptions;

public class NotValidMoves extends IllegalArgumentException {
    public NotValidMoves() {
        super();
    }

    public NotValidMoves(String s) {
        super(s);
    }

    public NotValidMoves(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidMoves(Throwable cause) {
        super(cause);
    }
}
