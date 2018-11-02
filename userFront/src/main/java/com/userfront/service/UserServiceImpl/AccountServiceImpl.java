package com.userfront.service.UserServiceImpl;

import com.userfront.Repository.PrimaryAccountRepo;
import com.userfront.Repository.SavingsAccountRepo;
import com.userfront.domain.*;
import com.userfront.service.AccountService;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    private static int counter = 0;

    @Autowired
    private PrimaryAccountRepo primaryAccountRepo;

    @Autowired
    private SavingsAccountRepo savingsAccountRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;


    @Override
    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        primaryAccount.setAccountNumber(++counter);

        primaryAccountRepo.save(primaryAccount);

        return primaryAccountRepo.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    @Override
    public SavingsAccount createSavingAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(++counter);

        savingsAccountRepo.save(savingsAccount);

        return savingsAccountRepo.findByAccountNumber(savingsAccount.getAccountNumber());
    }

    @Override
    public void deposit(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")){
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(new BigDecimal(amount));
            primaryAccountRepo.save(primaryAccount);

            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account", accountType, "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryTansaction(primaryTransaction);
        }
        else if(accountType.equalsIgnoreCase("Savings")){
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(new BigDecimal(amount));
            savingsAccountRepo.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to Savings Account", accountType, "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsTansaction(savingsTransaction);
        }
    }

    @Override
    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")){
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountRepo.save(primaryAccount);

            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account", accountType, "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryTansaction(primaryTransaction);
        }
        else if(accountType.equalsIgnoreCase("Savings")){
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountRepo.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from Savings Account", accountType, "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsTansaction(savingsTransaction);
        }
    }
}
