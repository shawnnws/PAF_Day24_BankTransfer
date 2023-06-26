package com.edu.nus.iss.paf_day24.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.nus.iss.paf_day24.model.BankAccount;
import com.edu.nus.iss.paf_day24.service.BankAccountService;

// This is what we call an API.

@RestController
@RequestMapping("/api/bankaccounts")
public class BankAccountController {
    
    @Autowired
    BankAccountService bankAccountService;

    /*
     * To return a responseentity with type corresponding to the data type the create function returns in service layer.
     * We use RequestBody because we are retrieving the body info of the created bank account using our service layer function.
     */
    @PostMapping
    public ResponseEntity<Boolean> createAccount(@RequestBody BankAccount bankAccount) {

        Boolean bankAccountCreated = bankAccountService.createAccount(bankAccount);

        if (bankAccountCreated) {
            return ResponseEntity.ok().body(bankAccountCreated);
        } else {
            // Return error body. Exception or custom exception handling info.
            return ResponseEntity.internalServerError().body(bankAccountCreated);
        }
    }

    /*
     * We use PathVariable here because we are retrieving the bank account 
     * info using what is passed into the URI defined by {account-id}.
     */
    @GetMapping("/{account-id}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable("account-id") Integer id) {
        BankAccount bankAccount = bankAccountService.retrieveAccountById(id);

        // This is just one of the ways to return the info retrieved.
        return new ResponseEntity<BankAccount>(bankAccount, HttpStatus.OK);
    }

    @PostMapping("/transfer/{transferer-id}/receiver/{receiver-id}/amount/{amount}")
    public ResponseEntity<Boolean> transferMoney(@PathVariable("transferer-id") Integer transferAccountId,
        @PathVariable("receiver-id") Integer receiverAccountId, @PathVariable("amount") Float transferAmount) {

            Boolean transferSuccess = bankAccountService.transferMoney(transferAccountId, receiverAccountId, transferAmount);
            
            return new ResponseEntity<Boolean>(transferSuccess, HttpStatus.OK);
        }
}
