package com.edu.nus.iss.paf_day24.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    
    private Integer statusCode;
    private Date timeStamp;
    private String message;
    private String description;
}
