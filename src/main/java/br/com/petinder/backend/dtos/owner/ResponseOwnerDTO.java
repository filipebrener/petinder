package br.com.petinder.backend.dtos.owner;

import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.dtos.address.ResponseAddressDTO;

public class ResponseOwnerDTO {

    private long id;

    private String uuid;

    private String name;

    private String celNumber;

    private String email;

    private ResponseAddressDTO address;

    public ResponseOwnerDTO(Owner owner) {
        this.id = owner.getId();
        this.uuid = owner.getUuid();
        this.name = owner.getName();
        this.email = owner.getEmail();
        this.celNumber = owner.getCelNumber();
        this.address = owner.getAddress() != null ? new ResponseAddressDTO(owner.getAddress()) : null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public ResponseAddressDTO getAddress() {
        return address;
    }

    public void setAddress(ResponseAddressDTO address) {
        this.address = address;
    }
}
