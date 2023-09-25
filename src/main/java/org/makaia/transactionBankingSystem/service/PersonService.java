package org.makaia.transactionBankingSystem.service;

import org.makaia.transactionBankingSystem.dto.dtoPerson.DTOPersonUpdate;
import org.makaia.transactionBankingSystem.exception.ApiException;
import org.makaia.transactionBankingSystem.model.Person;
import org.makaia.transactionBankingSystem.repository.PersonRepository;
import org.makaia.transactionBankingSystem.validation.validationPerson.CreatePersonValidation;
import org.makaia.transactionBankingSystem.validation.validationPerson.GetPersonValidation;
import org.makaia.transactionBankingSystem.validation.validationPerson.UpdatePersonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    PersonRepository personRepository;
    CreatePersonValidation createPersonValidation;
    UpdatePersonValidation updatePersonValidation;
    GetPersonValidation getPersonValidation;

    @Autowired
    public PersonService(PersonRepository personRepository, CreatePersonValidation createPersonValidation,
                         UpdatePersonValidation updatePersonValidation, GetPersonValidation getPersonValidation)
    {
        this.personRepository = personRepository;
        this.createPersonValidation = createPersonValidation;
        this.updatePersonValidation = updatePersonValidation;
        this.getPersonValidation = getPersonValidation;
    }

    public ResponseEntity<Person> getPersonById(String id) throws ApiException {
        getPersonValidation.setId(id);
        String message = getPersonValidation.validationEntryData();
        if(!message.isEmpty()){
            throw new ApiException (400, message);
        }

        Optional <Person> person = this.personRepository.findById(id);
        if (person.isEmpty()){
            throw new ApiException (404, "La persona con id '" + id + "' no se encuentra en la base de datos.");
        }
        return ResponseEntity.ok(person.get());
    }

    public ResponseEntity<Person> createPerson(Person person) throws ApiException {
        createPersonValidation.setPerson(person);
        String message = createPersonValidation.validationEntryData();

        if(!message.isEmpty()){
            throw new ApiException (400, message);
        } else{
            return ResponseEntity.ok(this.personRepository.save(person));
        }
    }

    public ResponseEntity<Person> updatePerson(DTOPersonUpdate personUpdate) throws ApiException {
        updatePersonValidation.setPerson(personUpdate);
        String message = "";

        message = message + updatePersonValidation.validationEntryData();

        if(!message.isEmpty()){
            throw new ApiException (400, message);
        }

        Person existingPerson = getPersonById(personUpdate.getId()).getBody();

        existingPerson.setEmail(personUpdate.getEmail());
        existingPerson.setPhone(personUpdate.getPhone());
        return ResponseEntity.ok(this.personRepository.save(existingPerson));
    }
}
