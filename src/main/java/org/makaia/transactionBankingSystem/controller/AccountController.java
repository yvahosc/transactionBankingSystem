package org.makaia.transactionBankingSystem.controller;

import org.makaia.transactionBankingSystem.dto.dtoAccount.DTOAccountCreation;
import org.makaia.transactionBankingSystem.dto.dtoAccount.DTOAccountDeposit;
import org.makaia.transactionBankingSystem.dto.dtoAccount.DTOAccountTransfer;
import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketConsultIn;
import org.makaia.transactionBankingSystem.exception.ApiException;
import org.makaia.transactionBankingSystem.model.Account;
import org.makaia.transactionBankingSystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Account> getAccount (@PathVariable Long id) throws ApiException {
        return this.accountService.getAccountById(id);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody DTOAccountCreation dtoAccountCreation) throws ApiException {
        return this.accountService.createAccount(dtoAccountCreation);
    }

    @PostMapping("/{account_number}")
    public ResponseEntity<Account> depositAccount(@PathVariable Long account_number, @RequestBody DTOAccountDeposit dtoAccountDeposit) throws ApiException {
        return this.accountService.depositInAccount(account_number, dtoAccountDeposit);
    }

    @PostMapping("/transfer")
    public ResponseEntity<DTOAccountTransfer> transferAccount(@RequestBody DTOAccountTransfer dtoAccountTransfer) throws ApiException {
        return this.accountService.transferToAccount(dtoAccountTransfer);
    }

    @GetMapping("/{accountNumber}/pockets")
    public ResponseEntity<List<DTOPocketConsultIn>> getPockets (@PathVariable Long accountNumber) throws ApiException {
        return this.accountService.getPocketsByAccountNumber(accountNumber);
    }
}
