package br.com.petinder.backend.repositories;

import br.com.petinder.backend.domains.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByCode(String code);
}
