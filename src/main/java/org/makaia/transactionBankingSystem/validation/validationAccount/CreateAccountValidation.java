package org.makaia.transactionBankingSystem.validation.validationAccount;

import org.makaia.transactionBankingSystem.dto.dtoAccount.DTOAccountCreation;
import org.makaia.transactionBankingSystem.exception.ApiException;
import org.makaia.transactionBankingSystem.validation.ValidationEntryData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class CreateAccountValidation implements ValidationEntryData {
    private DTOAccountCreation dtoAccountCreation;

    public DTOAccountCreation getDtoAccountCreation() {
        return dtoAccountCreation;
    }

    public void setDtoAccountCreation(DTOAccountCreation dtoAccountCreation) {
        this.dtoAccountCreation = dtoAccountCreation;
    }

    public boolean isValidBalance() throws ApiException {
        int result = this.dtoAccountCreation.getInitialBalance().compareTo(BigDecimal.valueOf(0));
        return result >= 0;
    }

    public boolean isValidPerson() throws ApiException {
        return this.getDtoAccountCreation().getOwner() != null;
    }

    public String validationEntryData() throws ApiException {
        String message = "";

        if(!isValidBalance()){
            message = message + "- Error. El saldo inicial de la cuenta debe ser mayor o igual a cero.";
        }
        if(!isValidPerson()){
            message = message + " - Error. El propietario de la cuenta no puede ser nulo.";
        }
        return message;
    }
}
