package br.com.residue.collect.infra.exceptions;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> manageInvalidArgs(MethodArgumentNotValidException error){
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> fieldErrors = error.getBindingResult().getFieldErrors();

        for (FieldError field : fieldErrors){
            errorMap.put(field.getField(), field.getDefaultMessage());
        }
        return errorMap;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> manageDataIntegrityViolation(){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Erro", "Usuario ja registrado!");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public Map<String, String> handleItemNotFoundException(ItemNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Erro", ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPasswordException.class)
    public Map<String, String> handleInvalidPasswordException(InvalidPasswordException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("Erro", ex.getMessage());
        return errors;
    }

}
