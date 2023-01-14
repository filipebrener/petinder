package br.com.petinder.backend.domains;

import br.com.petinder.backend.enums.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role extends BaseDomain implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", unique = true, nullable = false)
    private RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return this.name.toString();
    }

    public RoleName getRole() {
        return name;
    }

    public void setRole(RoleName name) {
        this.name = name;
    }
}
