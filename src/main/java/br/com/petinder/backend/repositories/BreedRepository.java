package br.com.petinder.backend.repositories;

import br.com.petinder.backend.domains.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
    int countByNameIgnoreCase(String name);
}
