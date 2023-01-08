package br.com.petinder.backend.repositories;

import br.com.petinder.backend.domains.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
