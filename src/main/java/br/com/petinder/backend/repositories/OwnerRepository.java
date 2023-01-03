package br.com.petinder.backend.repositories;

import br.com.petinder.backend.domains.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    public long countByCpf(String cpf);
    public long countByEmail(String email);
    public Owner findByUuid(String uuid);

}
