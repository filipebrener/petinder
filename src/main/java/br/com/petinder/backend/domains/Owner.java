package br.com.petinder.backend.domains;

import br.com.petinder.backend.validators.OfLegalAge;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Owner extends User {

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Temporal(TemporalType.DATE)
    @OfLegalAge(currentAge = 18)
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @CPF
    @NotBlank
    @Column(name = "cpf", unique=true, length = 11, nullable = false)
    private String cpf;

    @NotBlank
    @Size(min = 11, max = 14)
    @Column(name = "cel_number", length = 14, unique = true, nullable = false)
    private String celNumber;

    @Email
    @NotBlank
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    private Address address;

    @OneToMany
    private List<Pet> pets;
    public Owner(String name, Date birthDate, String username, String cpf, String celNumber, String email, String password, Address address, Set<Role> roles) {
        super(username, password, roles);
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.celNumber = celNumber;
        this.email = email;
        this.address = address;
    }

    public Owner(String name, Date birthDate, String username, String cpf, String celNumber, String email, String password, Set<Role> roles) {
        super(username, password, roles);
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.celNumber = celNumber;
        this.email = email;
    }

    public Owner() {}

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCelNumber() {
        return celNumber;
    }

    public void setCelNumber(String celNumber) {
        this.celNumber = celNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
