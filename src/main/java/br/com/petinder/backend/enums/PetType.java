package br.com.petinder.backend.enums;

public enum PetType {

    DOG("cachorro"), CAT("gato");

    private String value;

    PetType(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
