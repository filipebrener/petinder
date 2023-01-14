package br.com.petinder.backend.dtos.pet;

import br.com.petinder.backend.domains.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class EditPetDTO {

    @NotNull(message = "O id é obrigatório")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotNull(message = "A data de nascimento é obrigatório")
    private Date birthDate;

    @NotNull(message = "O campo que informa se tem pedigree é obrigatório")
    private boolean hasPedigree;

    @NotNull(message = "O id da raça é obrigatório")
    private Long breedId;

    public EditPetDTO(String name, Date birthDate, boolean hasPedigree, Long breedId) {
        this.name = name;
        this.birthDate = birthDate;
        this.hasPedigree = hasPedigree;
        this.breedId = breedId;
    }

    public EditPetDTO() {
    }

    public EditPetDTO(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.birthDate = pet.getBirthDate();
        this.breedId = pet.getBreed().getId();
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean hasPedigree() {
        return hasPedigree;
    }

    public void setHasPedigree(boolean hasPedigree) {
        this.hasPedigree = hasPedigree;
    }

    public Long getBreedId() {
        return breedId;
    }

    public void setBreedId(Long breedId) {
        this.breedId = breedId;
    }


}
