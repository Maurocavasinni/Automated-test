package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.RoleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    void testGetAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        
        assertNotNull(roles);
        assertEquals(4, roles.size());
    }

    @Test
    void testFindById() {
        RoleDto role = roleService.findById("admin");
        
        assertNotNull(role);
        assertEquals("admin", role.id());
        assertEquals("ADMIN", role.nome());
    }

    @Test
    void testFindByIdNotFound() {
        RoleDto role = roleService.findById("nonexistent");
        assertNull(role);
    }
}
