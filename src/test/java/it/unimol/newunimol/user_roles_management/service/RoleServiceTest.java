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

    @Test
    void testFindByIdWithNullId() {
        RoleDto role = roleService.findById(null);
        assertNull(role);
    }

    @Test
    void testFindByIdWithEmptyString() {
        RoleDto role = roleService.findById("");
        assertNull(role);
    }

    @Test
    void testGetAllRolesNotNull() {
        List<RoleDto> roles = roleService.getAllRoles();
        
        assertNotNull(roles);
        assertFalse(roles.isEmpty());
        assertTrue(roles.size() > 0);
    }

    @Test
    void testFindByIdStudent() {
        RoleDto role = roleService.findById("student");
        
        assertNotNull(role);
        assertEquals("student", role.id());
    }

    @Test
    void testFindByIdTeacher() {
        RoleDto role = roleService.findById("teach");
        
        assertNotNull(role);
        assertEquals("teach", role.id());
    }

    @Test
    void testFindByIdSuperAdmin() {
        RoleDto role = roleService.findById("sadmin");
        
        assertNotNull(role);
        assertEquals("sadmin", role.id());
    }

    @Test
    void testAllRolesHaveValidProperties() {
        List<RoleDto> roles = roleService.getAllRoles();
        
        for (RoleDto role : roles) {
            assertNotNull(role.id());
            assertNotNull(role.nome());
            assertFalse(role.id().isEmpty());
            assertFalse(role.nome().isEmpty());
        }
    }

    @Test
    void testAssignRoleSuccess() {
        boolean result = roleService.assignRole("000001", "admin");
        assertTrue(result);
    }

    @Test
    void testAssignRoleWithValidIds() {
        assertDoesNotThrow(() -> {
            roleService.assignRole("000001", "student");
        });
    }

    @Test
    void testGetAllRolesContainsAllPredefinedRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        
        assertTrue(roles.stream().anyMatch(r -> r.id().equals("sadmin")));
        assertTrue(roles.stream().anyMatch(r -> r.id().equals("admin")));
        assertTrue(roles.stream().anyMatch(r -> r.id().equals("teach")));
        assertTrue(roles.stream().anyMatch(r -> r.id().equals("student")));
    }
}
