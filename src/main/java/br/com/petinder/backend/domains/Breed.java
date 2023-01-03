package br.com.petinder.backend.domains;

import br.com.petinder.backend.dtos.breed.CreateBreedDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Breed extends BaseDomain{

    @Column(name = "name")
    @NotBlank(message = "A raça não pode ficar em branco")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "A descrição não pode ficar em branco")
    private String description;

    public Breed(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Breed() {
    }

    public Breed(CreateBreedDTO requestDTO) {
        this.description = requestDTO.getDescription();
        this.name = requestDTO.getName();
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
