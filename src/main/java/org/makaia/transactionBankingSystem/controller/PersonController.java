package org.makaia.transactionBankingSystem.controller;

import org.makaia.transactionBankingSystem.dto.dtoPerson.DTOPersonUpdate;
import org.makaia.transactionBankingSystem.exception.ApiException;
import org.makaia.transactionBankingSystem.model.Person;
import org.makaia.transactionBankingSystem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/persons")
public class PersonController {
    PersonService personService;
    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Person> getPerson (@PathVariable String id) throws ApiException {
        return this.personService.getPersonById(id);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson (@RequestBody Person person) throws ApiException {
        return this.personService.createPerson(person);
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson (@RequestBody DTOPersonUpdate person) throws ApiException {
        return this.personService.updatePerson(person);
    }
}
