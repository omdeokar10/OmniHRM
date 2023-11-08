package com.github.bostonworks.omnihrm.Logtime.exception;

public class UserNotPresent extends Exception {
    public UserNotPresent(String cause) {
        super(cause);
        System.out.println("User not present in the database:" + cause);
    }
}
