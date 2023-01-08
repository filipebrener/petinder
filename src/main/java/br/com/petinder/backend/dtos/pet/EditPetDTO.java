package br.com.petinder.backend.dtos.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class EditPetDTO {

    @NotNull(message = "O id é obrigatório")
    private long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotNull(message = "A data de nascimento é obrigatório")
    private Date birthDate;

    @NotNull(message = "O campo que informa se tem pedigree é obrigatório")
    private boolean hasPedigree;

    @NotBlank(message = "O id da raça é obrigatório")
    private long breedId;

    public EditPetDTO(String name, Date birthDate, boolean hasPedigree, long breedId) {
        this.name = name;
        this.birthDate = birthDate;
        this.hasPedigree = hasPedigree;
        this.breedId = breedId;
    }

    public EditPetDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getBreedId() {
        return breedId;
    }

    public void setBreedUuid(long breedId) {
        this.breedId = breedId;
    }


}
