package br.com.petinder.backend.domains;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseDomain implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) @Column(name="id")
    private long id;

    @Column(name="uuid", length=64, unique=true, nullable=false, updatable=false)
    private String uuid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", nullable=false, updatable=false)
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }

    public long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
