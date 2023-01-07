package br.com.petinder.backend.dtos.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditPetDTO {

    @NotBlank
    private String uuid;

    @NotBlank
    private String name;

    @NotNull
    private int age;

    @NotNull
    private boolean hasPedigree;

    @NotBlank
    private String breedUuid;

    public EditPetDTO(String name, int age, boolean hasPedigree, String breedUuid) {
        this.name = name;
        this.age = age;
        this.hasPedigree = hasPedigree;
        this.breedUuid = breedUuid;
    }

    public EditPetDTO() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
