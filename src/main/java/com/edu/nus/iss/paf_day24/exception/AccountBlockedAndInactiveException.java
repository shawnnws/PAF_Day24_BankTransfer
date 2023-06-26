package com.edu.nus.iss.paf_day24.exception;

public class AccountBlockedAndInactiveException extends RuntimeException{

    public AccountBlockedAndInactiveException(String message) {
        super(message);
    }
}
