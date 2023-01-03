package br.com.petinder.backend.dtos.pet;

import br.com.petinder.backend.domains.Pet;
import br.com.petinder.backend.dtos.pet.CreatePetDTO;

public class ResponsePetDTO extends CreatePetDTO {
    public ResponsePetDTO(Pet persistedPet) {
    }
}
