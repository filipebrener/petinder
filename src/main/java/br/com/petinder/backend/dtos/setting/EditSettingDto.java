package br.com.petinder.backend.dtos.setting;

import br.com.petinder.backend.domains.Setting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditSettingDto {

    @NotNull(message = "É obrigatório informar o ID")
    private Long id;

    @NotBlank(message = "O código é obrigatório")
    private String code;

    @NotBlank(message = "O valor é obrigatório")
    private String value;

    public EditSettingDto(Long id, String code, String value) {
        this.id = id;
        this.code = code;
        this.value = value;
    }

    public EditSettingDto() {
    }

    public EditSettingDto(Setting setting) {
        this.id = setting.getId();
        this.value = setting.getValue();
        this.code = setting.getCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
