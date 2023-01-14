package br.com.petinder.backend.controllers;

import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.dtos.response.MessageDTO;
import br.com.petinder.backend.dtos.owner.CreateOwnerDTO;
import br.com.petinder.backend.dtos.owner.EditOwnerDTO;
import br.com.petinder.backend.dtos.owner.ResponseOwnerDTO;
import br.com.petinder.backend.enums.RoleName;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.CustomForbiddenRequestException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.services.OwnerService;
import br.com.petinder.backend.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;

    private final RoleService roleService;

    public OwnerController(OwnerService ownerService, RoleService roleService) {
        this.ownerService = ownerService;
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseOwnerDTO> create(@RequestBody @Valid CreateOwnerDTO requestDTO) throws AlreadyExistsException {
        Owner persistedOwner = ownerService.createWithUserRole(requestDTO);
        return new ResponseEntity<>(new ResponseOwnerDTO(persistedOwner), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseOwnerDTO> get(@PathVariable("id") Long id) throws NotFoundException {
        Owner owner = ownerService.findById(id);
        return new ResponseEntity<>(new ResponseOwnerDTO(owner), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable("id") Long id, @AuthenticationPrincipal Owner loggedOwner) throws NotFoundException, CustomForbiddenRequestException {
        boolean isAdminUser = roleService.userHaveRole(loggedOwner, RoleName.ROLE_ADMIN);
        boolean havePermission = Objects.equals(id, loggedOwner.getId()) || isAdminUser;
        if(!havePermission) throw new CustomForbiddenRequestException("O usuário logado não tem permissão para apagar esse usuário!");
        ownerService.delete(id);
        return new ResponseEntity<>(new MessageDTO("Usuário apagado com sucesso!"), HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<ResponseOwnerDTO>> listAll() {
        List<Owner> result = ownerService.findAll();
        return new ResponseEntity<>(result.stream().map(ResponseOwnerDTO::new).toList(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseOwnerDTO> edit(@RequestBody @Valid EditOwnerDTO dto, @AuthenticationPrincipal Owner loggedOwner) throws NotFoundException, CustomForbiddenRequestException {
        boolean isAdminUser = roleService.userHaveRole(loggedOwner, RoleName.ROLE_ADMIN);
        boolean havePermission = Objects.equals(dto.getId(), loggedOwner.getId()) || isAdminUser;
        if(!havePermission) throw new CustomForbiddenRequestException("O usuário logado não tem permissão para editar esse usuário!");
        Owner editedOwner = ownerService.edit(dto);
        return new ResponseEntity<>(new ResponseOwnerDTO(editedOwner), HttpStatus.OK);
    }

}