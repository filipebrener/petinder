package br.com.petinder.backend.repositories;

import br.com.petinder.backend.domains.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
    Breed findByUuid(String breedUuid);

    int countByNameIgnoreCase(String name);
}
