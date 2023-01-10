package br.com.petinder.backend.services;

import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.domains.Pet;
import br.com.petinder.backend.dtos.pet.CreatePetDTO;
import br.com.petinder.backend.dtos.pet.EditPetDTO;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final BreedService breedService;
    private final OwnerService ownerService;
    private final SettingService settingService;

    public PetService(PetRepository petRepository, BreedService breedService, OwnerService ownerService, SettingService settingService) {
        this.petRepository = petRepository;
        this.breedService = breedService;
        this.ownerService = ownerService;
        this.settingService = settingService;
    }

    public Pet create(CreatePetDTO requestDTO) throws NotFoundException {
        Pet pet = new Pet(requestDTO);
        Breed breed = breedService.findById(requestDTO.getBreedId());
        pet.setBreed(breed);
        Owner owner = ownerService.findById(requestDTO.getOwnerId());
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    public Pet findById(long id) throws NotFoundException {
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isEmpty()) throw new NotFoundException("Não foi possível encontrar um pet com o id: " + id);
        return optionalPet.get();
    }

    public void delete(long id) throws NotFoundException {
        Pet pet = findById(id);
        petRepository.delete(pet);
    }

    public List<Pet> findAll() {
        return petRepository.findAll().stream().toList();
    }

    public Pet edit(EditPetDTO dto) throws NotFoundException {
        Pet pet = findById(dto.getId());
        pet.setBirthDate(dto.getBirthDate());
        pet.setHasPedigree(dto.hasPedigree());
        pet.setName(dto.getName());
        Breed breed = breedService.findById(dto.getBreedId());
        pet.setBreed(breed);
        return petRepository.save(pet);
    }

    public String getBirthDateFormatted(Pet pet){
        String format = settingService.getDateFormatSettingValue("dd/MM/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(pet.getBirthDate());
    }
}
