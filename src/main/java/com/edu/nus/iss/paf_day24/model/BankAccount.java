package com.edu.nus.iss.paf_day24.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    
    private Integer id;
    private String fullName;
    private Boolean isBlocked;
    private Boolean isActive;
    private String accountType;
    private float balance;
}
