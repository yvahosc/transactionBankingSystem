package org.makaia.transactionBankingSystem.validation.validationPocket;

import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketTransfer;
import org.makaia.transactionBankingSystem.validation.ValidationEntryData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Pattern;


@Component
public class TransferPocketValidation implements ValidationEntryData {
    private DTOPocketTransfer dtoPocketTransfer;

    public DTOPocketTransfer getDtoPocketTransfer() {
        return dtoPocketTransfer;
    }

    public void setDtoPocketTransfer(DTOPocketTransfer dtoPocketTransfer) {
        this.dtoPocketTransfer = dtoPocketTransfer;
    }

    public boolean isValidAmount() {
        int result = this.dtoPocketTransfer.getAmount().compareTo(BigDecimal.valueOf(0));
        return result > 0;
    }

    public boolean isValidAccountNumber() {
        String regx = "^([0-9]{15})$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.dtoPocketTransfer.getAccountNumber().toString()).matches();
    }

    public boolean isValidPocketNumber() {
        String regx = "^([0-9])+$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.dtoPocketTransfer.getAccountNumber().toString()).matches();
    }

    public String validationEntryData() {
        String message = "";

        if(!isValidPocketNumber()){
            message = "- Error al ingresar el numero del bolsillo. Ingrese un valor válido: número.";
        }

        if(!isValidAccountNumber()){
            message = "- Error al ingresar el numero de la cuenta. Ingrese un valor válido: número" +
                    " de 15 dígitos.";
        }

        if(!isValidAmount()) {
            message = message + " - Error. La cantidad a transferir debe ser mayor a cero.";
        }
        return message;
    }
}
