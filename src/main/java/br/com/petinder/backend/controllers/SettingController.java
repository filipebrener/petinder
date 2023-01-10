package br.com.petinder.backend.controllers;

import br.com.petinder.backend.domains.Setting;
import br.com.petinder.backend.dtos.response.MessageDTO;
import br.com.petinder.backend.dtos.setting.CreateSettingDto;
import br.com.petinder.backend.dtos.setting.EditSettingDto;
import br.com.petinder.backend.dtos.setting.ResponseSettingDto;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.services.SettingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @PostMapping("/create")
    public ResponseEntity<ResponseSettingDto> create(@RequestBody @Valid CreateSettingDto request) throws AlreadyExistsException {
        Setting persistedSetting = settingService.create(request);
        ResponseSettingDto response = new ResponseSettingDto(persistedSetting);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable(name = "id") long id) throws NotFoundException {
        settingService.delete(id);
        return new ResponseEntity<>(new MessageDTO("Setting apagada com sucesso!"), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseSettingDto> edit(@RequestBody @Valid EditSettingDto request) throws NotFoundException {
        Setting editedSetting = settingService.edit(request);
        ResponseSettingDto response = new ResponseSettingDto(editedSetting);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseSettingDto> get(@PathVariable(name = "id") long id) throws NotFoundException {
        Setting setting = settingService.findById(id);
        ResponseSettingDto response = new ResponseSettingDto(setting);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<ResponseSettingDto>> listAll(){
        List<Setting> settings = settingService.findAll();
        return new ResponseEntity<>(settings.stream().map(ResponseSettingDto::new).toList(), HttpStatus.OK);
    }
 }
