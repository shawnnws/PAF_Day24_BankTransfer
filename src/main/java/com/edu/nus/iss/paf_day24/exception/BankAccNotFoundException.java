package com.edu.nus.iss.paf_day24.exception;

/*
 * Custom exception class for if requested BankAccount is not found in our database.
 * The functions are standard.
 * 1. Empty constructor.
 * 2. With message.
 * 3. With message and cause.
 * 4. With cause.
 * IMPORTANT: Will need to know and create a GlobalExceptionHandler besides this custom exception class.
 */


public class BankAccNotFoundException extends RuntimeException{
    
    public BankAccNotFoundException() {
        super();
    }

    public BankAccNotFoundException(String message) {
        super(message);
    }

    public BankAccNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankAccNotFoundException(Throwable cause) {
        super(cause);
    }
}
