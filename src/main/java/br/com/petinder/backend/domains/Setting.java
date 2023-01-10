package br.com.petinder.backend.domains;

import br.com.petinder.backend.dtos.setting.CreateSettingDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Setting extends BaseDomain {

    @NotBlank
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotBlank
    @Column(name = "s_value", nullable = false)
    private String value;

    public Setting(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public Setting(CreateSettingDto request) {
        this.code = request.getCode();
        this.value = request.getValue();
    }
    public Setting() {
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

