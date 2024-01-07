package com.masaGreen.presta.models.entities;

import com.masaGreen.presta.models.superClasess.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    private String name;

    @Override
    public String toString() {
        return this.name;
    }


}
