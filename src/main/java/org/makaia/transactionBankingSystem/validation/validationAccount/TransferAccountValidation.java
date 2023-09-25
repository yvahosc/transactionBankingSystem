package org.makaia.transactionBankingSystem.validation.validationAccount;

import org.makaia.transactionBankingSystem.dto.dtoAccount.DTOAccountTransfer;
import org.makaia.transactionBankingSystem.exception.ApiException;
import org.makaia.transactionBankingSystem.validation.ValidationEntryData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Pattern;


@Component
public class TransferAccountValidation implements ValidationEntryData {
    private DTOAccountTransfer dtoAccountTransfer;

    public DTOAccountTransfer getDtoAccountTransfer() {
        return dtoAccountTransfer;
    }

    public void setDtoAccountTransfer(DTOAccountTransfer dtoAccountTransfer) {
        this.dtoAccountTransfer = dtoAccountTransfer;
    }

    public boolean isValidAmount(){
        int result = this.dtoAccountTransfer.getAmount().compareTo(BigDecimal.valueOf(2000));
        return result >= 0;
    }

    public boolean areValidAccounts() {
        String regx = "^([0-9]{15})$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.dtoAccountTransfer.getSourceAccountNumber().toString()).matches() &&
                pattern.matcher(this.dtoAccountTransfer.getDestinationAccountNumber().toString()).matches();
    }

    public String validationEntryData() throws ApiException {
        String message = "";

        if(!areValidAccounts()){
            message = message + "- Error al ingresar los numeros de las cuentas. Ingrese valores válidos: " +
                    "números de 15 dígitos.";
        }

        if(!isValidAmount()){
            message = message + " - Error. La cantidad a transferir debe ser mayor o igual a $2000.";
        }

        return message;
    }
}
