package br.com.petinder.backend;

import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.dtos.address.CreateAddressDTO;
import br.com.petinder.backend.dtos.breed.CreateBreedDTO;
import br.com.petinder.backend.dtos.owner.CreateOwnerDTO;
import br.com.petinder.backend.enums.PetType;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.services.BreedService;
import br.com.petinder.backend.services.CpfHandlerService;
import br.com.petinder.backend.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

public abstract class BaseTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected OwnerService ownerService;

    @Autowired
    protected BreedService breedService;

    protected Owner createOwner() throws AlreadyExistsException {
        return ownerService.create(getCreateOwnerDTOWithAddress());
    }

    protected Breed createBreed() throws AlreadyExistsException {
        return breedService.create(getCreateBreedDto());
    }

    protected String cpf() {
        return new CpfHandlerService().cpf(false);
    }

    protected CreateBreedDTO getCreateBreedDto(){
        CreateBreedDTO dto = new CreateBreedDTO();
        dto.setDescription("Cachorro mais bravo que tem!");
        dto.setType(PetType.DOG);
        dto.setName("CHIHUAHUA");
        return dto;
    }

    protected CreateOwnerDTO getCreateOwnerDTOWithoutAddress(){
        CreateOwnerDTO dto = new CreateOwnerDTO();
        dto.setName("Filipe Brenner");
        dto.setCpf(cpf());
        dto.setEmail(uuid() + "filipe.brener@ufv.br");
        dto.setCelNumber(uuid().toString().substring(0,11));
        return dto;
    }

    protected CreateOwnerDTO getCreateOwnerDTOWithAddress(){
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

    protected String uuid(){
        return UUID.randomUUID().toString();
    }
}
