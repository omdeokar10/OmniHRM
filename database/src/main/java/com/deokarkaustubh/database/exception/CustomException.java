package com.deokarkaustubh.database.exception;


public class CustomException extends Exception {
    public CustomException(String cause) {
        super(cause);
        System.out.println("Exception caused by:" + cause);
    }
}
