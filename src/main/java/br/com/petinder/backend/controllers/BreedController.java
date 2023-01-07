package br.com.petinder.backend.controllers;

import br.com.petinder.backend.domains.Breed;
import br.com.petinder.backend.dtos.MessageDTO;
import br.com.petinder.backend.dtos.breed.CreateBreedDTO;
import br.com.petinder.backend.dtos.breed.EditBreedDTO;
import br.com.petinder.backend.dtos.breed.ResponseBreedDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.services.BreedService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/breed")
public class BreedController {

    @Autowired
    BreedService breedService;

    @PostMapping("/create")
    public ResponseEntity<ResponseBreedDTO> create(@RequestBody @Valid CreateBreedDTO requestDTO) throws AlreadyExistsException, NotFoundException {
        Breed persistedBreed = breedService.create(requestDTO);
        ResponseBreedDTO responseDTO = new ResponseBreedDTO(persistedBreed);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<ResponseBreedDTO> get(@PathVariable("uuid") String uuid) throws NotFoundException {
        Breed breed = breedService.findByUuid(uuid);
        return new ResponseEntity<>(new ResponseBreedDTO(breed), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<MessageDTO> delete(@PathVariable("uuid") String uuid) throws NotFoundException {
        breedService.delete(uuid);
        return new ResponseEntity<>(new MessageDTO("Raça apagada com sucesso!"), HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<ResponseBreedDTO>> listAll() {
        List<Breed> result = breedService.findAll();
        return new ResponseEntity<>(result.stream().map(ResponseBreedDTO::new).toList(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseBreedDTO> edit(@RequestBody @Valid EditBreedDTO dto) throws NotFoundException {
        Breed editedPet = breedService.edit(dto);
        return new ResponseEntity<>(new ResponseBreedDTO(editedPet), HttpStatus.OK);
    }


}
