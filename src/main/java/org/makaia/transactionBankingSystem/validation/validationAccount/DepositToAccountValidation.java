package org.makaia.transactionBankingSystem.validation.validationAccount;

import org.makaia.transactionBankingSystem.dto.dtoAccount.DTOAccountDeposit;
import org.makaia.transactionBankingSystem.exception.ApiException;
import org.makaia.transactionBankingSystem.validation.ValidationEntryData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Pattern;


@Component
public class DepositToAccountValidation implements ValidationEntryData {
    private DTOAccountDeposit dtoAccountDeposit;
    private Long accountNumber;

    public DTOAccountDeposit getDtoAccountDeposit() {
        return dtoAccountDeposit;
    }

    public void setDtoAccountDeposit(DTOAccountDeposit dtoAccountDeposit) {
        this.dtoAccountDeposit = dtoAccountDeposit;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isValidAmount() {
        int result = this.dtoAccountDeposit.getAmount().compareTo(BigDecimal.valueOf(2000));
        return result >= 0;
    }

    public boolean isValidAccountNumber() {
        String regx = "^([0-9]{15})$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.getAccountNumber().toString()).matches();
    }

    public String validationEntryData() throws ApiException {
        String message = "";

        if(!isValidAccountNumber()){
            message = "- Error al ingresar el numero de la cuenta. Ingrese un valor válido: número" +
                    " de 15 dígitos.";
        }

        if(!isValidAmount()){
            message = message + " - Error. La cantidad a transferir debe ser mayor o igual a $2000.";
        }

        return message;
    }
}
