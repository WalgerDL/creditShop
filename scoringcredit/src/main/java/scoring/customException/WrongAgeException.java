package scoring.customException;

public class WrongAgeException extends Exception {
    public WrongAgeException() {
    }

    public WrongAgeException(String message) {
        super(message);
    }

    public WrongAgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAgeException(Throwable cause) {
        super(cause);
    }

    public WrongAgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
