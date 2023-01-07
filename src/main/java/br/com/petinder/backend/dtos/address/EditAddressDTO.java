package br.com.petinder.backend.dtos.address;

import jakarta.persistence.Embeddable;

@Embeddable
public class EditAddressDTO {

    private String street;

    private String neighborhood;

    private String zipCode;

    private int number;

    private String complement;

    private String city;

    private String state;

    private String country;

    public EditAddressDTO(String street, String neighborhood, String zipCode, int number, String complement, String city, String state, String country) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.country = country;
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

    public Integer getNumber() {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}
