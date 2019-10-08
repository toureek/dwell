package com.dwell.it.exception;

public class DBManipulateException extends RuntimeException {

    public DBManipulateException(String message) {
        super(message);
    }  // 只处理数据库操作时 引发的问题
}
