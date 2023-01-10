package br.com.petinder.backend.controllers;

import br.com.petinder.backend.BaseTest;
import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.domains.Pet;
import br.com.petinder.backend.dtos.breed.CreateBreedDTO;
import br.com.petinder.backend.dtos.breed.ResponseBreedDTO;
import br.com.petinder.backend.dtos.pet.CreatePetDTO;
import br.com.petinder.backend.dtos.pet.EditPetDTO;
import br.com.petinder.backend.dtos.pet.ResponsePetDTO;
import br.com.petinder.backend.dtos.response.MessageDTO;
import br.com.petinder.backend.dtos.response.errors.ErrorMessage;
import br.com.petinder.backend.dtos.response.errors.FieldErrorsMessageDTO;
import br.com.petinder.backend.enums.PetType;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.BreedRepository;
import br.com.petinder.backend.repositories.OwnerRepository;
import br.com.petinder.backend.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetControllerTest extends BaseTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BreedRepository breedRepository;

    @BeforeEach
    public void beforeEach(){
        petRepository.deleteAll();
        ownerRepository.deleteAll();
        breedRepository.deleteAll();
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(sdf.format(requestBody.getBirthDate()), sdf.format(persistedPet.getBirthDate()));
        assertEquals(requestBody.getBreedId(), persistedPet.getBreed().getId());
        assertEquals(requestBody.getName(), persistedPet.getName());
    }

    @Test
    public void sendCreateRequestWithoutRequiredFields(){
        CreatePetDTO requestBody = new CreatePetDTO();
        List<FieldErrorsMessageDTO> response = webTestClient.post()
                .uri("/pet/create")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }
    @Test
    public void deletePetSuccessfully() throws AlreadyExistsException, NotFoundException {
        Pet pet = createPet();
        MessageDTO response = webTestClient.delete()
                .uri("/pet/delete/" + pet.getId())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(MessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Pet apagado com sucesso!", response.getMessage());
    }

    @Test
    public void deleteNonExistingPet(){
        MessageDTO response = webTestClient.delete()
                .uri("/pet/delete/1234")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(MessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível encontrar um pet com o id: 1234", response.getMessage());
    }

    @Test
    public void editPetSuccessfully() throws AlreadyExistsException, NotFoundException {
        Pet pet = createPet();
        EditPetDTO requestBody = new EditPetDTO(pet);
        requestBody.setName("Nome novo");
        requestBody.setHasPedigree(false);
        ResponsePetDTO response = webTestClient.put()
                .uri("/pet/edit")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ResponsePetDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        Optional<Pet> optionalPersistedPet = petRepository.findById(pet.getId());
        assertTrue(optionalPersistedPet.isPresent());
        Pet persistedPet = optionalPersistedPet.get();
        assertEquals(response.getName(), persistedPet.getName());
        assertEquals(requestBody.getName(), persistedPet.getName());
        assertEquals(response.hasPedigree(), persistedPet.hasPedigree());
        assertEquals(requestBody.hasPedigree(), persistedPet.hasPedigree());
    }

    @Test
    public void changePetBreedSuccessfully() throws AlreadyExistsException, NotFoundException {
        Pet pet = createPet();
        long firstBreedId = pet.getBreed().getId();
        CreateBreedDTO newBreedRequest = new CreateBreedDTO();
        newBreedRequest.setName("pug");
        newBreedRequest.setType(PetType.DOG);
        newBreedRequest.setDescription("cachorrinho do MIB");
        ResponseBreedDTO newBreedResponse = webTestClient.post()
                .uri("/breed/create")
                .bodyValue(newBreedRequest)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(ResponseBreedDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(newBreedResponse);
        EditPetDTO editPetDTO = new EditPetDTO(pet);
        editPetDTO.setBreedId(newBreedResponse.getId());

        ResponsePetDTO petWithNewBreed = webTestClient.put()
                .uri("/pet/edit")
                .bodyValue(editPetDTO)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ResponsePetDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(petWithNewBreed);
        long newResponseBreedId = petWithNewBreed.getBreed().getId();
        assertEquals(pet.getId(), petWithNewBreed.getId());
        Optional<Pet> optionalPersistedPet = petRepository.findById(pet.getId());
        assertTrue(optionalPersistedPet.isPresent());
        Pet persistedPet = optionalPersistedPet.get();
        assertNotEquals(firstBreedId, newResponseBreedId);
        assertNotEquals(firstBreedId, persistedPet.getBreed().getId());
        assertEquals(petWithNewBreed.getBreed().getId(), persistedPet.getBreed().getId());
    }

    @Test
    public void sendEditRequestWithoutRequiredFields(){
        CreatePetDTO requestBody = new CreatePetDTO();
        List<FieldErrorsMessageDTO> response = webTestClient.post()
                .uri("/pet/create")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }

    @Test
    public void editNonExistingPet() throws AlreadyExistsException {
        Breed breed = createBreed();
        EditPetDTO editPetDTO = new EditPetDTO();
        editPetDTO.setBreedId(breed.getId());
        editPetDTO.setName("Maya");
        editPetDTO.setHasPedigree(true);
        editPetDTO.setBirthDate(new Date());
        editPetDTO.setId(1234);
        MessageDTO response = webTestClient.put()
                .uri("/pet/edit")
                .bodyValue(editPetDTO)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(MessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível encontrar um pet com o id: 1234", response.getMessage());
    }

    @Test
    public void setNonExistingBreedForPet() throws AlreadyExistsException, NotFoundException {
        Pet pet = createPet();
        EditPetDTO editPetDTO = new EditPetDTO(pet);
        editPetDTO.setBreedId(1234);
        ErrorMessage response = webTestClient.put()
                .uri("/pet/edit")
                .bodyValue(editPetDTO)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível encontrar uma raça com o id: 1234", response.getMessage());
    }
}