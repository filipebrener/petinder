package br.com.petinder.backend.dtos.response.errors;

public class ErrorMessageDto {

    private String message;

    public ErrorMessageDto(String message) {
        this.message = message;
    }

    public ErrorMessageDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
