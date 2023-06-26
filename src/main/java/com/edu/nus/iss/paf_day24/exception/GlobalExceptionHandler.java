package com.edu.nus.iss.paf_day24.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

/*
 * The idea here is that we can customized different error message displays according to what we want for each
 * different exceptions that we may encounter.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankAccNotFoundException.class)
    public ModelAndView handleBankAccountNotFoundException(BankAccNotFoundException ex, HttpServletRequest request) {

        // Forming the custom error message according to what we want to be displayed. Attributes are according to our ErrorMessage exception class.
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(404);
        errMsg.setTimeStamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        // This function will return a model and a view.
        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", errMsg);
        return mav;
    }

    @ExceptionHandler(AccountBlockedAndInactiveException.class)
    public ModelAndView handleAccountBlockedAndInactiveException(AccountBlockedAndInactiveException ex, HttpServletRequest request) {

        // Forming the custom error message according to what we want to be displayed. Attributes are according to our ErrorMessage exception class.
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(404);
        errMsg.setTimeStamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        // This function will return a model and a view.
        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", errMsg);
        return mav;
    }

    @ExceptionHandler(BalanceNotSufficientException.class)
    public ModelAndView handleBalanceNotSufficientException(BalanceNotSufficientException ex, HttpServletRequest request) {

        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(404);
        errMsg.setTimeStamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", errMsg);
        return mav;
    }

    // Alternative method for handling BalanceNotSufficientException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleBalanceNotSufficientException(IllegalArgumentException ex,
            HttpServletRequest request) {
        // forming the custom error message
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(400);
        errMsg.setTimeStamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        // return the error 
        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.BAD_REQUEST);
    }


    // Internal Server Error: Happens when our API encounter a generic error related to logic for example.
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorMessage> handleHttpServerErrorException(HttpServerErrorException ex, HttpServletRequest request) {
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(400);
        errMsg.setTimeStamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());
        
        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
