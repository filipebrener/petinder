package br.com.petinder.backend.dtos.pet;

import br.com.petinder.backend.domains.Pet;
import br.com.petinder.backend.dtos.breed.ResponseBreedDTO;
import br.com.petinder.backend.dtos.owner.ResponseOwnerDTO;

import java.util.Date;

public class ResponsePetDTO {

    private long id;
    private String name;
    private Date birthDate;
    private int age;
    private boolean hasPedigree;
    private ResponseBreedDTO breed;
    private ResponseOwnerDTO owner;

    public ResponsePetDTO(Pet persistedPet) {
        this.id = persistedPet.getId();
        this.name = persistedPet.getName();
        this.birthDate = persistedPet.getBirthDate();
        this.hasPedigree = persistedPet.hasPedigree();
        this.breed = new ResponseBreedDTO(persistedPet.getBreed());
        this.owner = new ResponseOwnerDTO(persistedPet.getOwner());
    }

    public ResponsePetDTO() { }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean hasPedigree() {
        return hasPedigree;
    }

    public void setHasPedigree(boolean hasPedigree) {
        this.hasPedigree = hasPedigree;
    }

    public ResponseBreedDTO getBreed() {
        return breed;
    }

    public void setBreed(ResponseBreedDTO breed) {
        this.breed = breed;
    }

    public ResponseOwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(ResponseOwnerDTO owner) {
        this.owner = owner;
    }
}
