package br.com.petinder.backend.services;

import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.domains.Pet;
import br.com.petinder.backend.dtos.pet.CreatePetDTO;
import br.com.petinder.backend.dtos.pet.EditPetDTO;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final BreedService breedService;

    public PetService(PetRepository petRepository, BreedService breedService) {
        this.petRepository = petRepository;
        this.breedService = breedService;
    }

    public Pet create(CreatePetDTO requestDTO) throws NotFoundException {
        Pet pet = new Pet(requestDTO);
        Breed breed = breedService.findByUuid(requestDTO.getBreedUuid());
        pet.setBreed(breed);
        return petRepository.save(pet);
    }

    public Pet getByUuid(String uuid) throws NotFoundException {
        Pet pet = petRepository.findByUuid(uuid);
        if (pet == null) throw new NotFoundException("Não foi possível encontrar um pet com o uuid: " + uuid);
        return pet;
    }

    public void delete(String uuid) throws NotFoundException {
        Pet pet = petRepository.findByUuid(uuid);
        if (pet == null) throw new NotFoundException("Não foi possível encontrar um pet com o uuid: " + uuid);
        petRepository.delete(pet);
    }

    public List<Pet> findAll() {
        return petRepository.findAll().stream().toList();
    }

    public Pet edit(EditPetDTO dto) throws NotFoundException {
        Pet pet = petRepository.findByUuid(dto.getUuid());
        if(pet == null) throw new NotFoundException("Não foi possível encontrar um pet com uuid: " + dto.getUuid());
        pet.setAge(dto.getAge());
        pet.setHasPedigree(dto.getHasPedigree());
        pet.setName(dto.getName());
        Breed breed = breedService.findByUuid(dto.getBreedUuid());
        pet.setBreed(breed);
        return petRepository.save(pet);
    }
}
