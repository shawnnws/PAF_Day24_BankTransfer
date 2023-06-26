package com.edu.nus.iss.paf_day24.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.nus.iss.paf_day24.exception.BankAccNotFoundException;
import com.edu.nus.iss.paf_day24.model.BankAccount;

@Repository
public class BankAccountRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    // Important to test out SQL queries in SQL workbench first before defining repo class.
    private final String GET_ACCOUNT_SQL = "select * from bank_account where id = ?";
    // Withdraw operation for specified account id.
    private final String WITHDRAW_SQL =  "update bank_account set balance = balance - ? where id = ?";
    // Deposit operation for specified account id.
    private final String DEPOSIT_SQL = "update bank_account set balance = balance + ? where id = ?";
    private final String CREATE_ACCOUNT_SQL = "insert into bank_account (full_name, is_blocked, is_active, account_type, balance) values (?, ?, ?, ?, ?)";
    // private final String CREATE_ACCOUNT_SQL = "insert into bank_account values (?, ?, ?, ?, ?)";

    // RowMapper used for queryForObject: Retrieving an account object using this function with the bankAccountId as the query key.
    public BankAccount getAccountById(Integer bankAccountId) {
        // BankAccount bankAccount = jdbcTemplate.queryForObject(GET_ACCOUNT_SQL, BeanPropertyRowMapper
        //     .newInstance(BankAccount.class), bankAccountId);

        // if (bankAccount == null) {
        //     throw new BankAccountNotFoundException("Account not created");
        // }

        List<BankAccount> bankAccounts = jdbcTemplate.query(GET_ACCOUNT_SQL, BeanPropertyRowMapper.newInstance(BankAccount.class), bankAccountId);

        if (bankAccounts.isEmpty()) {
             throw new BankAccNotFoundException("Account doesn't not exist.");
        }

        BankAccount bankAccount = bankAccounts.get(0);

        return bankAccount;
    }

    public Boolean withdrawAmount(Float withdrawAmount, Integer withdrawAccountId) {

        /*
         * First parameter is the SQL function.
         * Subsequent parameters to follow as per order defined in our WITHDRAW_SQL function.
         */
        Integer iResult = jdbcTemplate.update(WITHDRAW_SQL, withdrawAmount, withdrawAccountId);

        // Return whether iResult is 0 or 1 with condition being true or false.
        return iResult > 0 ? true : false;
    }

    // Deposit same as withdraw
    public Boolean depositAmount(Float depositAmount, Integer depositAccountId) {

        Integer iResult = jdbcTemplate.update(DEPOSIT_SQL, depositAmount, depositAccountId);

        return iResult > 0 ? true : false;
    }

    // Data type to return depends on what we want, can return account id upon creation or simply just a boolean value.
    public Boolean createAccount(BankAccount bankAccount) {
        
        Integer iResult = jdbcTemplate.update(CREATE_ACCOUNT_SQL, bankAccount.getFullName(), 
            bankAccount.getIsBlocked(), bankAccount.getIsActive(), bankAccount.getAccountType(), bankAccount.getBalance());

        return iResult > 0 ? true : false;
    }

    
}   
