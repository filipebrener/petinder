package br.com.petinder.backend.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Breed extends BaseDomain{

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Breed(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Breed() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
