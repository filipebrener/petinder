package br.com.petinder.backend.domains;

import br.com.petinder.backend.dtos.address.CreateAddressDTO;
import br.com.petinder.backend.dtos.address.EditAddressDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Embeddable
public class Address {

    @NotBlank
    @Column(name = "street")
    private String street;

    @NotBlank
    @Column(name = "neighborhood")
    private String neighborhood;

    @NotBlank
    @Column(name = "zipCode")
    private String zipCode;

    @NotNull
    @Column(name = "number")
    private int number;

    @NotBlank
    @Column(name = "complement")
    private String complement;

    @NotBlank
    @Column(name = "city")
    private String city;

    @NotBlank
    @Column(name = "state")
    private String state;

    @NotBlank
    @Column(name = "country")
    private String country;

    public Address(){}

    public Address(String street, String neighborhood, String zipCode, int number, String complement, String city, String state, String country) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.number = number;
        this.complement = complement;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public Address(CreateAddressDTO address) {
        this.street = address.getStreet();
        this.neighborhood = address.getNeighborhood();
        this.zipCode = address.getZipCode();
        this.number = address.getNumber();
        this.complement = address.getComplement();
        this.country = address.getCountry();
        this.state = address.getState();
        this.city = address.getCity();
    }

    public Address(EditAddressDTO address) {
        this.street = address.getStreet();
        this.neighborhood = address.getNeighborhood();
        this.zipCode = address.getZipCode();
        this.number = address.getNumber();
        this.complement = address.getComplement();
        this.country = address.getCountry();
        this.state = address.getState();
        this.city = address.getCity();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
