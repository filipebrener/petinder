package br.com.petinder.backend.dtos.owner;

import br.com.petinder.backend.dtos.address.CreateAddressDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class CreateOwnerDTO {

    @NotBlank(message = "O nome não pode estar vazio!")
    private String name;

    @CPF(message = "O CPF [${validatedValue}] não é válido!")
    @NotBlank(message = "O CPF não pode estar vazio!")
    private String cpf;

    @NotBlank(message = "O número de telefone não pode estar vazio!")
    @Size(min = 11, max = 14, message = "O número de celular deve conter entre 11 e 14 caracteres!")
    private String celNumber;

    @NotBlank(message = "O email não pode ficar vazio!")
    @Email(message = "O email não está em um formato válido!")
    private String email;

    private CreateAddressDTO address;

    public CreateOwnerDTO() {
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

    public CreateAddressDTO getAddress() {
        return address;
    }

    public void setAddress(CreateAddressDTO address) {
        this.address = address;
    }
}
