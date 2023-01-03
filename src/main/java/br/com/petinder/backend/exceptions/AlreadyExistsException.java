package br.com.petinder.backend.exceptions;

import java.util.List;

public class AlreadyExistsException extends Throwable {

    private String message;
    private List<String> errors;
    public AlreadyExistsException(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public List<String> getErrors(){
        return errors;
    }

}
