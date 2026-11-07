package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    /**
     * Test buscar visita por ID existente
     */
    @Test
    public void testFindVisitById() {
        Integer ID = 1;
        String EXPECTED_DESC = "rabies shot";

        VisitDTO visit = null;
        try {
            visit = this.visitService.findById(ID);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(visit);
        assertEquals(EXPECTED_DESC, visit.getDescription());
        assertEquals(7, visit.getPetId());
        assertEquals(2, visit.getVetId());
        log.info("VISIT FOUND: {}", visit);
    }

    /**
     * Test buscar visita por ID inexistente
     */
    @Test
    public void testFindVisitById_NotFound() {
        Integer ID = 9999;
        assertThrows(VisitNotFoundException.class, () -> {
            this.visitService.findById(ID);
        });
    }

    /**
     * Test buscar visitas por petId
     */
    @Test
    public void testFindVisitByPetId() {
        Integer PET_ID = 8;
        List<VisitDTO> visits = this.visitService.findByPetId(PET_ID);

        assertNotNull(visits);
        assertEquals(2, visits.size());
        log.info("VISITS FOR PET ID {}: {}", PET_ID, visits);
    }

    /**
     * Test buscar visitas por vetId
     */
    @Test
    public void testFindVisitByVetId() {
        Integer VET_ID = 2;
        List<VisitDTO> visits = this.visitService.findByVetId(VET_ID);

        assertNotNull(visits);
        assertEquals(2, visits.size());
        log.info("VISITS FOR VET ID {}: {}", VET_ID, visits);
    }

    /**
     * Test crear una nueva visita
     */
    @Test
    public void testCreateVisit() {
        VisitDTO newVisit = VisitDTO.builder()
                .petId(1)
                .vetId(1)
                .visitDate(LocalDate.of(2026, 5, 20))
                .description("vaccination checkup")
                .cost(new BigDecimal("85.50"))
                .build();

        VisitDTO createdVisit = this.visitService.create(newVisit);

        log.info("VISIT CREATED: {}", createdVisit);
        assertNotNull(createdVisit.getId());
        assertEquals(1, createdVisit.getPetId());
        assertEquals(1, createdVisit.getVetId());
        assertEquals("vaccination checkup", createdVisit.getDescription());
        assertEquals(new BigDecimal("85.50"), createdVisit.getCost());
    }

    /**
     * Test actualizar una visita
     */
    @Test
    public void testUpdateVisit() {
        VisitDTO newVisit = VisitDTO.builder()
                .petId(2)
                .vetId(2)
                .visitDate(LocalDate.of(2026, 6, 10))
                .description("ear infection")
                .cost(new BigDecimal("50.00"))
                .build();

        VisitDTO createdVisit = this.visitService.create(newVisit);

        // Actualizamos datos
        createdVisit.setDescription("ear infection treated");
        createdVisit.setCost(new BigDecimal("75.00"));

        VisitDTO updatedVisit = this.visitService.update(createdVisit);

        log.info("VISIT UPDATED: {}", updatedVisit);
        assertEquals("ear infection treated", updatedVisit.getDescription());
        assertEquals(new BigDecimal("75.00"), updatedVisit.getCost());
    }

    /**
     * Test eliminar una visita
     */
    @Test
    public void testDeleteVisit() {
        VisitDTO newVisit = VisitDTO.builder()
                .petId(3)
                .vetId(3)
                .visitDate(LocalDate.of(2026, 7, 15))
                .description("minor surgery")
                .cost(new BigDecimal("120.00"))
                .build();

        VisitDTO createdVisit = this.visitService.create(newVisit);
        Integer id = createdVisit.getId();

        try {
            this.visitService.delete(id);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VisitNotFoundException.class, () -> {
            this.visitService.findById(id);
        });
        log.info("VISIT DELETED SUCCESSFULLY WITH ID: {}", id);
    }
}
