package br.com.petinder.backend.dtos.owner;

import br.com.petinder.backend.dtos.address.EditAddressDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EditOwnerDTO {

    @NotNull(message = "É necessário informar o ID")
    private long id;

    @NotBlank(message = "O nome não pode estar vazio!")
    private String name;

    @NotBlank(message = "O número de telefone não pode estar vazio!")
    @Size(min = 11, max = 14, message = "O número de celular deve conter entre 11 e 14 caracteres!")
    private String celNumber;

    private EditAddressDTO address;

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

    public String getCelNumber() {
        return celNumber;
    }

    public void setCelNumber(String celNumber) {
        this.celNumber = celNumber;
    }

    public EditAddressDTO getAddress() {
        return address;
    }

    public void setAddress(EditAddressDTO address) {
        this.address = address;
    }
}
