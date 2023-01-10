package br.com.petinder.backend.dtos.response.errors;

import java.util.List;

public class FieldErrorsMessageDTO {

    private String field;

    private List<String> errors;

    public FieldErrorsMessageDTO(String field, List<String> errors) {
        this.field = field;
        this.errors = errors;
    }

    public FieldErrorsMessageDTO() {
    }

    void addErrorInErrorMessageList(String error){
        this.errors.add(error);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
