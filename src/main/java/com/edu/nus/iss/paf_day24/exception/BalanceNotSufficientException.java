package com.edu.nus.iss.paf_day24.exception;

public class BalanceNotSufficientException extends RuntimeException{
    
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
