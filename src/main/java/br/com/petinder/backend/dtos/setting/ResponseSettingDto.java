package br.com.petinder.backend.dtos.setting;

import br.com.petinder.backend.domains.Setting;

public class ResponseSettingDto {

    private Long id;
    private String value;

    private String code;

    public ResponseSettingDto(Long id, String value, String code) {
        this.id = id;
        this.value = value;
        this.code = code;
    }

    public ResponseSettingDto() {
    }

    public ResponseSettingDto(Setting persistedSetting) {
        this.id = persistedSetting.getId();
        this.code = persistedSetting.getCode();
        this.value = persistedSetting.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
