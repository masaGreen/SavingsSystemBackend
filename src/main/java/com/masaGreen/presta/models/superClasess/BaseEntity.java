package com.masaGreen.presta.models.superClasess;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseEntity {
    @Id
    private String id;

    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    private void generate(){
        if(id == null){
            id = UUID.randomUUID().toString();
        }
        if(createdAt ==  null){
            createdAt = Instant.now();
        }
    }
}
