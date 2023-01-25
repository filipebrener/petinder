package br.com.petinder.backend;

import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.domains.Pet;
import br.com.petinder.backend.domains.Setting;
import br.com.petinder.backend.dtos.address.CreateAddressDTO;
import br.com.petinder.backend.dtos.breed.CreateBreedDTO;
import br.com.petinder.backend.dtos.owner.CreateOwnerDTO;
import br.com.petinder.backend.dtos.pet.CreatePetDTO;
import br.com.petinder.backend.dtos.setting.CreateSettingDto;
import br.com.petinder.backend.enums.PetType;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public abstract class BaseTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected OwnerService ownerService;

    @Autowired
    protected BreedService breedService;

    @Autowired
    protected PetService petService;

    @Autowired
    protected SettingService settingService;

    protected String password = uuid();

    protected Pet createPet() throws AlreadyExistsException, NotFoundException {
        return petService.create(getCreatePetDto());
    }

    protected Owner createOwnerWithUserRole() throws AlreadyExistsException {
        return ownerService.createWithUserRole(getCreateOwnerDTOWithAddress());
    }

    protected Owner createOwnerWithAdminRole() throws AlreadyExistsException {
        return ownerService.createWithAdminRole(getCreateOwnerDTOWithAddress());
    }

    protected Breed createBreed() throws AlreadyExistsException {
        return breedService.create(getCreateBreedDto());
    }

    protected Setting createSetting() throws AlreadyExistsException {
        return settingService.create(getCreateSettingDto());
    }

    protected String cpf() {
        return new CpfHandlerService().cpf(false);
    }

    protected CreatePetDTO getCreatePetDto() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        Breed breed = createBreed();
        CreatePetDTO dto = new CreatePetDTO();
        dto.setBirthDate(new Date());
        dto.setOwnerId(owner.getId());
        dto.setHasPedigree(true);
        dto.setBreedId(breed.getId());
        dto.setName("Maya");
        return dto;
    }

    protected CreateBreedDTO getCreateBreedDto(){
        CreateBreedDTO dto = new CreateBreedDTO();
        dto.setDescription("Cachorro mais bravo que tem!");
        dto.setType(PetType.DOG);
        dto.setName("CHIHUAHUA");
        return dto;
    }

    protected Date getNineTeenYearsAgo(){
        return Date.from(
                LocalDateTime
                        .now()
                        .minusYears(19)
                        .atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    protected CreateOwnerDTO getCreateOwnerDTOWithoutAddress(){
        CreateOwnerDTO dto = new CreateOwnerDTO();
        dto.setName("Filipe Brenner");
        dto.setCpf(cpf());
        dto.setEmail(uuid() + "filipe.brener@ufv.br");
        dto.setCelNumber(uuid().substring(0,11));
        dto.setPassword(password);
        dto.setUsername("@cachorrinho" + uuid());
        dto.setBirthDate(getNineTeenYearsAgo());
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

    protected CreateSettingDto getCreateSettingDto(){
        CreateSettingDto setting = new CreateSettingDto();
        setting.setCode("setting code");
        setting.setValue("setting value");
        return setting;
    }

    protected String uuid(){
        return UUID.randomUUID().toString();
    }
}
