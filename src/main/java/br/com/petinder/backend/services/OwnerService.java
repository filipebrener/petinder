package br.com.petinder.backend.services;

import br.com.petinder.backend.domains.Address;
import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.dtos.address.EditAddressDTO;
import br.com.petinder.backend.dtos.owner.CreateOwnerDTO;
import br.com.petinder.backend.dtos.owner.EditOwnerDTO;
import br.com.petinder.backend.exceptions.AlreadyExistsException;
import br.com.petinder.backend.exceptions.NotFoundException;
import br.com.petinder.backend.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    public Owner create(CreateOwnerDTO dto) throws AlreadyExistsException {
        List<String> errors = getOwnerPersistenseValidationErrors(dto);
        boolean hasErrors = !errors.isEmpty();
        if (hasErrors) throw new AlreadyExistsException("Não foi possível criar o usuário!", errors);
        boolean hasAddress = dto.getAddress() != null;
        return (!hasAddress ? createWithoutAddress(dto) : createWithAddress(dto));
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

    private Owner createWithoutAddress(CreateOwnerDTO dto){
        Owner newOwner = new Owner(dto.getName(), dto.getCpf(), dto.getCelNumber(), dto.getEmail());
        return ownerRepository.save(newOwner);
    }

    private Owner createWithAddress(CreateOwnerDTO dto){
        Address newAddress = new Address(dto.getAddress());
        Owner newOwner = new Owner(dto.getName(), dto.getCpf(), dto.getCelNumber(), dto.getEmail(), newAddress);
        return ownerRepository.save(newOwner);
    }

    public void delete(long id) throws NotFoundException {
        Owner owner = findById(id);
        ownerRepository.delete(owner);
    }

    public Owner findById(long id) throws NotFoundException {
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
        EditAddressDTO newAddressDto = dto.getAddress();
        if(newAddressDto != null) owner.setAddress(new Address(newAddressDto));
        return ownerRepository.save(owner);
    }
}
