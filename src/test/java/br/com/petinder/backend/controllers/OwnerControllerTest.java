package br.com.petinder.backend.controllers;

import br.com.petinder.backend.BaseTest;
import br.com.petinder.backend.domains.Address;
import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.dtos.MessageDTO;
import br.com.petinder.backend.dtos.address.CreateAddressDTO;
import br.com.petinder.backend.dtos.errors.ErrorsListDTO;
import br.com.petinder.backend.dtos.owner.CreateOwnerDTO;
import br.com.petinder.backend.dtos.owner.EditOwnerDTO;
import br.com.petinder.backend.dtos.owner.ResponseOwnerDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerTest extends BaseTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    public void beforeEach(){
        ownerRepository.deleteAll();
    }

    @Test
    public void createOwnerWithAddressSuccessfully(){
        CreateOwnerDTO dto = getCreateOwnerDTOWithAddress();
        ResponseOwnerDTO response = webTestClient.post()
                .uri("/owner/create")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(ResponseOwnerDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        Owner persistedOwner = ownerRepository.findByUuid(response.getUuid());
        assertAllOwnerFields(persistedOwner, dto);
    }

    @Test
    public void createOwnerWithoutAddressSuccessfully(){
        CreateOwnerDTO dto = getCreateOwnerDTOWithoutAddress();
        ResponseOwnerDTO response = webTestClient.post()
                .uri("/owner/create")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(ResponseOwnerDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        Owner persistedOwner = ownerRepository.findByUuid(response.getUuid());
        assertOwnerDetails(persistedOwner, dto);
        assertNull(persistedOwner.getAddress());
        assertNull(dto.getAddress());
    }

    @Test
    public void creatingOwnerWithDuplicateCpfTest() throws AlreadyExistsException {
        Owner peresistedOwner = createOwner();
        CreateOwnerDTO dto = getCreateOwnerDTOWithoutAddress();
        dto.setCpf(peresistedOwner.getCpf());
        ErrorsListDTO response = webTestClient.post()
                .uri("/owner/create")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorsListDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertNotNull(response.getMessage());
        assertEquals(1, response.getErrors().size());
        assertEquals("Não foi possível criar o usuário!", response.getMessage());
        assertEquals("CPF informado já foi cadastrado!", response.getErrors().get(0));
    }

    @Test
    public void creatingOwnerWithDuplicateCelNumberTest() throws AlreadyExistsException {
        Owner peresistedOwner = createOwner();
        CreateOwnerDTO dto = getCreateOwnerDTOWithoutAddress();
        dto.setCelNumber(peresistedOwner.getCelNumber());
        ErrorsListDTO response = webTestClient.post()
                .uri("/owner/create")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorsListDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertNotNull(response.getMessage());
        assertEquals(1, response.getErrors().size());
        assertEquals("Não foi possível criar o usuário!", response.getMessage());
        assertEquals("Número de celular informado já foi cadastrado!", response.getErrors().get(0));
    }

    @Test
    public void creatingOwnerWithDuplicateEmailTest() throws AlreadyExistsException {
        Owner peresistedOwner = createOwner();
        CreateOwnerDTO dto = getCreateOwnerDTOWithoutAddress();
        dto.setEmail(peresistedOwner.getEmail());
        ErrorsListDTO response = webTestClient.post()
                .uri("/owner/create")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorsListDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertNotNull(response.getMessage());
        assertEquals(1, response.getErrors().size());
        assertEquals("Não foi possível criar o usuário!", response.getMessage());
        assertEquals("E-mail informado já foi cadastrado!", response.getErrors().get(0));
    }

    @Test
    public void editOwnerTest() throws AlreadyExistsException {
        String newName = "Filipe Brenner";
        String newCelNumber = "+5538999810408";
        Owner peresistedOwner = createOwner();
        EditOwnerDTO dto = new EditOwnerDTO();
        dto.setUuid(peresistedOwner.getUuid());
        dto.setName(newName);
        dto.setCelNumber(newCelNumber);
        webTestClient.put()
                .uri("/owner/edit")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ResponseOwnerDTO.class);
        Owner editedOwner = ownerRepository.findByUuid(peresistedOwner.getUuid());
        assertEquals(newName, editedOwner.getName());
        assertEquals(newCelNumber, editedOwner.getCelNumber());
    }
    @Test
    public void deleteOwnerTest() throws AlreadyExistsException {
        Owner owner = createOwner();

        MessageDTO response = webTestClient.delete()
                .uri("/owner/delete/" + owner.getUuid())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(MessageDTO.class)
                .returnResult()
                .getResponseBody();

        assertEquals("Usuário apagado com sucesso!", response.getMessage());
        assertNull(ownerRepository.findByUuid(owner.getUuid()));
    }

    private void assertAllOwnerFields(Owner persistedOwner, CreateOwnerDTO dto){
        assertOwnerDetails(persistedOwner, dto);
        assertOwnerAddress(persistedOwner, dto);
    }

    private void assertOwnerDetails(Owner persistedOwner, CreateOwnerDTO dto){
        assertNotEquals( null, persistedOwner);
        assertEquals(dto.getName(), persistedOwner.getName(), "O nome deve ser igual");
        assertEquals(dto.getEmail(), persistedOwner.getEmail(), "O email deve ser igual");
        assertEquals(dto.getCpf(), persistedOwner.getCpf(), "O CPF deve ser igual");
        assertEquals(dto.getCelNumber(), persistedOwner.getCelNumber(), "O número de celular deve ser igual");
    }

    private void assertOwnerAddress(Owner persistedOwner, CreateOwnerDTO dto){
        assertNotEquals(null, persistedOwner.getAddress());
        Address address = persistedOwner.getAddress();
        CreateAddressDTO addressDTO = dto.getAddress();
        assertEquals(addressDTO.getCountry(), address.getCountry(), "O país deve ser igual");
        assertEquals(addressDTO.getState(), address.getState(), "O estado deve ser igual");
        assertEquals(addressDTO.getCity(), address.getCity(), "A cidade deve ser igual");
        assertEquals(addressDTO.getNeighborhood(), address.getNeighborhood(), "O bairro deve ser igual");
        assertEquals(addressDTO.getStreet(), address.getStreet(), "A rua deve ser igual");
        assertEquals(addressDTO.getNumber(), address.getNumber(), "O número deve ser igual");
        assertEquals(addressDTO.getComplement(), address.getComplement(), "O complemento deve ser igual");
        assertEquals(addressDTO.getZipCode(), address.getZipCode(), "O CEP deve ser igual");
    }

}
