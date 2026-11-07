package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    /**
     * Test buscar especialidad por ID existente
     */
    @Test
    public void testFindSpecialtyById() {
        Integer ID = 1;
        String EXPECTED_NAME = "radiology";
        String EXPECTED_OFFICE = "Farewell";

        SpecialtyDTO specialty = null;
        try {
            specialty = this.specialtyService.findById(ID);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(specialty);
        assertEquals(EXPECTED_NAME, specialty.getName());
        assertEquals(EXPECTED_OFFICE, specialty.getOffice());
        log.info("SPECIALTY FOUND: {}", specialty);
    }

    /**
     * Test buscar especialidad por ID inexistente
     */
    @Test
    public void testFindSpecialtyById_NotFound() {
        Integer ID = 9999;
        assertThrows(SpecialtyNotFoundException.class, () -> {
            this.specialtyService.findById(ID);
        });
    }

    /**
     * Test buscar especialidad por nombre
     */
    @Test
    public void testFindSpecialtyByName() {
        String NAME = "surgery";
        List<SpecialtyDTO> specialties = this.specialtyService.findByName(NAME);

        assertNotNull(specialties);
        assertFalse(specialties.isEmpty());
        assertEquals("Maryland", specialties.get(0).getOffice());
        log.info("SPECIALTY FOUND BY NAME {}: {}", NAME, specialties);
    }

    /**
     * Test buscar especialidad por consultorio (office)
     */
    @Test
    public void testFindSpecialtyByOffice() {
        String OFFICE = "Terranova";
        List<SpecialtyDTO> specialties = this.specialtyService.findByOffice(OFFICE);

        assertNotNull(specialties);
        assertEquals(1, specialties.size());
        assertEquals("dentistry", specialties.get(0).getName());
        log.info("SPECIALTY IN OFFICE {}: {}", OFFICE, specialties);
    }

    /**
     * Test crear una nueva especialidad
     */
    @Test
    public void testCreateSpecialty() {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("dermatology")
                .office("Miraflores")
                .hOpen(9)
                .hClose(17)
                .build();

        SpecialtyDTO createdSpecialty = this.specialtyService.create(newSpecialty);

        log.info("SPECIALTY CREATED: {}", createdSpecialty);
        assertNotNull(createdSpecialty.getId());
        assertEquals("dermatology", createdSpecialty.getName());
        assertEquals("Miraflores", createdSpecialty.getOffice());
        assertEquals(9, createdSpecialty.getHOpen());
        assertEquals(17, createdSpecialty.getHClose());
    }

    /**
     * Test actualizar una especialidad
     */
    @Test
    public void testUpdateSpecialty() {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("cardiology")
                .office("San Isidro")
                .hOpen(8)
                .hClose(14)
                .build();

        SpecialtyDTO createdSpecialty = this.specialtyService.create(newSpecialty);

        // Actualizamos datos
        createdSpecialty.setName("cardiology advanced");
        createdSpecialty.setOffice("San Isidro - Piso 2");
        createdSpecialty.setHClose(16);

        SpecialtyDTO updatedSpecialty = this.specialtyService.update(createdSpecialty);

        log.info("SPECIALTY UPDATED: {}", updatedSpecialty);
        assertEquals("cardiology advanced", updatedSpecialty.getName());
        assertEquals("San Isidro - Piso 2", updatedSpecialty.getOffice());
        assertEquals(16, updatedSpecialty.getHClose());
    }

    /**
     * Test eliminar una especialidad
     */
    @Test
    public void testDeleteSpecialty() {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("oncology")
                .office("Surco")
                .hOpen(10)
                .hClose(18)
                .build();

        SpecialtyDTO createdSpecialty = this.specialtyService.create(newSpecialty);
        Integer id = createdSpecialty.getId();

        try {
            this.specialtyService.delete(id);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(SpecialtyNotFoundException.class, () -> {
            this.specialtyService.findById(id);
        });
        log.info("SPECIALTY DELETED SUCCESSFULLY WITH ID: {}", id);
    }
}
