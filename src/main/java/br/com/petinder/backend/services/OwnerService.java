package br.com.petinder.backend.services;

import br.com.petinder.backend.domains.Address;
import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.domains.Role;
import br.com.petinder.backend.dtos.address.EditAddressDTO;
import br.com.petinder.backend.dtos.owner.CreateOwnerDTO;
import br.com.petinder.backend.dtos.owner.EditOwnerDTO;
import br.com.petinder.backend.enums.RoleName;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.OwnerRepository;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final RoleService roleService;

    public OwnerService(OwnerRepository ownerRepository, RoleService roleService) {
        this.ownerRepository = ownerRepository;
        this.roleService = roleService;
    }

    public void validateForCreate(CreateOwnerDTO dto) throws AlreadyExistsException {
        List<String> errors = getOwnerPersistenseValidationErrors(dto);
        boolean hasErrors = !errors.isEmpty();
        if (hasErrors) throw new AlreadyExistsException("Não foi possível criar o usuário!", errors);
    }

    public Owner createWithAdminRole(CreateOwnerDTO dto) throws AlreadyExistsException {
        validateForCreate(dto);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRole(RoleName.ROLE_USER));
        roles.add(roleService.getRole(RoleName.ROLE_ADMIN));
        boolean hasAddress = dto.getAddress() != null;
        return (!hasAddress ? createWithoutAddress(dto, roles) : createWithAddress(dto, roles));
    }

    public Owner createWithUserRole(CreateOwnerDTO dto) throws AlreadyExistsException {
        validateForCreate(dto);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRole(RoleName.ROLE_USER));
        boolean hasAddress = dto.getAddress() != null;
        return (!hasAddress ? createWithoutAddress(dto, roles) : createWithAddress(dto, roles));
    }

    public List<String> getOwnerPersistenseValidationErrors(CreateOwnerDTO dto){
        List<String> errors = new ArrayList<>();
        boolean alreadyExistsWithEmail = ownerRepository.countByEmail(dto.getEmail()) > 0;
        boolean alreadyExistsWithCpf = ownerRepository.countByCpf(dto.getCpf()) > 0;
        boolean alreadyExistsWithCelNumber = ownerRepository.countByCelNumber(dto.getCelNumber()) > 0;
        if (alreadyExistsWithEmail) errors.add("E-mail informado já foi cadastrado!");
        if (alreadyExistsWithCpf) errors.add("CPF informado já foi cadastrado!");
        if (alreadyExistsWithCelNumber) errors.add("Número de celular informado já foi cadastrado!");
        return errors;
    }

    private Owner createWithoutAddress(CreateOwnerDTO dto, Set<Role> roles){
        Owner newOwner = new Owner(dto.getName(), dto.getBirthDate(), dto.getUsername(), dto.getCpf(), dto.getCelNumber(), dto.getEmail(), dto.getPassword(), roles);
        return ownerRepository.save(newOwner);
    }

    private Owner createWithAddress(CreateOwnerDTO dto, Set<Role> roles){
        Address newAddress = new Address(dto.getAddress());
        Owner newOwner = new Owner(dto.getName(), dto.getBirthDate(), dto.getUsername(), dto.getCpf(), dto.getCelNumber(), dto.getEmail(), dto.getPassword(), newAddress, roles);
        return ownerRepository.save(newOwner);
    }

    public void delete(Long id) throws NotFoundException {
        Owner owner = findById(id);
        ownerRepository.delete(owner);
    }

    public Owner findById(Long id) throws NotFoundException {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if(optionalOwner.isEmpty()) throw new NotFoundException("Não foi possível encontrar o usuário com id: " + id);
        return optionalOwner.get();
    }

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    public Owner edit(EditOwnerDTO dto) throws NotFoundException {
        Owner owner = findById(dto.getId());
        if(dto.getName() != null) owner.setName(dto.getName());
        if(dto.getCelNumber() != null) owner.setCelNumber(dto.getCelNumber());
        if(dto.getBirthDate() != null) owner.setBirthDate(dto.getBirthDate());
        EditAddressDTO newAddressDto = dto.getAddress();
        if(newAddressDto != null) owner.setAddress(new Address(newAddressDto));
        return ownerRepository.save(owner);
    }
}
