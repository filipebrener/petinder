package br.com.petinder.backend.dtos.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePetDTO {

    @NotBlank(message = "O nome não pode ficar em branco!")
    private String name;

    @NotNull(message = "É necessário informar a idade!")
    private int age;

    @NotNull(message = "É necessário informar se tem pedigree!")
    private boolean hasPedigree;

    @NotBlank(message = "É necessário informar o Uuid da raça!")
    private String breedUuid;

    public CreatePetDTO(String name, int age, boolean hasPedigree, String breedUuid) {
        this.name = name;
        this.age = age;
        this.hasPedigree = hasPedigree;
        this.breedUuid = breedUuid;
    }

    public CreatePetDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getHasPedigree() {
        return hasPedigree;
    }

    public void setHasPedigree(boolean hasPedigree) {
        this.hasPedigree = hasPedigree;
    }

    public String getBreedUuid() {
        return breedUuid;
    }

    public void setBreedUuid(String breedUuid) {
        this.breedUuid = breedUuid;
    }
}
