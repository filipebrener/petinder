package br.com.petinder.backend.controllers;

import br.com.petinder.backend.BaseTest;
import br.com.petinder.backend.domains.Setting;
import br.com.petinder.backend.dtos.response.MessageDTO;
import br.com.petinder.backend.dtos.response.errors.ErrorMessage;
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
    public void createSettingSuccessfully(){
        CreateSettingDto request = getCreateSettingDto();
        ResponseSettingDto responseBody = webTestClient.post()
                .uri("/setting/create")
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
    public void createSettingWithoutRequiredFields(){
        CreateSettingDto request = new CreateSettingDto();
        List<FieldErrorsMessageDTO> response = webTestClient.post()
                .uri("/setting/create")
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();
        assertNotNull(response);
    }

    @Test
    public void createExistingSetting() throws AlreadyExistsException {
        Setting setting = createSetting();
        CreateSettingDto request = getCreateSettingDto();
        request.setCode(setting.getCode());
        ErrorsListDTO response = webTestClient.post()
                .uri("/setting/create")
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorsListDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);

    }

    @Test
    public void deleteSettingSuccessfully() throws AlreadyExistsException {
        Setting setting = createSetting();
        MessageDTO response = webTestClient.delete()
                .uri("/setting/delete/" + setting.getId())
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
    public void deleteNonExistingSetting(){
        ErrorMessage response = webTestClient.delete()
                .uri("/setting/delete/1234")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível encontrar uma configuração com o id: 1234", response.getMessage());
    }

    @Test
    public void editSettingSuccessfully() throws AlreadyExistsException {
        EditSettingDto request = new EditSettingDto(createSetting());
        request.setCode("new code");
        request.setValue("new value");
        ResponseSettingDto response = webTestClient.put()
                .uri("/setting/edit")
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
    public void editNonExistingSetting(){
        EditSettingDto request = new EditSettingDto();
        request.setId(1234L);
        request.setCode("code");
        request.setValue("value");
        ErrorMessage response = webTestClient.put()
                .uri("/setting/edit")
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals("Não foi possível encontrar uma configuração com o id: 1234", response.getMessage());
    }

    @Test
    public void editSettingWithoutRequiredFields(){
        EditSettingDto request = new EditSettingDto();
        List<FieldErrorsMessageDTO> response = webTestClient.put()
                .uri("/setting/edit")
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBodyList(FieldErrorsMessageDTO.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
    }

}
