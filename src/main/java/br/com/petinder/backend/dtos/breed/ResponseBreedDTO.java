package br.com.petinder.backend.dtos.breed;

import br.com.petinder.backend.domains.Breed;

public class ResponseBreedDTO {

    private String uuid;

    private String name;

    private String description;

    public ResponseBreedDTO(Breed persistedBreed) {
        this.description = persistedBreed.getDescription();
        this.name = persistedBreed.getDescription();
        this.uuid = persistedBreed.getUuid();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
