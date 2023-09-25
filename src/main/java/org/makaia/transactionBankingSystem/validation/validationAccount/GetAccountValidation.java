package org.makaia.transactionBankingSystem.validation.validationAccount;

import org.makaia.transactionBankingSystem.validation.ValidationEntryData;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class GetAccountValidation implements ValidationEntryData {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isValidId(){
        String regx = "^([0-9]{15})$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.getId().toString()).matches();
    }

    public String validationEntryData() {
        String message = "";

        if(!isValidId()){
            message = "- Error al ingresar el numero de cuenta. Ingrese un numero válido: " +
                    "número de 15 dígitos.";
        }
        return message;
    }
}