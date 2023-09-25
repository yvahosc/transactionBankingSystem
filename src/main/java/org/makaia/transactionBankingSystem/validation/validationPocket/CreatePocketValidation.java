package org.makaia.transactionBankingSystem.validation.validationPocket;

import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketCreation;
import org.makaia.transactionBankingSystem.validation.ValidationEntryData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Pattern;


@Component
public class CreatePocketValidation implements ValidationEntryData {
    private DTOPocketCreation dtoPocketCreation;

    public DTOPocketCreation getDtoPocketCreation() {
        return dtoPocketCreation;
    }

    public void setDtoPocketCreation(DTOPocketCreation dtoPocketCreation) {
        this.dtoPocketCreation = dtoPocketCreation;
    }

    public boolean isValidName(){
        String regx = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.dtoPocketCreation.getName()).matches();
    }

    public boolean isValidAmount() {
        int result = this.dtoPocketCreation.getInitialBalance().compareTo(BigDecimal.valueOf(0));
        return result > 0;
    }

    public boolean isValidAccountNumber() {
        String regx = "^([0-9]{15})$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.dtoPocketCreation.getAccountNumber().toString()).matches();
    }


    public String validationEntryData() {
        String message = "";

        if(!isValidAccountNumber()){
            message = "- Error al ingresar el numero de la cuenta. Ingrese un valor válido: número" +
                    " de 15 dígitos.";
        }

        if(!isValidName()){
            message = message + " - Error al ingresar el nombre del bolsillo. Ingrese información válida: solo letras.";
        }

        if(!isValidAmount()) {
            message = message + " - Error. La cantidad a transferir debe ser mayor a cero.";
        }
        return message;
    }
}
