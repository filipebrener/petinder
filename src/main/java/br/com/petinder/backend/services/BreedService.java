package br.com.petinder.backend.services;

import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.dtos.breed.CreateBreedDTO;
import br.com.petinder.backend.dtos.breed.EditBreedDTO;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreedService {

    @Autowired
    private BreedRepository breedRepository;

    public Breed findByUuid(String breedUuid) throws NotFoundException {
        Breed breed = breedRepository.findByUuid(breedUuid);
        if (breed == null) throw new NotFoundException("Não foi possível encontrar uma raça com o uuid: " + breedUuid);
        return breed;
    }

    public Breed create(CreateBreedDTO requestDTO) {
        Breed breed = new Breed(requestDTO);
        return breedRepository.save(breed);
    }

    public void delete(String uuid) throws NotFoundException {
        Breed breed = findByUuid(uuid);
        breedRepository.delete(breed);
    }

    public List<Breed> findAll() {
        return breedRepository.findAll().stream().toList();
    }

    public Breed edit(EditBreedDTO dto) throws NotFoundException {
        Breed breed = findByUuid(dto.getUuid());
        breed.setDescription(dto.getDescription());
        breed.setName(dto.getName());
        return breedRepository.save(breed);
    }
}
