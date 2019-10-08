package com.dwell.it.exception;

public class MessageRuntimeException extends RuntimeException {

    public MessageRuntimeException(String message) {
        super(message);
    }  // 只处理servcie到controller的reponse信息交互
}
