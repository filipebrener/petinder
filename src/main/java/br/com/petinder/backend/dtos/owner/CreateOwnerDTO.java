package br.com.petinder.backend.dtos.owner;

import br.com.petinder.backend.dtos.address.CreateAddressDTO;
import br.com.petinder.backend.validators.OfLegalAge;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

public class CreateOwnerDTO {

    @NotBlank(message = "O nome não pode estar vazio!")
    private String name;

    @CPF(message = "O CPF [${validatedValue}] não é válido!")
    @NotBlank(message = "O CPF não pode estar vazio!")
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória")
    @OfLegalAge(currentAge = 18, message = "É obrigatório ter no mínimo {currentAge} anos!")
    private Date birthDate;

    @NotBlank(message = "O número de telefone não pode estar vazio!")
    @Size(min = 11, max = 14, message = "O número de celular deve conter entre 11 e 14 caracteres!")
    private String celNumber;

    @NotBlank(message = "O email não pode ficar vazio!")
    @Email(message = "O email não está em um formato válido!")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo {value} caracteres")
    private String password;

    @NotBlank(message = "O username não pode ficar vazio!")
    private String username;

    private CreateAddressDTO address;

    public CreateOwnerDTO() {
    }

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

    public CreateAddressDTO getAddress() {
        return address;
    }

    public void setAddress(CreateAddressDTO address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
