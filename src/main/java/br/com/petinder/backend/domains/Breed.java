package br.com.petinder.backend.domains;

import br.com.petinder.backend.dtos.breed.CreateBreedDTO;
import br.com.petinder.backend.enums.PetType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.StringUtils;

@Entity
public class Breed extends BaseDomain{

    @NotBlank
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "type", nullable = false)
    private PetType type;

    public Breed(String name, String description, PetType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public Breed() {
    }

    @Override
    public void prePersist(){
        super.prePersist();
        this.name = StringUtils.capitalize(this.name.toLowerCase());
    }

    public Breed(CreateBreedDTO requestDTO) {
        this.description = requestDTO.getDescription();
        this.name = requestDTO.getName();
        this.type = requestDTO.getType();
    }

    public String getName() {
        return StringUtils.capitalize(name);
    }

    public void setName(String name) {
        this.name = StringUtils.capitalize(name);
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
