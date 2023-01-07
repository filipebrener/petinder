package br.com.petinder.backend.services;

import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.dtos.breed.CreateBreedDTO;
import br.com.petinder.backend.dtos.breed.EditBreedDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Breed create(CreateBreedDTO dto) throws AlreadyExistsException {
        Breed breed = new Breed(dto);
        List<String> errors = getBreedPersistenceValidationErrors(dto);
        boolean hasErrors = !errors.isEmpty();
        if (hasErrors) throw new AlreadyExistsException("Não foi possível criar a raça!", errors);
        return breedRepository.save(breed);
    }

    private List<String> getBreedPersistenceValidationErrors(CreateBreedDTO dto) {
        ArrayList<String> errors = new ArrayList<>();
        if (breedRepository.countByNameIgnoreCase(dto.getName()) > 0) errors.add("A raça " + dto.getName() + " já existe!");
        return errors;
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
