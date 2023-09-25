package org.makaia.transactionBankingSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice()
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ValidationException> handler(ApiException e){
        ValidationException exception = new ValidationException(e.getStatusCode(), e.getMessage());
        return new ResponseEntity<ValidationException>(exception, HttpStatus.valueOf(e.getStatusCode()));
    }
}
