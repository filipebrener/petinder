package br.com.petinder.backend.dtos.breed;

import br.com.petinder.backend.enums.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateBreedDTO {

    @NotBlank(message = "O nome da raça não pode ficar em branco!")
    private String name;

    @NotBlank(message = "A descrição não pode ficar em branco!")
    private String description;

    @NotNull(message = "O tipo do pet é obrigatório!")
    private PetType type;

    public CreateBreedDTO(String name, String description, PetType type) {
        this.name = name;
        this.description = description;
        this.type = type;
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

    public PetType getType() {
        return this.type;
    }

    public void setType(PetType type){
        this.type = type;
    }
}
