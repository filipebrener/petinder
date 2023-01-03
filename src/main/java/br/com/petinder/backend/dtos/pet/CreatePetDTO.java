package br.com.petinder.backend.dtos.pet;

import br.com.petinder.backend.enums.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePetDTO {

    @NotBlank
    private PetType type;

    @NotBlank
    private String name;

    @NotNull
    private int age;

    @NotNull
    private boolean hasPedigree;

    @NotBlank
    private String breedUuid;

    public CreatePetDTO(PetType type, String name, int age, boolean hasPedigree, String breedUuid) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.hasPedigree = hasPedigree;
        this.breedUuid = breedUuid;
    }

    public CreatePetDTO() {
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
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
