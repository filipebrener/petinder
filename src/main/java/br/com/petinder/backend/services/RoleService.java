package br.com.petinder.backend.services;

import br.com.petinder.backend.domains.Owner;
import br.com.petinder.backend.domains.Role;
import br.com.petinder.backend.enums.RoleName;
import br.com.petinder.backend.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {
    final private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean userHaveRole(Owner owner, RoleName roleName){
        Set<Role> userRoles = (Set<Role>) owner.getAuthorities();
        return userRoles
                .stream()
                .map(Role::getAuthority)
                .toList()
                .contains(roleName.toString());
    }

    public Role getRole(RoleName role){
        return roleRepository.findByName(role).orElseGet(() ->{
            Role newRole = new Role(role);
            return roleRepository.save(newRole);
        });
    }



}
