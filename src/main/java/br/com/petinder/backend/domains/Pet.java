package br.com.petinder.backend.domains;

import br.com.petinder.backend.dtos.pet.CreatePetDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pet")
public class Pet extends BaseDomain {

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @NotNull
    @Column(name = "has_pedigree", nullable = false)
    private boolean hasPedigree;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "breed_id", referencedColumnName = "id", nullable = false)
    private Breed breed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Owner owner;

    @OneToMany
    private List<Pet> likes;

    @OneToMany
    private List<Pet> matches;

    public Pet(String name, Date birthDate, boolean hasPedigree, Breed breed) {
        this.name = name;
        this.birthDate = birthDate;
        this.hasPedigree = hasPedigree;
        this.breed = breed;
    }

    public Pet() {
    }

    public Pet(CreatePetDTO dto) {
        this.name = dto.getName();
        this.birthDate = dto.getBirthDate();
        this.hasPedigree = dto.hasPedigree();
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
