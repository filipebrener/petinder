package br.com.petinder.backend.dtos.address;

import br.com.petinder.backend.domains.Address;

public class ResponseAddressDTO {

    private String street;

    private String neighborhood;

    private String zipCode;

    private int number;

    private String complement;

    private String city;

    private String state;

    private String country;

    public ResponseAddressDTO(Address address) {
        this.street = address.getStreet();
        this.neighborhood = address.getNeighborhood();
        this.zipCode = address.getZipCode();
        this.number = address.getNumber();
        this.complement = address.getComplement();
        this.city = address.getCity();
        this.state = address.getState();
        this.country = address.getCountry();
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
