package com.userfront.Controller;

import com.userfront.domain.*;
import com.userfront.service.AccountService;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/primaryAccount", method = RequestMethod.GET)
    public String primaryAccount(Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        List<PrimaryTransaction> primaryTransactionList = transactionService.getPrimaryTransactions(principal.getName());

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("primaryTransactionList", primaryTransactionList);
        model.addAttribute("user", user);

        return "primaryAccount";
    }

    @RequestMapping(value = "/savingsAccount", method = RequestMethod.GET)
    public String savingsAccount(Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();
        List<SavingsTransaction> savingsTransactionList = transactionService.getSavingsTransactions(principal.getName());

        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsTransactionList", savingsTransactionList);
        model.addAttribute("user", user);

        return "savingsAccount";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model){
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositPost(@ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal){
        accountService.deposit(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userPage";
    }
    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model){
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdraw(@ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal){
        accountService.withdraw(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userPage";
    }
}
