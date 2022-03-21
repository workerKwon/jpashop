package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {

    /**
     * 
     */
    public NotEnoughStockException() {
    }

    /**
     * @param message
     */
    public NotEnoughStockException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public NotEnoughStockException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
