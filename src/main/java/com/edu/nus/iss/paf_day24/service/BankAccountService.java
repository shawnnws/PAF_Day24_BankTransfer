package com.edu.nus.iss.paf_day24.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.nus.iss.paf_day24.exception.AccountBlockedAndInactiveException;
import com.edu.nus.iss.paf_day24.exception.BalanceNotSufficientException;
import com.edu.nus.iss.paf_day24.model.BankAccount;
import com.edu.nus.iss.paf_day24.repository.BankAccountRepo;

@Service
public class BankAccountService {
    
    @Autowired
    BankAccountRepo bankAccountRepo;

    public BankAccount retrieveAccountById(Integer accountId) {

        BankAccount bankAccount = bankAccountRepo.getAccountById(accountId);
        return bankAccount;

        //Alternatively to test our custom exception.
        // BankAccount bankAccount = bankAccountRepo.getAccountById(accountId);
        // System.out.println("BankAccountService > retrieveBankAccountId > " + bankAccount.toString());

        // return bankAccount;
    }

    public Boolean createAccount(BankAccount bankAccount) {
        return bankAccountRepo.createAccount(bankAccount);
    }

    /*
     * Transactional: encompass in a single unit of work.
     * 1. Writing of records to more than one table
     * 2. Update more than one record in the same table
     */
    @Transactional
    public Boolean transferMoney(Integer withdrawAccountId, Integer depositAccountId, Float transferAmount) {
        /*
         * Transactional logic
         * 1. Check that the transfer account exists.
         * 2. Check that the receiving account exists.
         * 3. Check that both accounts are not blocked and active.
         * 4. Check that the transfer account has sufficient balance for the transfer amount.
         */

        // 1. Check that the transfer account exists.
        Boolean transferAccountExists = false;
        BankAccount transferBankAccount = bankAccountRepo.getAccountById(withdrawAccountId);
        if (transferBankAccount != null) {
            transferAccountExists = true;
        }

        // 2. Check that the receiving account exists.
        Boolean receiverAccountExists = false;
        BankAccount receiverBankAccount = bankAccountRepo.getAccountById(depositAccountId);
        if (receiverBankAccount != null) {
            receiverAccountExists = true;
        }

        // 3. Check that both accounts are not blocked and active.
        Boolean transferAllowed = false;
        if (transferBankAccount.getIsActive() && transferBankAccount.getIsBlocked()) {
            transferAllowed = true;
        }

        Boolean receiveAllowed = false;
        if (receiverBankAccount.getIsActive() && receiverBankAccount.getIsBlocked()) {
            receiveAllowed = true;
        }

        // 4. Check that the transfer account has sufficient balance for the transfer amount.
        Boolean sufficientBalance = false;
        if (transferBankAccount.getBalance() > transferAmount) {
            sufficientBalance = true;
        }

        if (transferAccountExists && receiverAccountExists && transferAllowed && receiveAllowed && sufficientBalance) {
            /*
             * Allow transfer operations.
             * Else throw exceptions accordingly.
             */

            // Simulating exception before first operation.
            // if (true) {
            
            // }

            bankAccountRepo.withdrawAmount(transferAmount, withdrawAccountId);
            bankAccountRepo.depositAmount(transferAmount, depositAccountId);
        } else {
            if (!transferAllowed) {
                throw new AccountBlockedAndInactiveException("Transfer account either blocked or inactive.");
            }
            if (!receiveAllowed) {
                throw new AccountBlockedAndInactiveException("Receiving account either blocked or inactive.");
            }
            if (!sufficientBalance) {
                throw new BalanceNotSufficientException("Transfer account balance not sufficient.");
            }
        }

        return true;
    }
}
