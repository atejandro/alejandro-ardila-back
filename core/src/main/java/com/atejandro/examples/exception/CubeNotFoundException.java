package com.atejandro.examples.exception;

/**
 * Created by atejandro on 14/06/17.
 */
public class CubeNotFoundException extends Exception {
    public CubeNotFoundException(String s) {
        super(s);
    }

    public CubeNotFoundException() {
    }
}
