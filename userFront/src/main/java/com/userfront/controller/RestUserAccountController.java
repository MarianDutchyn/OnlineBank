package com.userfront.controller;

import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class RestUserAccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/user/getAll", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @RequestMapping(value = "/user/primaryTransaction")
    public List<PrimaryTransaction> getPrimaryTransaction(@RequestParam("username") String username){
        return transactionService.getPrimaryTransactions(username);
    }

    @RequestMapping(value = "/user/savingsTransaction")
    public List<SavingsTransaction> getSavingsTransaction(@RequestParam("username") String username){
        return transactionService.getSavingsTransactions(username);
    }

    @RequestMapping(value = "/user/{username}/enable", method = RequestMethod.GET)
    public void enableUser(@PathVariable("username") String username){
        userService.enabledUser(username);
    }

    @RequestMapping(value = "/user/{username}/disable", method = RequestMethod.GET)
    public void disableUser(@PathVariable("username") String username){
        userService.disableUser(username);
    }

}
