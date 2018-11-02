package com.userfront.service;

import com.userfront.domain.*;

import java.security.Principal;
import java.util.List;

public interface TransactionService {

    void savePrimaryTansaction(PrimaryTransaction primaryTransaction);
    void saveSavingsTansaction(SavingsTransaction savingsTransaction);
    List<PrimaryTransaction> getPrimaryTransactions(String username);
    List<SavingsTransaction> getSavingsTransactions(String username);

    void betweenAccounts(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount);

    List<Recipient> findRecipientList(Principal principal);
    void saveRecipient(Recipient recipient);
    Recipient findByRecipientName(String recipientName);
    void deleteRecipientByName(String recipientName);

    void toSomeoneElse(Recipient recipient, String accountType, double amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount);

}
