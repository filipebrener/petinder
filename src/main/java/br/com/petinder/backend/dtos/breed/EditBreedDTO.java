package br.com.petinder.backend.dtos.breed;

import br.com.petinder.backend.domains.Breed;
import jakarta.validation.constraints.NotBlank;

public class EditBreedDTO {

    @NotBlank(message = "O uuid não pode ficar em branco")
    private String uuid;

    @NotBlank(message = "A raça não pode ficar em branco")
    private String name;

    @NotBlank(message = "A descrição não pode ficar em branco")
    private String description;

    public EditBreedDTO(String name, String description, String uuid) {
        this.name = name;
        this.description = description;
        this.uuid = uuid;
    }

    public EditBreedDTO() {
    }

    public EditBreedDTO(Breed breed) {
        this.uuid = breed.getUuid();
        this.name = breed.getName();
        this.description = breed.getDescription();
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
