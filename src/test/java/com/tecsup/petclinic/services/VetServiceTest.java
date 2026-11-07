package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    /**
     * Test buscar veterinario por ID existente
     */
    @Test
    public void testFindVetById() {
        Integer ID = 1;
        String EXPECTED_FIRST_NAME = "James";
        String EXPECTED_LAST_NAME = "Carter";

        VetDTO vet = null;
        try {
            vet = this.vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(vet);
        assertEquals(EXPECTED_FIRST_NAME, vet.getFirstName());
        assertEquals(EXPECTED_LAST_NAME, vet.getLastName());
        log.info("VET FOUND: {}", vet);
    }

    /**
     * Test buscar veterinario por ID inexistente
     */
    @Test
    public void testFindVetById_NotFound() {
        Integer ID = 9999;
        assertThrows(VetNotFoundException.class, () -> {
            this.vetService.findById(ID);
        });
    }

    /**
     * Test buscar veterinarios por apellido
     */
    @Test
    public void testFindVetByLastName() {
        String LAST_NAME = "Douglas";
        List<VetDTO> vets = this.vetService.findByLastName(LAST_NAME);

        assertNotNull(vets);
        assertFalse(vets.isEmpty());
        assertEquals("Linda", vets.get(0).getFirstName());
        log.info("VETS WITH LAST NAME {}: {}", LAST_NAME, vets);
    }

    /**
     * Test crear un nuevo veterinario
     */
    @Test
    public void testCreateVet() {
        VetDTO newVet = VetDTO.builder()
                .firstName("Carlos")
                .lastName("Ramirez")
                .email("carlos.ramirez@petclinic.com")
                .phone("999888777")
                .active(true)
                .build();

        VetDTO createdVet = this.vetService.create(newVet);

        log.info("VET CREATED: {}", createdVet);
        assertNotNull(createdVet.getId());
        assertEquals("Carlos", createdVet.getFirstName());
        assertEquals("Ramirez", createdVet.getLastName());
        assertEquals("carlos.ramirez@petclinic.com", createdVet.getEmail());
        assertTrue(createdVet.getActive());
    }

    /**
     * Test actualizar un veterinario
     */
    @Test
    public void testUpdateVet() {
        VetDTO newVet = VetDTO.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .email("ana.gomez@petclinic.com")
                .phone("987654321")
                .active(true)
                .build();

        VetDTO createdVet = this.vetService.create(newVet);

        // Actualizamos datos
        createdVet.setFirstName("Ana Maria");
        createdVet.setLastName("Gomez Prado");
        createdVet.setPhone("912345678");

        VetDTO updatedVet = this.vetService.update(createdVet);

        log.info("VET UPDATED: {}", updatedVet);
        assertEquals("Ana Maria", updatedVet.getFirstName());
        assertEquals("Gomez Prado", updatedVet.getLastName());
        assertEquals("912345678", updatedVet.getPhone());
    }

    /**
     * Test eliminar un veterinario
     */
    @Test
    public void testDeleteVet() {
        VetDTO newVet = VetDTO.builder()
                .firstName("Marcos")
                .lastName("Paredes")
                .email("marcos.paredes@petclinic.com")
                .phone("955443322")
                .active(true)
                .build();

        VetDTO createdVet = this.vetService.create(newVet);
        Integer id = createdVet.getId();

        try {
            this.vetService.delete(id);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VetNotFoundException.class, () -> {
            this.vetService.findById(id);
        });
        log.info("VET DELETED SUCCESSFULLY WITH ID: {}", id);
    }
}
