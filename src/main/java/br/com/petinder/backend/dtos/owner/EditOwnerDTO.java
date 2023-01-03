package br.com.petinder.backend.dtos.owner;

import br.com.petinder.backend.dtos.address.EditAddressDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EditOwnerDTO {

    @NotBlank(message = "É necessário informar o UUID")
    private String uuid;

    @NotBlank(message = "O nome não pode estar vazio!")
    private String name;

    @NotBlank(message = "O número de telefone não pode estar vazio!")
    @Size(min = 11, max = 14, message = "O número de celular deve conter entre 11 e 14 caracteres!")
    private String celNumber;

    private EditAddressDTO address;

    public String getUuid() {
        return uuid;
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
