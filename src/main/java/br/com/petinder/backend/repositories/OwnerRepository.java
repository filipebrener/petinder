package br.com.petinder.backend.repositories;

import br.com.petinder.backend.domains.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    long countByCpf(String cpf);
    long countByEmail(String email);
    long countByCelNumber(String celNumber);
    Owner findByUuid(String uuid);
}
