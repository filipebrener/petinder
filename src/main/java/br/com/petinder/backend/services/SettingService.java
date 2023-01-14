package br.com.petinder.backend.services;

import br.com.petinder.backend.domains.Setting;
import br.com.petinder.backend.dtos.setting.CreateSettingDto;
import br.com.petinder.backend.dtos.setting.EditSettingDto;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SettingService {
    @Autowired
    SettingRepository settingRepository;

    public String getSettingValueByCode(String code, String defaultValue){
        Optional<Setting> optionalSetting = settingRepository.findByCode(code);
        if(optionalSetting.isPresent()){
            return optionalSetting.get().getValue();
        } else {
            return defaultValue;
        }
    }

    public String getDateFormatSettingValue(String defaultValue){
        return getSettingValueByCode("date.format", defaultValue);
    }

    public Setting create(CreateSettingDto request) throws AlreadyExistsException {
        List<String> errors = getSettingPersistenseValidationErrors(request);
        boolean hasErrors = !errors.isEmpty();
        if(hasErrors) throw new AlreadyExistsException("Não foi possível criar a setting [" + request.getCode() + "]", errors);
        Setting newSetting = new Setting(request);
        return settingRepository.save(newSetting);
    }

    private List<String> getSettingPersistenseValidationErrors(CreateSettingDto request){
        Optional<Setting> existingSetting = settingRepository.findByCode(request.getCode());
        List<String> errors = new ArrayList<>();
        if(existingSetting.isPresent()) errors.add("A setting com o código [" + request.getCode() + "] já existe");
        return errors;
    }

    public Setting findById(Long id) throws NotFoundException {
        Optional<Setting> existingSetting = settingRepository.findById(id);
        if(existingSetting.isEmpty()) throw new NotFoundException("Não foi possível encontrar uma configuração com o id: " + id);
        return existingSetting.get();
    }

    public void delete(Long id) throws NotFoundException {
        Setting existingSetting = findById(id);
        settingRepository.delete(existingSetting);
    }

    public Setting edit(EditSettingDto settingWithNewValues) throws NotFoundException {
        Setting setting = findById(settingWithNewValues.getId());
        setting.setCode(settingWithNewValues.getCode());
        setting.setValue(settingWithNewValues.getValue());
        return settingRepository.save(setting);
    }

    public List<Setting> findAll() {
        return settingRepository.findAll();
    }
}
