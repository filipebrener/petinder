package br.com.petinder.backend.domains;

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

}
