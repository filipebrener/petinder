package br.com.petinder.backend.controllers;

import br.com.petinder.backend.BaseTest;
import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.dtos.response.MessageDTO;
import br.com.petinder.backend.dtos.breed.CreateBreedDTO;
import br.com.petinder.backend.dtos.breed.EditBreedDTO;
import br.com.petinder.backend.dtos.breed.ResponseBreedDTO;
import br.com.petinder.backend.dtos.response.errors.ErrorsListDTO;
import br.com.petinder.backend.dtos.response.errors.FieldErrorsMessageDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.BreedRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BreedControllerTest extends BaseTest {

    @Autowired
    private BreedRepository breedRepository;

    @BeforeEach
    public void beforeEach(){
        breedRepository.deleteAll();
    }

    @Test
    public void createBreedSuccessfully() throws NotFoundException, AlreadyExistsException {
        Owner admin = createOwnerWithAdminRole();
        CreateBreedDTO dto = getCreateBreedDto();
        String breedNameInUpperCase = "BORDER COLLIE";
        String breedNameCapitalized = "Border collie";
        dto.setName(breedNameInUpperCase);
        ResponseBreedDTO response = webTestClient.post()
                .uri("/breed/create")
                .bodyValue(dto)
                .headers( httpHeaders -> httpHeaders.setBasicAuth(admin.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(ResponseBreedDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        Breed persistedBreed = breedService.findById(response.getId());
        assertNotNull(persistedBreed);
        assertEquals(dto.getDescription(), persistedBreed.getDescription());
        assertEquals(
                breedNameCapitalized,
                persistedBreed.getName(),
                "O nome deve ser parseado para apenas a primeira letra seja maiuscula!"
        );
        assertEquals(dto.getType(), persistedBreed.getType());
    }

    @Test
    public void createAlreadyExistingBreedTest() throws AlreadyExistsException {
        Owner admin = createOwnerWithAdminRole();
        Breed breed = createBreed();
        CreateBreedDTO requestBody = getCreateBreedDto();
        requestBody.setName(breed.getName());
        ErrorsListDTO response = webTestClient.post()
                .uri("/breed/create")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(admin.getUsername(), password))
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorsListDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível criar a raça!", response.getMessage());
        assertEquals(1, response.getErrors().size());
    }

    @Test
    public void deleteBreedSuccessfully() throws AlreadyExistsException {
        Owner admin = createOwnerWithAdminRole();
        Breed breed = createBreed();
        MessageDTO response = webTestClient.delete()
                .uri("/breed/delete/" + breed.getId())
                .headers( httpHeaders -> httpHeaders.setBasicAuth(admin.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(MessageDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Raça apagada com sucesso!", response.getMessage());
        assertTrue(breedRepository.findById(breed.getId()).isEmpty());
    }

    @Test
    public void editBreedNameSuccessfully() throws AlreadyExistsException {
        Owner admin = createOwnerWithAdminRole();
        Breed breed = createBreed();
        String newName = "Hot dog";
        EditBreedDTO requestBody = new EditBreedDTO(breed);
        requestBody.setName(newName);
        ResponseBreedDTO response = webTestClient.put()
                .uri("/breed/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(admin.getUsername(), password))
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ResponseBreedDTO.class)
                .returnResult().getResponseBody();

        Optional<Breed> optionalPersistedBreed = breedRepository.findById(breed.getId());
        assertFalse(optionalPersistedBreed.isEmpty());
        Breed persistedBreed = optionalPersistedBreed.get();
        assertNotNull(persistedBreed);
        assertNotNull(response);
        assertEquals(newName, response.getName());
        assertEquals(response.getName(), persistedBreed.getName());
    }

    @Test
    public void editBreedDescriptionSuccessfully() throws AlreadyExistsException {
        Owner admin = createOwnerWithAdminRole();
        Breed breed = createBreed();
        String newDescription = "Cachorro quente mais gostoso do oeste!";
        EditBreedDTO requestBody = new EditBreedDTO(breed);
        requestBody.setDescription(newDescription);
        ResponseBreedDTO response = webTestClient.put()
                .uri("/breed/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(admin.getUsername(), password))
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ResponseBreedDTO.class)
                .returnResult().getResponseBody();

        Optional<Breed> optionalPersistedBreed = breedRepository.findById(breed.getId());
        assertFalse(optionalPersistedBreed.isEmpty());
        Breed persistedBreed = optionalPersistedBreed.get();
        assertNotNull(persistedBreed);
        assertNotNull(response);
        assertEquals(newDescription, response.getDescription());
        assertEquals(response.getDescription(), persistedBreed.getDescription());
    }

    @Test
    public void editNonExistingBreed() throws AlreadyExistsException {
        Owner admin = createOwnerWithAdminRole();
        EditBreedDTO requestBody = new EditBreedDTO();
        String uuid = uuid();
        Long id = 1234L;
        requestBody.setId(id);
        requestBody.setName("nome: " + uuid);
        requestBody.setDescription("descrição: " + uuid);
        ErrorsListDTO response = webTestClient.put()
                .uri("/breed/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(admin.getUsername(), password))
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorsListDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível encontrar uma raça com o id: " + id, response.getMessage());
    }

    @Test
    public void deleteNonExistingBreed() throws AlreadyExistsException {
        Owner admin = createOwnerWithAdminRole();
        Long id = 1234L;
        ErrorsListDTO response = webTestClient.delete()
                .uri("/breed/delete/" + id)
                .headers( httpHeaders -> httpHeaders.setBasicAuth(admin.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorsListDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível encontrar uma raça com o id: " + id, response.getMessage());
    }

    @Test
    public void editBreedSendingRequestWithoutRequiredFieldsTest() throws AlreadyExistsException {
        Owner admin = createOwnerWithAdminRole();
        EditBreedDTO requestBody = new EditBreedDTO();
        List<FieldErrorsMessageDTO> response = webTestClient.put()
                .uri("/breed/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(admin.getUsername(), password))
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }

    @Test
    public void createBreedSendingRequestWithoutRequiredFieldsTest() throws AlreadyExistsException {
        Owner admin = createOwnerWithAdminRole();
        CreateBreedDTO requestBody = new CreateBreedDTO();
        List<FieldErrorsMessageDTO> response = webTestClient.post()
                .uri("/breed/create")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(admin.getUsername(), password))
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }


}

