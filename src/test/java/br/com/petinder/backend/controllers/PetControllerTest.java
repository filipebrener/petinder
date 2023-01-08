package br.com.petinder.backend.controllers;


import br.com.petinder.backend.BaseTest;
import br.com.petinder.backend.domains.Pet;
import br.com.petinder.backend.dtos.pet.CreatePetDTO;
import br.com.petinder.backend.dtos.pet.ResponsePetDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetControllerTest extends BaseTest {

    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    public void beforeEach(){
        petRepository.deleteAll();
    }

    @Test
    public void createPetSuccessfully() throws AlreadyExistsException {
        CreatePetDTO requestBody = getCreatePetDto();
        ResponsePetDTO responseBody = webTestClient.post()
                .uri("/pet/create")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(ResponsePetDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(responseBody);

        Optional<Pet> optionalPersistedPet = petRepository.findById(responseBody.getId());
        assertFalse(optionalPersistedPet.isEmpty());
        Pet persistedPet = optionalPersistedPet.get();
        assertNotNull(persistedPet);
        assertEquals(requestBody.getOwnerId(), persistedPet.getOwner().getId());
        // TODO: Terminar de verificar os campos
    }

}
