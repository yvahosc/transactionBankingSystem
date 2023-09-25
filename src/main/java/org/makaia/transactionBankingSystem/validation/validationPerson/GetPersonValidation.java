package org.makaia.transactionBankingSystem.validation.validationPerson;

import org.makaia.transactionBankingSystem.validation.ValidationEntryData;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class GetPersonValidation implements ValidationEntryData {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isValidId(){
        String regx = "^([0-9]{6,10})$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.getId()).matches();
    }

    public String validationEntryData() {
        String message = "";

        if(!isValidId()){
            message = "- Error al ingresar el documento de identidad. Ingrese un identificador válido: " +
                    "número con entre seis y diez dígitos.";
        }

        return message;
    }
}