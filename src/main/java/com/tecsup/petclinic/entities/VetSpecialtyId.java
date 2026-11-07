package com.tecsup.petclinic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VetSpecialtyId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "vet_id")
    private Integer vetId;

    @Column(name = "specialty_id")
    private Integer specialtyId;
}
