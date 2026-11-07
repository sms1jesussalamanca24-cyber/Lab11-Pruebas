package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    /**
     * Test buscar especialidad de un veterinario por ID compuesto
     */
    @Test
    public void testFindById() {
        Integer VET_ID = 2;
        Integer SPECIALTY_ID = 1;

        VetSpecialtyDTO dto = null;
        try {
            dto = this.vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(dto);
        assertEquals(VET_ID, dto.getVetId());
        assertEquals(SPECIALTY_ID, dto.getSpecialtyId());
        assertEquals(8, dto.getYearsExperience());
        assertTrue(dto.getIsPrimary());
        log.info("VET SPECIALTY FOUND: {}", dto);
    }

    /**
     * Test buscar especialidades de un veterinario
     */
    @Test
    public void testFindSpecialtiesByVet() {
        Integer VET_ID = 3;
        List<VetSpecialtyDTO> specialties = this.vetSpecialtyService.findSpecialtiesByVet(VET_ID);

        assertNotNull(specialties);
        assertEquals(2, specialties.size());
        log.info("SPECIALTIES FOR VET ID {}: {}", VET_ID, specialties);
    }

    /**
     * Test buscar veterinarios por especialidad
     */
    @Test
    public void testFindVetsBySpecialty() {
        Integer SPECIALTY_ID = 2; // surgery (assigned to vet 3 and vet 4)
        List<VetSpecialtyDTO> vets = this.vetSpecialtyService.findVetsBySpecialty(SPECIALTY_ID);

        assertNotNull(vets);
        assertEquals(2, vets.size());
        log.info("VETS WITH SPECIALTY ID {}: {}", SPECIALTY_ID, vets);
    }

    /**
     * Test asignar una nueva especialidad a un veterinario
     */
    @Test
    public void testAssignSpecialtyToVet() {
        VetSpecialtyDTO newAssignment = VetSpecialtyDTO.builder()
                .vetId(1)
                .specialtyId(2)
                .certificationDate(LocalDate.of(2023, 1, 15))
                .yearsExperience(3)
                .isPrimary(true)
                .notes("Certified general surgeon")
                .build();

        VetSpecialtyDTO created = this.vetSpecialtyService.assignSpecialtyToVet(newAssignment);

        log.info("ASSIGNMENT CREATED: {}", created);
        assertNotNull(created);
        assertEquals(1, created.getVetId());
        assertEquals(2, created.getSpecialtyId());
        assertEquals(3, created.getYearsExperience());
        assertTrue(created.getIsPrimary());
        assertEquals("Certified general surgeon", created.getNotes());
    }

    /**
     * Test eliminar una relación de especialidad de un veterinario
     */
    @Test
    public void testRemoveSpecialtyFromVet() {
        // Asignamos una relación para luego eliminarla
        VetSpecialtyDTO tempAssignment = VetSpecialtyDTO.builder()
                .vetId(1)
                .specialtyId(3)
                .certificationDate(LocalDate.of(2022, 5, 10))
                .yearsExperience(2)
                .isPrimary(false)
                .notes("Temporary dental assignment")
                .build();

        this.vetSpecialtyService.assignSpecialtyToVet(tempAssignment);

        // Eliminamos la relación
        try {
            this.vetSpecialtyService.removeSpecialtyFromVet(1, 3);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        // Verificamos que ya no exista
        assertThrows(VetSpecialtyNotFoundException.class, () -> {
            this.vetSpecialtyService.findById(1, 3);
        });
        log.info("VET SPECIALTY SUCCESSFULLY REMOVED FOR VET 1 AND SPECIALTY 3");
    }

    /**
     * Test eliminar relación inexistente debe lanzar excepción
     */
    @Test
    public void testRemoveSpecialtyFromVet_NotFound() {
        assertThrows(VetSpecialtyNotFoundException.class, () -> {
            this.vetSpecialtyService.removeSpecialtyFromVet(99, 99);
        });
    }
}
