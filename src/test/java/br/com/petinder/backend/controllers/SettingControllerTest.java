package br.com.petinder.backend.controllers;

import br.com.petinder.backend.BaseTest;
import br.com.petinder.backend.domains.Setting;
import br.com.petinder.backend.dtos.setting.CreateSettingDto;
import br.com.petinder.backend.dtos.setting.ResponseSettingDto;
import br.com.petinder.backend.repositories.SettingRepository;
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
public class SettingControllerTest extends BaseTest {

    @Autowired
    private SettingRepository settingRepository;

    @BeforeEach
    public void beforeEach(){
        settingRepository.deleteAll();
    }

    @Test
    public void createSettingSuccessfully(){
        CreateSettingDto request = new CreateSettingDto();
        request.setCode("setting code");
        request.setValue("setting value");
        ResponseSettingDto responseBody = webTestClient.post()
                .uri("/setting/create")
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(ResponseSettingDto.class)
                .returnResult().getResponseBody();

        // TODO: id tá retornando zero, investigar
        assertNotNull(responseBody);

        Optional<Setting> optionalSetting = settingRepository.findById(responseBody.getId());
        assertTrue(optionalSetting.isPresent());
        Setting persistedSetting = optionalSetting.get();
        assertEquals(request.getCode(),persistedSetting.getCode());
        assertEquals(request.getCode(), responseBody.getCode());
        assertEquals(request.getValue(),persistedSetting.getValue());
        assertEquals(request.getValue(), responseBody.getCode());
    }

}
