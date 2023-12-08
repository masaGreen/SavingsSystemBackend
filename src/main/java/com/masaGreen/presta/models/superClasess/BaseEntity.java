package com.masaGreen.presta.models.superClasess;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseEntity {
    private String id;

    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    private void generateId(){
        if(id == null){
            id = UUID.randomUUID().toString();
        }
    }
}
