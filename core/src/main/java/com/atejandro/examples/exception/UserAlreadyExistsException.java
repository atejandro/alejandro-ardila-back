package com.atejandro.examples.exception;

/**
 * Created by atejandro on 13/06/17.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
