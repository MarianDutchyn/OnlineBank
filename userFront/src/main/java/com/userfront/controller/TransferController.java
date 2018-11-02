package com.userfront.Controller;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.Recipient;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.GET)
    public String betweenAccounts(Model model){
       model.addAttribute("transferFrom", "");
       model.addAttribute("transferTo", "");
       model.addAttribute("amount", "");
       return "betweenAccounts";
    }

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.POST)
    public String betweenAccounts(@ModelAttribute("transferFrom") String transferFrom, @ModelAttribute("transferTo") String transferTo,
                                  @ModelAttribute("amount") String amount, Principal principal){
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        transactionService.betweenAccounts(transferFrom, transferTo, amount, primaryAccount, savingsAccount);

        return "redirect:/userPage";
    }

    @RequestMapping(value = "/recipient", method = RequestMethod.GET)
    public String recipient(Model model, Principal principal){
        Recipient recipient = new Recipient();
        List<Recipient> recipientList = transactionService.findRecipientList(principal);
        model.addAttribute("recipient", recipient);
        model.addAttribute("recipientList", recipientList);
        return "recipient";
    }

    @RequestMapping(value = "/recipient/save", method = RequestMethod.POST)
    public String recipientPost(@ModelAttribute("recipient") Recipient recipient, Principal principal){
        User user = userService.findByUsername(principal.getName());
        recipient.setUser(user);
        transactionService.saveRecipient(recipient);

        return "redirect:/transfer/recipient";
    }

    @RequestMapping(value = "/recipient/edit", method = RequestMethod.GET)
    public String recipientEdit(@RequestParam("recipientName") String recipientName, Model model, Principal principal){
        Recipient recipient = transactionService.findByRecipientName(recipientName);
        List<Recipient> recipientList = transactionService.findRecipientList(principal);
        model.addAttribute("recipient", recipient);
        model.addAttribute("recipientList", recipientList);

        return "recipient";
    }

    @RequestMapping(value = "/recipient/delete", method = RequestMethod.GET)
    public String recipientDelete(@RequestParam("recipientName") String recipientName, Model model, Principal principal){
        transactionService.deleteRecipientByName(recipientName);
        Recipient recipient = new Recipient();
        List<Recipient> recipientList = transactionService.findRecipientList(principal);
        model.addAttribute("recipient", recipient);
        model.addAttribute("recipientList", recipientList);

        return "recipient";
    }

    @RequestMapping(value = "/toSomeoneElse", method = RequestMethod.GET)
    public String toSomeoneElse(Model model, Principal principal){
      List<Recipient> recipientList = transactionService.findRecipientList(principal);
      model.addAttribute("recipientList", recipientList);
      model.addAttribute("accountType","");
      return "toSomeoneElse";
    }

    @RequestMapping(value = "/toSomeoneElse", method = RequestMethod.POST)
    public String toSomeoneElsePost(@ModelAttribute("recipientName") String recipientName, @ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal){
        User user = userService.findByUsername(principal.getName());
       Recipient recipient = transactionService.findByRecipientName(recipientName);
       transactionService.toSomeoneElse(recipient, accountType, Double.parseDouble(amount), user.getPrimaryAccount(), user.getSavingsAccount());
        return "redirect:/userPage";
    }
}
