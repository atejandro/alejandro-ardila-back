package com.atejandro.examples.exception;

/**
 * Created by atejandro on 13/06/17.
 */
public class UserNotFoundException extends Throwable {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
