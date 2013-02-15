package com.badtuned.telnetecho.io;

/**
 * Simple custom runtime exception in file access operations.
 * 
 * @author Janne Valkealahti
 *
 */
public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = -6988526909037164100L;

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

}
