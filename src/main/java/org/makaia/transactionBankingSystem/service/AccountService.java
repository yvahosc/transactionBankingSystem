package org.makaia.transactionBankingSystem.service;

import org.makaia.transactionBankingSystem.dto.dtoAccount.DTOAccountCreation;
import org.makaia.transactionBankingSystem.dto.dtoAccount.DTOAccountDeposit;
import org.makaia.transactionBankingSystem.dto.dtoAccount.DTOAccountTransfer;
import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketConsultIn;
import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketConsultOut;
import org.makaia.transactionBankingSystem.exception.ApiException;
import org.makaia.transactionBankingSystem.model.Account;
import org.makaia.transactionBankingSystem.model.Person;
import org.makaia.transactionBankingSystem.repository.AccountRepository;
import org.makaia.transactionBankingSystem.validation.validationAccount.CreateAccountValidation;
import org.makaia.transactionBankingSystem.validation.validationAccount.DepositToAccountValidation;
import org.makaia.transactionBankingSystem.validation.validationAccount.GetAccountValidation;
import org.makaia.transactionBankingSystem.validation.validationAccount.TransferAccountValidation;
import org.makaia.transactionBankingSystem.validation.validationPerson.CreatePersonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accountRepository;
    CreateAccountValidation createAccountValidation;
    DepositToAccountValidation depositToAccountValidation;
    TransferAccountValidation transferAccountValidation;
    GetAccountValidation getAccountValidation;
    CreatePersonValidation createPersonValidation;

    @Autowired
    public AccountService(AccountRepository accountRepository, CreateAccountValidation createAccountValidation,
                          DepositToAccountValidation depositToAccountValidation, TransferAccountValidation
                           transferAccountValidation, GetAccountValidation getAccountValidation, CreatePersonValidation createPersonValidation)
    {
        this.accountRepository = accountRepository;
        this.createAccountValidation = createAccountValidation;
        this.depositToAccountValidation = depositToAccountValidation;
        this.transferAccountValidation = transferAccountValidation;
        this.getAccountValidation = getAccountValidation;
        this.createPersonValidation = createPersonValidation;
    }

    public ResponseEntity<Account> getAccountById(Long id) throws ApiException {
        getAccountValidation.setId(id);
        String message = getAccountValidation.validationEntryData();
        if(!message.isEmpty()){
            throw new ApiException (400, message);
        }
        Optional<Account> account = this.accountRepository.findById(id);
        if (account.isEmpty()){
            throw new ApiException (404, "La cuenta con id '" + id + "' no se encuentra en la base de datos.");
        }
        return ResponseEntity.ok(account.get());
    }

    public ResponseEntity<Account> createAccount(DTOAccountCreation dtoAccountCreation) throws ApiException {
        createAccountValidation.setDtoAccountCreation(dtoAccountCreation);
        String message = createAccountValidation.validationEntryData();

        if(!message.isEmpty()){
            throw new ApiException (400, message);
        }

        createPersonValidation.setPerson(dtoAccountCreation.getOwner());
        String message2 =  createPersonValidation.validationEntryData();

        if(!message2.isEmpty()){
            throw new ApiException (400, message2);
        }

        Person existingPerson = getPersonById(dtoAccountCreation.getOwner().getId());
        if(existingPerson != null){
            Account account = new Account(Math.round(100000000000000L + Math.random() * 999999999999999L),
                    dtoAccountCreation.getInitialBalance(), dtoAccountCreation.getOwner());
            return ResponseEntity.ok(this.accountRepository.save(account));
        }else{
            ResponseEntity<Person> createdPerson = createPerson(dtoAccountCreation.getOwner());
            Account account = new Account(Math.round(100000000000000L + Math.random() * 999999999999999L),
                    dtoAccountCreation.getInitialBalance(), createdPerson.getBody());
            return ResponseEntity.ok(this.accountRepository.save(account));
        }
    }

    public ResponseEntity<Account> depositInAccount(Long accountNumber, DTOAccountDeposit dtoAccountDeposit) throws
            ApiException
    {
        depositToAccountValidation.setDtoAccountDeposit(dtoAccountDeposit);
        depositToAccountValidation.setAccountNumber(accountNumber);
        String message = depositToAccountValidation.validationEntryData();

        if(!message.isEmpty()){
            throw new ApiException (400, message);
        }

        Account existingAccount = getAccountById(accountNumber).getBody();
        existingAccount.setBalance(existingAccount.getBalance().add(dtoAccountDeposit.getAmount()));
        return ResponseEntity.ok(this.accountRepository.save(existingAccount));
    }

    public ResponseEntity<DTOAccountTransfer> transferToAccount(DTOAccountTransfer dtoAccountTransfer) throws
            ApiException
    {
        transferAccountValidation.setDtoAccountTransfer(dtoAccountTransfer);
        String message = transferAccountValidation.validationEntryData();

        if(!message.isEmpty()){
            throw new ApiException (400, message);
        } else{
            Account existingSourceAccount = getAccountById(dtoAccountTransfer.getSourceAccountNumber()).getBody();
            Account existingDestinationAccount = getAccountById(dtoAccountTransfer.getDestinationAccountNumber()).
                    getBody();

            if(existingDestinationAccount.equals(existingSourceAccount)){
                throw new ApiException (404, "Las cuentas de origen y destino no pueden ser iguales.");
            }

            if(isThereEnoughBalance(existingSourceAccount, dtoAccountTransfer.getAmount())){
                existingSourceAccount.setBalance(existingSourceAccount.getBalance().add(dtoAccountTransfer.getAmount().
                        negate()));
                existingDestinationAccount.setBalance(existingDestinationAccount.getBalance().add(dtoAccountTransfer.
                        getAmount()));
                this.accountRepository.save(existingSourceAccount);
                this.accountRepository.save(existingDestinationAccount);
                return ResponseEntity.ok(dtoAccountTransfer);
            } else{
                throw new ApiException (400, "La cuenta con numero '" + dtoAccountTransfer.getSourceAccountNumber()
                        + "' no tiene suficientes fondos disponibles para realizar la transferencia.");
            }
        }
    }

    public ResponseEntity<List<DTOPocketConsultIn>> getPocketsByAccountNumber(Long accountNumber) throws ApiException {
        Account existingAccount = getAccountById(accountNumber).getBody();

        String uri = "http://localhost:8080/api/pockets/" + existingAccount.getAccountNumber();
        ResponseEntity<DTOPocketConsultOut> response = new RestTemplate().getForEntity(uri, DTOPocketConsultOut.class);
        if (response.getBody().getPockets().isEmpty()){
            throw new ApiException (404, "La cuenta con numero '" + accountNumber + "' no cuenta con bolsillos.");
        }
        return ResponseEntity.ok(response.getBody().getPockets());
    }

    public Person getPersonById(String id){
        String uri = "http://localhost:8080/api/persons/" + id;
        ResponseEntity<Person> response;
        try{
            response = new RestTemplate().getForEntity(uri, Person.class);
        } catch(Exception e){
            return null;
        }
        return response.getBody();
    }

    public ResponseEntity createPerson(Person person){
        String uri = "http://localhost:8080/api/persons";
        return new RestTemplate().postForEntity(uri, person, Person.class);
    }

    private boolean isThereEnoughBalance(Account account, BigDecimal amountToTransfer) {
        return account.getBalance().add(balanceInPocketsOfOneAccount(account.getAccountNumber()).negate())
                .compareTo(amountToTransfer) >= 0;
    }

    public BigDecimal balanceInPocketsOfOneAccount(Long accountNumber){
        BigDecimal sumAmountInPockets = BigDecimal.valueOf(0);
        try{
            DTOPocketConsultOut pocketsInAccount = new DTOPocketConsultOut(getPocketsByAccountNumber(accountNumber).getBody());
            if(!pocketsInAccount.getPockets().isEmpty()){
                for (DTOPocketConsultIn pocket: pocketsInAccount.getPockets()){
                    BigDecimal amountInPocket = pocket.getAmount();
                    sumAmountInPockets = sumAmountInPockets.add(amountInPocket);
                }
            }
        } catch (NullPointerException | ApiException e) {
            sumAmountInPockets = BigDecimal.valueOf(0);
        }
        return sumAmountInPockets;
    }
}
