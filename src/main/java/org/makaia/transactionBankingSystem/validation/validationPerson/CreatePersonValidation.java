package org.makaia.transactionBankingSystem.validation.validationPerson;

import org.makaia.transactionBankingSystem.model.Person;
import org.makaia.transactionBankingSystem.validation.ValidationEntryData;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CreatePersonValidation implements ValidationEntryData {
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isValidName(){
        String regx = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.person.getFirstName()).matches() &&
                pattern.matcher(this.person.getLastName()).matches();
    }

    public boolean isValidId(){
        String regx = "^([0-9]{6,10})$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.person.getId()).matches();
    }

    public boolean isValidEmail(){
        String regx = "^[A-Za-z]+[A-Za-z0-9+_.-]*@[A-Za-z]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.person.getEmail()).matches();
    }

    public boolean isValidPhone(){
        String regx = "^\\++([0-9]{2})+-+([0-9]{10})$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(this.person.getPhone()).matches();
    }

    public String validationEntryData() {
        String message = "";

        if(!isValidId()){
            message = "- Error al ingresar el documento de identidad. Ingrese un identificador válido: " +
                    "número con entre seis y diez dígitos.";
        }
        if(!isValidName()){
            message = message + " - Error al ingresar el nombre y/o apellido. Ingrese información válida: " +
                    "solo letras.";
        }
        if(!isValidEmail()){
            message = message + " - Error al ingresar el correo. Ingrese uno válido: con la estructura " +
                    "ejemplo@dominio.com.co";
        }
        if(!isValidPhone()){
            message = message + " - Error al ingresar el teléfono. Ingrese uno válido: con la estructura " +
                    "+XX-XXXXXXXXXX";
        }
        return message;
    }
}