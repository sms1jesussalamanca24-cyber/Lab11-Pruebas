package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
public class TypeServiceTest {

    @Autowired
    private PetTypeService petTypeService;

    /**
     * Test buscar tipo por ID existente
     */
    @Test
    public void testFindTypeById() {
        Integer ID = 1;
        String EXPECTED_NAME = "cat";

        PetTypeDTO petType = null;
        try {
            petType = this.petTypeService.findById(ID);
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(petType);
        assertEquals(EXPECTED_NAME, petType.getName());
        assertEquals("Domestic feline", petType.getDescription());
        assertTrue(petType.getActive());
        log.info("TYPE FOUND: {}", petType);
    }

    /**
     * Test buscar tipo por ID inexistente
     */
    @Test
    public void testFindTypeById_NotFound() {
        Integer ID = 9999;
        assertThrows(PetTypeNotFoundException.class, () -> {
            this.petTypeService.findById(ID);
        });
    }

    /**
     * Test buscar tipos por nombre
     */
    @Test
    public void testFindTypeByName() {
        String NAME = "dog";
        List<PetTypeDTO> types = this.petTypeService.findByName(NAME);

        assertNotNull(types);
        assertFalse(types.isEmpty());
        assertEquals("Domestic canine", types.get(0).getDescription());
        log.info("TYPE FOUND BY NAME {}: {}", NAME, types);
    }

    /**
     * Test buscar tipos activos
     */
    @Test
    public void testFindTypeByActive() {
        List<PetTypeDTO> activeTypes = this.petTypeService.findByActive(true);

        assertNotNull(activeTypes);
        assertTrue(activeTypes.size() >= 7);
        log.info("ACTIVE TYPES COUNT: {}", activeTypes.size());
    }

    /**
     * Test crear un nuevo tipo
     */
    @Test
    public void testCreateType() {
        PetTypeDTO newType = PetTypeDTO.builder()
                .name("parrot")
                .description("Tropical talking bird")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(50)
                .careLevel("high")
                .build();

        PetTypeDTO createdType = this.petTypeService.create(newType);

        log.info("TYPE CREATED: {}", createdType);
        assertNotNull(createdType.getId());
        assertEquals("parrot", createdType.getName());
        assertEquals("Tropical talking bird", createdType.getDescription());
        assertEquals(50, createdType.getAverageLifespan());
    }

    /**
     * Test actualizar un tipo
     */
    @Test
    public void testUpdateType() {
        PetTypeDTO newType = PetTypeDTO.builder()
                .name("ferret")
                .description("Domesticated polecat")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(8)
                .careLevel("medium")
                .build();

        PetTypeDTO createdType = this.petTypeService.create(newType);

        // Actualizamos datos
        createdType.setName("ferret upgraded");
        createdType.setDescription("Friendly domestic ferret");
        createdType.setAverageLifespan(10);

        PetTypeDTO updatedType = this.petTypeService.update(createdType);

        log.info("TYPE UPDATED: {}", updatedType);
        assertEquals("ferret upgraded", updatedType.getName());
        assertEquals("Friendly domestic ferret", updatedType.getDescription());
        assertEquals(10, updatedType.getAverageLifespan());
    }

    /**
     * Test eliminar un tipo
     */
    @Test
    public void testDeleteType() {
        PetTypeDTO newType = PetTypeDTO.builder()
                .name("iguana")
                .description("Large arboreal lizard")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(15)
                .careLevel("high")
                .build();

        PetTypeDTO createdType = this.petTypeService.create(newType);
        Integer id = createdType.getId();

        try {
            this.petTypeService.delete(id);
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(PetTypeNotFoundException.class, () -> {
            this.petTypeService.findById(id);
        });
        log.info("TYPE DELETED SUCCESSFULLY WITH ID: {}", id);
    }
}
