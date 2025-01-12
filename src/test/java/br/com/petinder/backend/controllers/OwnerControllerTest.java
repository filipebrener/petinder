package br.com.petinder.backend.controllers;

import br.com.petinder.backend.BaseTest;
import br.com.petinder.backend.domains.Address;
import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.dtos.response.MessageDTO;
import br.com.petinder.backend.dtos.address.CreateAddressDTO;
import br.com.petinder.backend.dtos.response.errors.ErrorMessageDto;
import br.com.petinder.backend.dtos.response.errors.ErrorsListDTO;
import br.com.petinder.backend.dtos.response.errors.FieldErrorsMessageDTO;
import br.com.petinder.backend.dtos.owner.CreateOwnerDTO;
import br.com.petinder.backend.dtos.owner.EditOwnerDTO;
import br.com.petinder.backend.dtos.owner.ResponseOwnerDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.OwnerRepository;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerTest extends BaseTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    public void beforeEach() throws AlreadyExistsException {
        ownerRepository.deleteAll();
    }

    @Test
    public void createOwnerWithAddressSuccessfully() throws NotFoundException {
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
        Owner persistedOwner = ownerService.findById(response.getId());
        assertAllOwnerFields(persistedOwner, dto);
    }

    @Test
    public void createOwnerWithoutAddressSuccessfully() throws NotFoundException {
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
        Owner persistedOwner = ownerService.findById(response.getId());
        assertOwnerDetails(persistedOwner, dto);
        assertNull(persistedOwner.getAddress());
        assertNull(dto.getAddress());
    }

    @Test
    public void createOwnerWithoutRequiredMinAge(){
        CreateOwnerDTO dto = getCreateOwnerDTOWithAddress();
        dto.setBirthDate(new Date());
        List<FieldErrorsMessageDTO> response = webTestClient.post()
                .uri("/owner/create")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult()
                .getResponseBody();

        assertEquals(1, response.size());
        assertEquals(response.get(0).getField(), "birthDate");
        assertEquals(response.get(0).getErrors().get(0), "É obrigatório ter no mínimo 18 anos!");
    }

    @Test
    public void creatingOwnerWithDuplicateCpfTest() throws AlreadyExistsException {
        Owner peresistedOwner = createOwnerWithUserRole();
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
        Owner peresistedOwner = createOwnerWithUserRole();
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
        Owner peresistedOwner = createOwnerWithUserRole();
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
        Owner owner = createOwnerWithUserRole();
        EditOwnerDTO dto = new EditOwnerDTO();
        dto.setId(owner.getId());
        dto.setName(newName);
        dto.setCelNumber(newCelNumber);
        Date nineteenYearsAgo = getNineTeenYearsAgo();
        dto.setBirthDate(nineteenYearsAgo);
        webTestClient.put()
                .uri("/owner/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ResponseOwnerDTO.class);
        Optional<Owner> optionalEditedOwner = ownerRepository.findById(owner.getId());
        assertFalse(optionalEditedOwner.isEmpty());
        Owner editedOwner = optionalEditedOwner.get();
        assertEquals(newName, editedOwner.getName());
        assertEquals(newCelNumber, editedOwner.getCelNumber());
        assertEquals(DateUtil.truncateTime(nineteenYearsAgo), DateUtil.truncateTime(editedOwner.getBirthDate()));
    }
    @Test
    public void deleteOwnerTest() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        MessageDTO response = webTestClient.delete()
                .uri("/owner/delete/" + owner.getId())
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(MessageDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Usuário apagado com sucesso!", response.getMessage());
        assertTrue(ownerRepository.findById(owner.getId()).isEmpty());
    }

    @Test
    public void deleteInvalidOwnerTest() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        Owner secondOwner = createOwnerWithUserRole();

        ErrorMessageDto response = webTestClient.delete()
                .uri("/owner/delete/" + secondOwner.getId())
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN)
                .expectBody(ErrorMessageDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("O usuário logado não tem permissão para apagar esse usuário!", response.getMessage());

    }

    @Test
    public void editNonExistingOwnerTest() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        String newName = "Filipe Brenner";
        String newCelNumber = "+5538999810408";
        EditOwnerDTO dto = new EditOwnerDTO();
        dto.setId(1234L);
        dto.setName(newName);
        dto.setCelNumber(newCelNumber);
        dto.setBirthDate(getNineTeenYearsAgo());
        ErrorsListDTO response = webTestClient.put()
                .uri("/owner/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorsListDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }

    @Test
    public void editInvalidOwnerTest() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        Owner secondOwner = createOwnerWithUserRole();
        String newName = "Filipe Brenner";
        String newCelNumber = "+5538999810408";
        EditOwnerDTO dto = new EditOwnerDTO();
        dto.setId(secondOwner.getId());
        dto.setName(newName);
        dto.setCelNumber(newCelNumber);
        dto.setBirthDate(getNineTeenYearsAgo());
        ErrorMessageDto response = webTestClient.put()
                .uri("/owner/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN)
                .expectBody(ErrorMessageDto.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("O usuário logado não tem permissão para editar esse usuário!", response.getMessage());
    }

    @Test
    public void deleteNonExistingOwnerTest() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        ErrorsListDTO response = webTestClient.delete()
                .uri("/owner/delete/" + 1234)
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorsListDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }

    @Test
    public void editOwnerSendingRequestWithoutRequiredFieldsTest() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        EditOwnerDTO requestBody = new EditOwnerDTO();
        List<FieldErrorsMessageDTO> response = webTestClient.put()
                .uri("/owner/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }

    @Test
    public void createOwnerSendingRequestWithoutRequiredFieldsTest() throws AlreadyExistsException {
        CreateOwnerDTO requestBody = new CreateOwnerDTO();
        List<FieldErrorsMessageDTO> response = webTestClient.post()
                .uri("/owner/create")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }

    @Test
    public void createOwnerWithInvalidPassword() throws AlreadyExistsException {
        CreateOwnerDTO dto = getCreateOwnerDTOWithAddress();
        dto.setPassword("1234567");
        List<FieldErrorsMessageDTO> response = webTestClient.post()
                .uri("/owner/create")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("password", response.get(0).getField());
    }

    private void assertAllOwnerFields(Owner persistedOwner, CreateOwnerDTO dto){
        assertOwnerDetails(persistedOwner, dto);
        assertOwnerAddress(persistedOwner, dto);
    }

    private void assertOwnerDetails(Owner persistedOwner, CreateOwnerDTO dto){
        assertNotEquals( null, persistedOwner, "O cliente(Dono) não deve estar nulo");
        assertEquals(dto.getName(), persistedOwner.getName(), "O nome deve ser igual");
        assertEquals(dto.getEmail(), persistedOwner.getEmail(), "O email deve ser igual");
        assertEquals(dto.getCpf(), persistedOwner.getCpf(), "O CPF deve ser igual");
        assertEquals(dto.getCelNumber(), persistedOwner.getCelNumber(), "O número de celular deve ser igual");
    }

    private void assertOwnerAddress(Owner persistedOwner, CreateOwnerDTO dto){
        assertNotEquals(null, persistedOwner.getAddress(), "O endereço não deve estar nulo");
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
