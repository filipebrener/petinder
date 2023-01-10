package br.com.petinder.backend.dtos.setting;

import br.com.petinder.backend.domains.Setting;

public class ResponseSettingDto {

    private long id;
    private String value;

    private String code;

    public ResponseSettingDto(long id, String value, String code) {
        this.id = id;
        this.value = value;
        this.code = code;
    }

    public ResponseSettingDto() {
    }

    public ResponseSettingDto(Setting persistedSetting) {
        this.code = persistedSetting.getCode();
        this.value = persistedSetting.getValue();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
