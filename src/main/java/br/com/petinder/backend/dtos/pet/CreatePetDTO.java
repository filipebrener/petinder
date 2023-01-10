package br.com.petinder.backend.dtos.pet;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CreatePetDTO {

    @NotBlank(message = "O nome não pode ficar em branco!")
    private String name;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "É necessário informar a data de nascimento!")
    private Date birthDate;

    @NotNull(message = "É necessário informar se tem pedigree!")
    private boolean hasPedigree;

    @NotNull(message = "É necessário informar o ID da raça!")
    private long breedId;

    @NotNull(message = "O ID do dono é obrigatório!")
    private long ownerId;

    public CreatePetDTO(String name, Date birthDate, boolean hasPedigree, long breedId, long ownerId) {
        this.name = name;
        this.birthDate = birthDate;
        this.hasPedigree = hasPedigree;
        this.breedId = breedId;
        this.ownerId = ownerId;
    }

    public CreatePetDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHasPedigree(boolean hasPedigree) {
        this.hasPedigree = hasPedigree;
    }

    public long getBreedId() {
        return breedId;
    }

    public void setBreedId(long breedId) {
        this.breedId = breedId;
    }

    public boolean hasPedigree() {
        return hasPedigree;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
