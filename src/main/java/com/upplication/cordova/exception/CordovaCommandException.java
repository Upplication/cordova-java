package com.upplication.cordova.exception;

/**
 * Exception when a cordova command not end as expected
 */
public class CordovaCommandException extends IllegalStateException{

    public CordovaCommandException(String msg) {
        super(msg);
    }
}
