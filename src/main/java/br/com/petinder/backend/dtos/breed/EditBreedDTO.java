package br.com.petinder.backend.dtos.breed;

import br.com.petinder.backend.domains.Breed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditBreedDTO {

    @NotNull(message = "O id não pode ficar em branco")
    private Long id;

    @NotBlank(message = "A raça não pode ficar em branco")
    private String name;

    @NotBlank(message = "A descrição não pode ficar em branco")
    private String description;

    public EditBreedDTO(String name, String description,Long id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public EditBreedDTO() {
    }

    public EditBreedDTO(Breed breed) {
        this.id = breed.getId();
        this.name = breed.getName();
        this.description = breed.getDescription();
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

}
