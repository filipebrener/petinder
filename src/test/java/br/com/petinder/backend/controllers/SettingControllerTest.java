package br.com.petinder.backend.controllers;

import br.com.petinder.backend.BaseTest;
import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.domains.Setting;
import br.com.petinder.backend.dtos.response.MessageDTO;
import br.com.petinder.backend.dtos.response.errors.ErrorMessageDto;
import br.com.petinder.backend.dtos.response.errors.ErrorsListDTO;
import br.com.petinder.backend.dtos.response.errors.FieldErrorsMessageDTO;
import br.com.petinder.backend.dtos.setting.CreateSettingDto;
import br.com.petinder.backend.dtos.setting.EditSettingDto;
import br.com.petinder.backend.dtos.setting.ResponseSettingDto;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.repositories.SettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SettingControllerTest extends BaseTest {

    @Autowired
    private SettingRepository settingRepository;

    @BeforeEach
    public void beforeEach(){
        settingRepository.deleteAll();
    }

    @Test
    public void createSettingSuccessfully() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        CreateSettingDto request = getCreateSettingDto();
        ResponseSettingDto responseBody = webTestClient.post()
                .uri("/setting/create")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(ResponseSettingDto.class)
                .returnResult().getResponseBody();

        assertNotNull(responseBody);

        Optional<Setting> optionalSetting = settingRepository.findById(responseBody.getId());
        assertTrue(optionalSetting.isPresent());
        Setting persistedSetting = optionalSetting.get();
        assertEquals(request.getCode(),persistedSetting.getCode());
        assertEquals(request.getCode(), responseBody.getCode());
        assertEquals(request.getValue(),persistedSetting.getValue());
        assertEquals(request.getValue(), responseBody.getValue());
    }

    @Test
    public void createSettingWithoutRequiredRole() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        CreateSettingDto request = getCreateSettingDto();
        webTestClient.post()
                .uri("/setting/create")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    public void createSettingWithoutRequiredFields() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        CreateSettingDto request = new CreateSettingDto();
        List<FieldErrorsMessageDTO> response = webTestClient.post()
                .uri("/setting/create")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();
        assertNotNull(response);
    }

    @Test
    public void createExistingSetting() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        Setting setting = createSetting();
        CreateSettingDto request = getCreateSettingDto();
        request.setCode(setting.getCode());
        ErrorsListDTO response = webTestClient.post()
                .uri("/setting/create")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorsListDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);

    }

    @Test
    public void deleteSettingSuccessfully() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        Setting setting = createSetting();
        MessageDTO response = webTestClient.delete()
                .uri("/setting/delete/" + setting.getId())
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(MessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Setting apagada com sucesso!", response.getMessage());
        Optional<Setting> optionalSetting = settingRepository.findById(setting.getId());
        assertTrue(optionalSetting.isEmpty());
    }

    @Test
    public void deleteSettingWithoutRequiredRole() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        Setting setting = createSetting();
        webTestClient.delete()
                .uri("/setting/delete/" + setting.getId())
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void deleteNonExistingSetting() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        ErrorMessageDto response = webTestClient.delete()
                .uri("/setting/delete/1234")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorMessageDto.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível encontrar uma configuração com o id: 1234", response.getMessage());
    }

    @Test
    public void editSettingSuccessfully() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        EditSettingDto request = new EditSettingDto(createSetting());
        request.setCode("new code");
        request.setValue("new value");
        ResponseSettingDto response = webTestClient.put()
                .uri("/setting/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ResponseSettingDto.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals(request.getId(), response.getId());
        assertEquals(request.getCode(), response.getCode());
        assertEquals(request.getValue(), response.getValue());
        Optional<Setting> optionalSetting = settingRepository.findById(request.getId());
        assertTrue(optionalSetting.isPresent());
        Setting persistedSetting = optionalSetting.get();
        assertEquals(request.getValue(), persistedSetting.getValue());
        assertEquals(request.getCode(), persistedSetting.getCode());
    }

    @Test
    public void editSettingWithoutRequiredRule() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        EditSettingDto request = new EditSettingDto(createSetting());
        request.setCode("new code");
        request.setValue("new value");
        webTestClient.put()
                .uri("/setting/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }
    @Test
    public void editNonExistingSetting() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        EditSettingDto request = new EditSettingDto();
        request.setId(1234L);
        request.setCode("code");
        request.setValue("value");
        ErrorMessageDto response = webTestClient.put()
                .uri("/setting/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorMessageDto.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível encontrar uma configuração com o id: 1234", response.getMessage());
    }

    @Test
    public void editSettingWithoutRequiredFields() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        EditSettingDto request = new EditSettingDto();
        List<FieldErrorsMessageDTO> response = webTestClient.put()
                .uri("/setting/edit")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }

    @Test
    public void getSettingListWithoutRequiredFields() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        Setting setting = createSetting();
        EditSettingDto request = new EditSettingDto();
        webTestClient.put()
                .uri("/setting/listAll")
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void getSettingSuccessfully() throws AlreadyExistsException {
        Owner owner = createOwnerWithAdminRole();
        Setting setting = createSetting();
        EditSettingDto request = new EditSettingDto();
        ResponseSettingDto response = webTestClient.get()
                .uri("/setting/get/" + setting.getId())
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ResponseSettingDto.class).returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals(setting.getId(), response.getId());
        assertEquals(setting.getCode(), response.getCode());
        assertEquals(setting.getValue(), response.getValue());
    }

    @Test
    public void getSettingWithoutRequiredFields() throws AlreadyExistsException {
        Owner owner = createOwnerWithUserRole();
        Setting setting = createSetting();
        EditSettingDto request = new EditSettingDto();
        webTestClient.get()
                .uri("/setting/get/" + setting.getId())
                .headers( httpHeaders -> httpHeaders.setBasicAuth(owner.getUsername(), password))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }
}
