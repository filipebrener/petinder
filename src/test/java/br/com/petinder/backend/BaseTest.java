package br.com.petinder.backend;

import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.dtos.address.CreateAddressDTO;
import br.com.petinder.backend.dtos.owner.CreateOwnerDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.services.CpfHandlerService;
import br.com.petinder.backend.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public abstract class BaseTest {

    @Autowired
    OwnerService ownerService;
    public Owner createOwner() throws AlreadyExistsException {
        return ownerService.create(getCreateOwnerDTOWithAddress());
    }

    public String cpf() {
        return new CpfHandlerService().cpf(false);
    }

    public CreateOwnerDTO getCreateOwnerDTOWithoutAddress(){
        CreateOwnerDTO dto = new CreateOwnerDTO();
        dto.setName("Filipe Brenner");
        dto.setCpf(cpf());
        dto.setEmail(uuid() + "filipe.brener@ufv.br");
        dto.setCelNumber(uuid().toString().substring(0,11));
        return dto;
    }

    public CreateOwnerDTO getCreateOwnerDTOWithAddress(){
        CreateOwnerDTO dto = getCreateOwnerDTOWithoutAddress();
        CreateAddressDTO address = new CreateAddressDTO();
        address.setCity("Río Paranaíba");
        address.setState("Minas Gerais");
        address.setStreet("Francisco de Paula Moura Neto");
        address.setCountry("Brasil");
        address.setZipCode("38810000");
        address.setNumber(160);
        address.setNeighborhood("Novo Río");
        dto.setAddress(address);
        return dto;
    }

    public String uuid(){
        return UUID.randomUUID().toString();
    }
}
