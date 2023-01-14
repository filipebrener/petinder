package br.com.petinder.backend.dtos.breed;

import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.enums.PetType;

public class ResponseBreedDTO {

    private Long id;

    private String name;

    private String description;

    private PetType type;

    public ResponseBreedDTO(Breed persistedBreed) {
        this.description = persistedBreed.getDescription();
        this.name = persistedBreed.getName();
        this.id = persistedBreed.getId();
        this.type = persistedBreed.getType();
    }

    public ResponseBreedDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }
}
