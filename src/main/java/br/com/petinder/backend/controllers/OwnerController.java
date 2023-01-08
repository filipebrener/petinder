package br.com.petinder.backend.controllers;

import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.dtos.MessageDTO;
import br.com.petinder.backend.dtos.owner.CreateOwnerDTO;
import br.com.petinder.backend.dtos.owner.EditOwnerDTO;
import br.com.petinder.backend.dtos.owner.ResponseOwnerDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.services.OwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @PostMapping("/create")
    public ResponseEntity<ResponseOwnerDTO> create(@RequestBody @Valid CreateOwnerDTO requestDTO) throws AlreadyExistsException {
        Owner persistedOwner = ownerService.create(requestDTO);
        ResponseOwnerDTO responseDTO = new ResponseOwnerDTO(persistedOwner);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseOwnerDTO> get(@PathVariable("id") long id) throws NotFoundException {
        Owner owner = ownerService.findById(id);
        return new ResponseEntity<>(new ResponseOwnerDTO(owner), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable("id") long id) throws NotFoundException {
        ownerService.delete(id);
        return new ResponseEntity<>(new MessageDTO("Usuário apagado com sucesso!"), HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<ResponseOwnerDTO>> listAll() {
        List<Owner> result = ownerService.findAll();
        return new ResponseEntity<>(result.stream().map(ResponseOwnerDTO::new).toList(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseOwnerDTO> edit(@RequestBody @Valid EditOwnerDTO dto) throws NotFoundException {
        Owner editedOwner = ownerService.edit(dto);
        return new ResponseEntity<>(new ResponseOwnerDTO(editedOwner), HttpStatus.OK);
    }
}