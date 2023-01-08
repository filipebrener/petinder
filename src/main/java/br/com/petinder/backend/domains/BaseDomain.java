package br.com.petinder.backend.domains;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseDomain implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) @Column(name="id")
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", nullable=false, updatable=false)
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }

    public long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
