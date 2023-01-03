package br.com.petinder.backend.exceptions;

public class NotFoundException extends Throwable {

    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
