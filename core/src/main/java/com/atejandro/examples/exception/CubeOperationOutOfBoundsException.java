package com.atejandro.examples.exception;

/**
 * Created by atejandro on 10/06/17.
 */
public class CubeOperationOutOfBoundsException extends ArrayIndexOutOfBoundsException {
    public CubeOperationOutOfBoundsException(String msg){
        super(msg);
    }
}
