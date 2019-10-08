package com.dwell.it.exception;

public class InternalMethodInvokeException extends Exception {

    public static final String INTERNAL_METHOD_INVOKE_PREFIX = "Inner_Method_Invoke_Exception-When-Invoked: ";

    public InternalMethodInvokeException(String message) {
        super(message);
    }
}
