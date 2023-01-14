package br.com.petinder.backend.repositories;

import br.com.petinder.backend.domains.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Long countByCpf(String cpf);
    Long countByEmail(String email);
    Long countByCelNumber(String celNumber);
    Optional<Owner> findByUsername(String username);

    // TODO: trocar as validações que usam count por exists

}
