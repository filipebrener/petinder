package br.com.petinder.backend.repositories;

import br.com.petinder.backend.domains.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Pet findByUuid(String uuid);
}
