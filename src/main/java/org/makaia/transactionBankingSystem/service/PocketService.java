package org.makaia.transactionBankingSystem.service;

import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketConsultIn;
import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketConsultOut;
import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketCreation;
import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketTransfer;
import org.makaia.transactionBankingSystem.exception.ApiException;
import org.makaia.transactionBankingSystem.model.Account;
import org.makaia.transactionBankingSystem.model.Pocket;
import org.makaia.transactionBankingSystem.repository.PocketRepository;
import org.makaia.transactionBankingSystem.validation.validationPocket.CreatePocketValidation;
import org.makaia.transactionBankingSystem.validation.validationPocket.TransferPocketValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PocketService {

    PocketRepository pocketRepository;
    CreatePocketValidation createPocketValidation;
    TransferPocketValidation transferPocketValidation;

    @Autowired
    public PocketService(PocketRepository pocketRepository, CreatePocketValidation createPocketValidation,
                         TransferPocketValidation transferPocketValidation)
    {
        this.pocketRepository = pocketRepository;
        this.createPocketValidation = createPocketValidation;
        this.transferPocketValidation = transferPocketValidation;
    }
    public ResponseEntity<DTOPocketConsultOut> getPocketsByAccountNumber(Long accountNumber) {
        List<Pocket> pockets = this.pocketRepository.findByAccountAccountNumber(accountNumber);

        List<DTOPocketConsultIn> pocketsDTO = new ArrayList<>();
        for (Pocket pocket: pockets){
            pocketsDTO.add(new DTOPocketConsultIn(pocket.getNumberPocket(), pocket.getName(), pocket.getAmount()));
        }
        return ResponseEntity.ok(new DTOPocketConsultOut(pocketsDTO));
    }

    public ResponseEntity<DTOPocketCreation> createPocket(DTOPocketCreation dtoPocketCreation) throws ApiException {
        createPocketValidation.setDtoPocketCreation(dtoPocketCreation);
        String message = createPocketValidation.validationEntryData();

        if(!message.isEmpty()){
            throw new ApiException (400, message);
        }

        Account existingAccount = getAccountById(dtoPocketCreation.getAccountNumber());

        if(existingAccount == null){
            throw new ApiException (404, "La cuenta con numero '" + dtoPocketCreation.getAccountNumber() +
                    "' no existe en la base de datos.");
        } else{
            if(isThereEnoughBalance(existingAccount, dtoPocketCreation.getInitialBalance())){
                Pocket pocket = new Pocket(dtoPocketCreation.getName(), dtoPocketCreation.getInitialBalance(),
                        existingAccount);
                this.pocketRepository.save(pocket);
                return ResponseEntity.ok(dtoPocketCreation);
            } else{
                throw new ApiException (400, "La cuenta con numero '" + dtoPocketCreation.getAccountNumber() +
                        "' no tiene suficientes fondos disponibles para enviar al bolsillo.");
            }
        }
    }

    public ResponseEntity<DTOPocketTransfer> transferToPocket(DTOPocketTransfer dtoPocketTransfer) throws ApiException {
        transferPocketValidation.setDtoPocketTransfer(dtoPocketTransfer);
        String message = transferPocketValidation.validationEntryData();

        if(!message.isEmpty()){
            throw new ApiException (400, message);
        }

        Account existingAccount = getAccountById(dtoPocketTransfer.getAccountNumber());
        Pocket existingPocket = getPocketByIdByAccount(dtoPocketTransfer.getPocketNumber(), dtoPocketTransfer
                .getAccountNumber());

        if(existingAccount == null){
            throw new ApiException (404, "La cuenta con numero '" + dtoPocketTransfer.getAccountNumber() +
                    "' no existe en la base de datos.");
        } else if(existingPocket == null){
            throw new ApiException (404, "El bolsillo con numero '" + dtoPocketTransfer.getPocketNumber() +
                    "' correspondiente a la cuenta '" + dtoPocketTransfer.getAccountNumber() + "' no existe en la base de datos.");
        }
        else{
            if(isThereEnoughBalance(existingAccount, dtoPocketTransfer.getAmount())){
                existingPocket.setAmount(existingPocket.getAmount().add(dtoPocketTransfer.getAmount()));
                this.pocketRepository.save(existingPocket);
                dtoPocketTransfer.setAmount(existingPocket.getAmount());
                return ResponseEntity.ok(dtoPocketTransfer);
            } else{
                throw new ApiException (400, "La cuenta con numero '" + dtoPocketTransfer.getAccountNumber() +
                        "' no tiene suficientes fondos disponibles para enviar al bolsillo.");
            }
        }
    }

    private Pocket getPocketByIdByAccount(Long pocketNumber, Long accountNumber) {
        return this.pocketRepository.findByNumberPocketAndAccountAccountNumber(pocketNumber, accountNumber);
    }

    private boolean isThereEnoughBalance(Account account, BigDecimal amountToMoveToPocket) {
        return account.getBalance().add(balanceInPocketsOfOneAccount(account.getAccountNumber()).negate()).compareTo(amountToMoveToPocket) >= 0;
    }

    public Account getAccountById (Long id){
        String uri = "http://localhost:8080/api/accounts/" + id;
        ResponseEntity<Account> response;
        try{
            response = new RestTemplate().getForEntity(uri, Account.class);
        } catch(Exception e){
            return null;
        }
        return response.getBody();
    }

    public BigDecimal balanceInPocketsOfOneAccount(Long accountNumber){
        DTOPocketConsultOut pocketsInAccount = getPocketsByAccountNumber(accountNumber).getBody();
        BigDecimal sumAmountInPockets = BigDecimal.valueOf(0);
        if(!pocketsInAccount.getPockets().isEmpty()){
            for (DTOPocketConsultIn pocket: pocketsInAccount.getPockets()){
                BigDecimal amountInPocket = pocket.getAmount();
                sumAmountInPockets = sumAmountInPockets.add(amountInPocket);
            }
        }
        return sumAmountInPockets;
    }
}
