package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    /**
     * Test buscar dueño por ID existente
     */
    @Test
    public void testFindOwnerById() {
        Integer ID = 1;
        String EXPECTED_FIRST_NAME = "George";
        String EXPECTED_LAST_NAME = "Franklin";

        OwnerDTO owner = null;
        try {
            owner = this.ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(owner);
        assertEquals(EXPECTED_FIRST_NAME, owner.getFirstName());
        assertEquals(EXPECTED_LAST_NAME, owner.getLastName());
        log.info("OWNER FOUND: {}", owner);
    }

    /**
     * Test buscar dueño por ID inexistente
     */
    @Test
    public void testFindOwnerById_NotFound() {
        Integer ID = 9999;
        assertThrows(OwnerNotFoundException.class, () -> {
            this.ownerService.findById(ID);
        });
    }

    /**
     * Test buscar dueños por apellido
     */
    @Test
    public void testFindOwnerByLastName() {
        String LAST_NAME = "Davis";
        List<OwnerDTO> owners = this.ownerService.findByLastName(LAST_NAME);

        assertNotNull(owners);
        assertEquals(2, owners.size());
        log.info("OWNERS FOUND BY LAST NAME {}: {}", LAST_NAME, owners);
    }

    /**
     * Test buscar dueños por ciudad
     */
    @Test
    public void testFindOwnerByCity() {
        String CITY = "Monona";
        List<OwnerDTO> owners = this.ownerService.findByCity(CITY);

        assertNotNull(owners);
        assertEquals(2, owners.size());
        log.info("OWNERS IN CITY {}: {}", CITY, owners);
    }

    /**
     * Test crear un nuevo dueño
     */
    @Test
    public void testCreateOwner() {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("Alejandro")
                .lastName("Toledo")
                .address("Av. Primavera 123")
                .city("Lima")
                .telephone("987654321")
                .build();

        OwnerDTO createdOwner = this.ownerService.create(newOwner);

        log.info("OWNER CREATED: {}", createdOwner);
        assertNotNull(createdOwner.getId());
        assertEquals("Alejandro", createdOwner.getFirstName());
        assertEquals("Toledo", createdOwner.getLastName());
        assertEquals("Lima", createdOwner.getCity());
    }

    /**
     * Test actualizar un dueño
     */
    @Test
    public void testUpdateOwner() {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("Pedro")
                .lastName("Castillo")
                .address("Calle Real 456")
                .city("Chota")
                .telephone("912345678")
                .build();

        OwnerDTO createdOwner = this.ownerService.create(newOwner);

        // Actualizamos datos
        createdOwner.setAddress("Av. Arequipa 789");
        createdOwner.setCity("Lima");
        createdOwner.setTelephone("999111222");

        OwnerDTO updatedOwner = this.ownerService.update(createdOwner);

        log.info("OWNER UPDATED: {}", updatedOwner);
        assertEquals("Av. Arequipa 789", updatedOwner.getAddress());
        assertEquals("Lima", updatedOwner.getCity());
        assertEquals("999111222", updatedOwner.getTelephone());
    }

    /**
     * Test eliminar un dueño
     */
    @Test
    public void testDeleteOwner() {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("Manuel")
                .lastName("Prado")
                .address("Jr. Unión 101")
                .city("Cusco")
                .telephone("944556677")
                .build();

        OwnerDTO createdOwner = this.ownerService.create(newOwner);
        Integer id = createdOwner.getId();

        try {
            this.ownerService.delete(id);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(OwnerNotFoundException.class, () -> {
            this.ownerService.findById(id);
        });
        log.info("OWNER DELETED SUCCESSFULLY WITH ID: {}", id);
    }
}
