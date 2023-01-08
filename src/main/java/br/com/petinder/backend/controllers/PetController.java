package br.com.petinder.backend.controllers;

import br.com.petinder.backend.domains.Pet;
import br.com.petinder.backend.dtos.MessageDTO;
import br.com.petinder.backend.dtos.pet.CreatePetDTO;
import br.com.petinder.backend.dtos.pet.EditPetDTO;
import br.com.petinder.backend.dtos.pet.ResponsePetDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.services.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping("/create")
    public ResponseEntity<ResponsePetDTO> create(@RequestBody @Valid CreatePetDTO requestDTO) throws AlreadyExistsException, NotFoundException {
        Pet persistedPet = petService.create(requestDTO);
        ResponsePetDTO responseDTO = new ResponsePetDTO(persistedPet);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponsePetDTO> get(@PathVariable("id") long id) throws NotFoundException {
        Pet pet = petService.findById(id);
        return new ResponseEntity<>(new ResponsePetDTO(pet), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable("id") long id) throws NotFoundException {
        petService.delete(id);
        return new ResponseEntity<>(new MessageDTO("Usuário apagado com sucesso!"), HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<ResponsePetDTO>> listAll() {
        List<Pet> result = petService.findAll();
        return new ResponseEntity<>(result.stream().map(ResponsePetDTO::new).toList(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponsePetDTO> edit(@RequestBody @Valid EditPetDTO dto) throws NotFoundException {
        Pet editedPet = petService.edit(dto);
        return new ResponseEntity<>(new ResponsePetDTO(editedPet), HttpStatus.OK);
    }

}
