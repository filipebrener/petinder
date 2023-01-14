package br.com.petinder.backend.controllers;

import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.domains.Pet;
import br.com.petinder.backend.dtos.response.MessageDTO;
import br.com.petinder.backend.dtos.pet.CreatePetDTO;
import br.com.petinder.backend.dtos.pet.EditPetDTO;
import br.com.petinder.backend.dtos.pet.ResponsePetDTO;
import br.com.petinder.backend.enums.RoleName;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.CustomForbiddenRequestException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.PetRepository;
import br.com.petinder.backend.services.PetService;
import br.com.petinder.backend.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    private final RoleService roleService;

    private final PetRepository petRepository;

    public PetController(PetService petService, RoleService roleService, PetRepository petRepository) {
        this.petService = petService;
        this.roleService = roleService;
        this.petRepository = petRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponsePetDTO> create(@RequestBody @Valid CreatePetDTO requestDTO) throws AlreadyExistsException, NotFoundException {
        Pet persistedPet = petService.create(requestDTO);
        return new ResponseEntity<>(new ResponsePetDTO(persistedPet), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponsePetDTO> get(@PathVariable("id") Long id) throws NotFoundException {
        Pet pet = petService.findById(id);
        return new ResponseEntity<>(new ResponsePetDTO(pet), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable("id") Long id, @AuthenticationPrincipal Owner loggedOwner) throws NotFoundException, CustomForbiddenRequestException {
        Pet petToDelete = petService.findById(id);
        boolean isAdminUser = roleService.userHaveRole(loggedOwner, RoleName.ROLE_ADMIN);
        boolean havePermission = Objects.equals(petToDelete.getOwner().getId(), loggedOwner.getId()) || isAdminUser;
        if(!havePermission) throw new CustomForbiddenRequestException("O usuário logado não tem permissão para deletar esse pet!");
        petRepository.delete(petToDelete);
        return new ResponseEntity<>(new MessageDTO("Pet apagado com sucesso!"), HttpStatus.OK);
    }

    // TODO: implementar paginação, filtros, ordenação e tudo que há de bom...
    @GetMapping("/listAll")
    public ResponseEntity<List<ResponsePetDTO>> listAll() {
        List<Pet> result = petService.findAll();
        return new ResponseEntity<>(result.stream().map(ResponsePetDTO::new).toList(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponsePetDTO> edit(@RequestBody @Valid EditPetDTO dto, @AuthenticationPrincipal Owner loggedOwner) throws NotFoundException, CustomForbiddenRequestException {
        Pet petToEdit = petService.findById(dto.getId());
        boolean isAdminUser = roleService.userHaveRole(loggedOwner, RoleName.ROLE_ADMIN);
        boolean havePermission = Objects.equals(petToEdit.getOwner().getId(), loggedOwner.getId()) || isAdminUser;
        if(!havePermission) throw new CustomForbiddenRequestException("O usuário logado não tem permissão para editar esse pet!");
        Pet editedPet = petService.edit(dto, petToEdit);
        return new ResponseEntity<>(new ResponsePetDTO(editedPet), HttpStatus.OK);
    }

}
