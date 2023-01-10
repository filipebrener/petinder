package br.com.petinder.backend.dtos.setting;

import jakarta.validation.constraints.NotBlank;

public class CreateSettingDto {

    @NotBlank(message = "O código é obrigatório")
    private String code;

    @NotBlank(message = "O valor é obrigatório")
    private String value;

    public CreateSettingDto(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public CreateSettingDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
