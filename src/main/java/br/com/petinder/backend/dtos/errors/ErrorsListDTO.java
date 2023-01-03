package br.com.petinder.backend.dtos.errors;

import java.util.List;

public class ErrorsListDTO {

    private String message;

    private List<String> errors;

    public ErrorsListDTO(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }
    public ErrorsListDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
