package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dto.Account;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.AccountService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account")
    @ResponseBody
    public Account getAccount(
        @RequestParam(name="username", required=true) String username,
        @RequestParam(name="password", required=true) String password
    ) {
        return accountService.getAccount(username, password);
    }

    @GetMapping("/seeUpgrades")
    @ResponseBody
    public String getUpgrades(
        @RequestParam(name="username", required=true) String username,
        @RequestParam(name="password", required=true) String password
    ){
        return accountService.getUpgrades(username, password);
    }

    @PostMapping("/upgradeTier")
    @ResponseBody
    public String upgradeTier(
        @RequestParam(name="username", required=true) String username,
        @RequestParam(name="password", required=true) String password,
        @RequestParam(name="tier", required=true) int tier
    ){
        return accountService.upgradeTier(username, password, tier);
    }

    @PostMapping("/cancelSubscription")
    @ResponseBody
    public String cancelSubscription(
        @RequestParam(name="username", required=true) String username,
        @RequestParam(name="password", required=true) String password
    ){
        return accountService.cancelSubscription(username, password);
    }
}

