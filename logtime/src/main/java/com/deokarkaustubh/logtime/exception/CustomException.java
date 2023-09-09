package com.deokarkaustubh.logtime.exception;


public class CustomException extends Exception {
    public CustomException(String cause) {
        super(cause);
        System.out.println("Exception caused by:" + cause);
    }
}
