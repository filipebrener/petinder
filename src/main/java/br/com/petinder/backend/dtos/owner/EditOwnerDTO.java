package br.com.petinder.backend.dtos.owner;

import br.com.petinder.backend.dtos.address.EditAddressDTO;
import br.com.petinder.backend.validators.OfLegalAge;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class EditOwnerDTO {

    @NotNull(message = "É necessário informar o ID")
    private Long id;

    @NotNull(message = "A data de nascimento é obrigatório!")
    @OfLegalAge(currentAge = 18, message = "É necessário ter no mínimo {currentAge} anos!")
    private Date birthDate;

    @NotBlank(message = "O nome não pode estar vazio!")
    private String name;

    @NotBlank(message = "O número de telefone não pode estar vazio!")
    @Size(min = 11, max = 14, message = "O número de celular deve conter entre 11 e 14 caracteres!")
    private String celNumber;

    private EditAddressDTO address;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
