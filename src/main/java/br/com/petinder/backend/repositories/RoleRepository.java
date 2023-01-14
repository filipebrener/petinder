package br.com.petinder.backend.repositories;


import br.com.petinder.backend.domains.Role;
import br.com.petinder.backend.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName role);
}
