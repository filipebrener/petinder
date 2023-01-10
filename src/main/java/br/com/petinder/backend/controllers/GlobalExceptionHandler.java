package br.com.petinder.backend.controllers;

import br.com.petinder.backend.dtos.response.errors.ErrorMessage;
import br.com.petinder.backend.dtos.response.errors.FieldErrorsMessageDTO;
import br.com.petinder.backend.dtos.response.errors.ErrorsListDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorsMessageDTO>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            List<String> currentList = errors.get(e.getField());
            if(currentList == null) currentList = new ArrayList<>();
            currentList.add(e.getDefaultMessage());
            errors.put(e.getField(), currentList);
        });
        List<FieldErrorsMessageDTO> response = new ArrayList<>();
        errors.forEach( (key,value) -> response.add(new FieldErrorsMessageDTO(key, value)));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AlreadyExistsException.class)
        public ResponseEntity<ErrorsListDTO> handleAlreadyExistsException(
            AlreadyExistsException ex) {
        ErrorsListDTO responseBody = new ErrorsListDTO(ex.getMessage(), ex.getErrors());
        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(
            NotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
