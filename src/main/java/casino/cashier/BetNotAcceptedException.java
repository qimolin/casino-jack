package casino.cashier;
/**
 * the behaviour of this class does not have to be tested
 */

public class BetNotAcceptedException extends Exception {
    public BetNotAcceptedException() {
    }

    public BetNotAcceptedException(String message) {
        super(message);
    }

    public BetNotAcceptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BetNotAcceptedException(Throwable cause) {
        super(cause);
    }

    public BetNotAcceptedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
