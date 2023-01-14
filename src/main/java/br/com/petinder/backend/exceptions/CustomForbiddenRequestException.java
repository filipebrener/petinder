package br.com.petinder.backend.exceptions;

public class CustomForbiddenRequestException extends Throwable{

    private String message;

    public CustomForbiddenRequestException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
