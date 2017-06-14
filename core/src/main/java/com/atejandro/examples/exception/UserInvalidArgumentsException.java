package com.atejandro.examples.exception;

/**
 * Created by atejandro on 13/06/17.
 */
public class UserInvalidArgumentsException extends Exception {
    public UserInvalidArgumentsException() {
    }

    public UserInvalidArgumentsException(String message) {
        super(message);
    }
}
