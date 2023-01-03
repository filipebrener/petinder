package br.com.petinder.backend.dtos.breed;

import jakarta.validation.constraints.NotBlank;

public class CreateBreedDTO {

    @NotBlank(message = "A raça não pode ficar em branco")
    private String name;

    @NotBlank(message = "A descrição não pode ficar em branco")
    private String description;

    public CreateBreedDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CreateBreedDTO() {
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
