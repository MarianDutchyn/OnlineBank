package com.userfront.service.UserServiceImpl;

import com.userfront.Repository.*;
import com.userfront.domain.*;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private PrimaryTransactionRepo primaryTransactionRepo;

    @Autowired
    private SavingsTransactionRepo savingsTransactionRepo;

    @Autowired
    private PrimaryAccountRepo primaryAccountRepo;

    @Autowired
    private SavingsAccountRepo savingsAccountRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipientRepository recipientRepository;


    @Override
    public void savePrimaryTansaction(PrimaryTransaction primaryTransaction) {
       primaryTransactionRepo.save(primaryTransaction);
    }

    @Override
    public void saveSavingsTansaction(SavingsTransaction savingsTransaction) {
        savingsTransactionRepo.save(savingsTransaction);
    }

    @Override
    public List<PrimaryTransaction> getPrimaryTransactions(String username) {
        User user = userService.findByUsername(username);
        List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();
        return primaryTransactionList;
    }

    @Override
    public List<SavingsTransaction> getSavingsTransactions(String username) {
        User user = userService.findByUsername(username);
        List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();
        return savingsTransactionList;
    }

    @Override
    public void betweenAccounts(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {

        if(transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")){
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));

            primaryAccountRepo.save(primaryAccount);
            savingsAccountRepo.save(savingsAccount);

            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Transfer between Accounts from " + transferFrom + " to " + transferTo + "Account", "Transfer", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
            primaryTransactionRepo.save(primaryTransaction);

        } else if(transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")){
                savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
                primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));

                savingsAccountRepo.save(savingsAccount);
                primaryAccountRepo.save(primaryAccount);

                Date date = new Date();
                SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer between Accounts from " + transferFrom + " to " + transferTo + "Account", "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
                savingsTransactionRepo.save(savingsTransaction);
            }
        }

    @Override
    public List<Recipient> findRecipientList(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Recipient> recipientList = recipientRepository.findAll().stream()
                .filter(recipient -> user.getUsername().equalsIgnoreCase(recipient.getUser().getUsername()))
                .collect(Collectors.toList());
        return recipientList;
    }

    @Override
    public void saveRecipient(Recipient recipient) {
        recipientRepository.save(recipient);
    }

    @Override
    public Recipient findByRecipientName(String recipientName) {
       Recipient recipient = recipientRepository.findByName(recipientName);
        return recipient;
    }

    @Override
    public void deleteRecipientByName(String recipientName) {
        recipientRepository.deleteByName(recipientName);
    }

    @Override
    public void toSomeoneElse(Recipient recipient, String accountType, double amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {
        if (accountType.equalsIgnoreCase("Primary")){
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountRepo.save(primaryAccount);

            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Transfer to " + recipient.getName(), "Transfer", "Finished",amount, primaryAccount.getAccountBalance(), primaryAccount);
            primaryTransactionRepo.save(primaryTransaction);
        } else if(accountType.equalsIgnoreCase("Savings")){
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountRepo.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to " + recipient.getName(), "Transfer", "Finished",amount, savingsAccount.getAccountBalance(), savingsAccount);
            savingsTransactionRepo.save(savingsTransaction);
        }
    }
}
