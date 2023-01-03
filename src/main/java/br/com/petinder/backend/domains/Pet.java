package br.com.petinder.backend.domains;

import br.com.petinder.backend.dtos.pet.CreatePetDTO;
import br.com.petinder.backend.enums.PetType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "pet")
public class Pet extends BaseDomain {

    @NotBlank
    @Column(name = "type")
    private PetType type;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "age")
    private int age;

    @NotNull
    @Column(name = "has_pedigree")
    private boolean hasPedigree;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "breed_id", referencedColumnName = "id")
    private Breed breed;

    @OneToMany
    private List<Pet> likes;

    @OneToMany
    private List<Pet> matches;

    public Pet(PetType type, String name, int age, boolean hasPedigree, Breed breed) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.hasPedigree = hasPedigree;
        this.breed = breed;
    }

    public Pet() {
    }

    public Pet(CreatePetDTO dto) {
        this.type = dto.getType();
        this.name = dto.getName();
        this.age = dto.getAge();
        this.hasPedigree = dto.getHasPedigree();
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

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public List<Pet> getLikes() {
        return likes;
    }

    public void setLikes(List<Pet> likes) {
        this.likes = likes;
    }

    public List<Pet> getMatches() {
        return matches;
    }

    public void setMatches(List<Pet> matches) {
        this.matches = matches;
    }
}
