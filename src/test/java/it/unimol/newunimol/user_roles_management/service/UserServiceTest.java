package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.UserCreationDto;
import it.unimol.newunimol.user_roles_management.dto.UserDto;
import it.unimol.newunimol.user_roles_management.dto.UserProfileDto;
import it.unimol.newunimol.user_roles_management.exceptions.InvalidRequestException;
import it.unimol.newunimol.user_roles_management.exceptions.UnknownUserException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testCreateSuperAdminIfNotExists() throws InvalidRequestException {
        UserCreationDto request = new UserCreationDto("superadmin", "superadmin@mail.com", 
                "Super", "Admin", "password", "sadmin");
        
        UserDto result = userService.createSuperAdminIfNotExists(request);
        
        assertNotNull(result);
        assertEquals("000000", result.id());
        assertEquals("superadmin", result.username());
        assertEquals("superadmin@mail.com", result.email());
    }

    @Test
    void testGetAllUsers() {
        List<UserProfileDto> users = userService.getAllUsers();
        
        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals("000000", users.get(0).id());
        assertEquals("superadmin", users.get(0).username());
    }

    @Test
    void testFindByUserId() throws UnknownUserException {
        UserDto user = userService.findByUserId("000001");
        
        assertNotNull(user);
        assertEquals("000001", user.id());
        assertEquals("upesc", user.username());
    }

    @Test
    void testFindByUsername() throws UnknownUserException {
        UserDto user = userService.findByUsername("testuser");
        
        assertNotNull(user);
        assertEquals("testuser", user.username());
        assertEquals("upesc.tuttperte@mediolanum.com", user.email());
    }

    @Test
    void testUpdateUser() throws UnknownUserException {
        UserDto userData = new UserDto("000001", "newusername", "newemail@test.com",
                "NewName", "NewSurname", "password", null, null, null);
        
        UserDto updated = userService.updateUser("000001", userData);
        
        assertNotNull(updated);
        assertEquals("newusername", updated.username());
        assertEquals("newemail@test.com", updated.email());
    }

    @Test
    void testGetUserProfile() throws UnknownUserException {
        UserProfileDto profile = userService.getUserProfile("token");
        
        assertNotNull(profile);
        assertEquals("000002", profile.id());
        assertEquals("m.pesce", profile.username());
    }

    @Test
    void testFindByUserIdNotFound() {
        assertThrows(UnknownUserException.class, () -> {
            userService.findByUserId("999999");
        });
    }

    @Test
    void testFindByUsernameNotFound() {
        assertThrows(UnknownUserException.class, () -> {
            userService.findByUsername("nonexistent");
        });
    }

    @Test
    void testUpdateUserNotFound() {
        UserDto userData = new UserDto("999999", "username", "email@test.com",
                "Name", "Surname", "password", null, null, null);
        
        assertThrows(UnknownUserException.class, () -> {
            userService.updateUser("999999", userData);
        });
    }

    @Test
    void testGetUserProfileInvalidToken() {
        assertThrows(UnknownUserException.class, () -> {
            userService.getUserProfile("invalid-token");
        });
    }

    @Test
    void testCreateSuperAdminWithInvalidData() {
        UserCreationDto request = new UserCreationDto("", "", "", "", "", "");
        
        assertThrows(InvalidRequestException.class, () -> {
            userService.createSuperAdminIfNotExists(request);
        });
    }

    @Test
    void testCreateSuperAdminWithNullFields() {
        UserCreationDto request = new UserCreationDto(null, null, null, null, null, null);
        
        assertThrows(InvalidRequestException.class, () -> {
            userService.createSuperAdminIfNotExists(request);
        });
    }

    @Test
    void testGetAllUsersReturnsNonEmptyList() {
        List<UserProfileDto> users = userService.getAllUsers();
        
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertTrue(users.size() >= 1);
    }

    @Test
    void testUpdateUserWithPartialData() throws UnknownUserException {
        UserDto userData = new UserDto("000001", "partialuser", null,
                null, null, null, null, null, null);
        
        UserDto updated = userService.updateUser("000001", userData);
        
        assertNotNull(updated);
        assertEquals("000001", updated.id());
    }

    @Test
    void testFindByUserIdWithValidId() throws UnknownUserException {
        UserDto user = userService.findByUserId("000001");
        
        assertNotNull(user);
        assertNotNull(user.username());
        assertNotNull(user.email());
    }

    @Test
    void testGetUserProfileWithValidToken() throws UnknownUserException {
        UserProfileDto profile = userService.getUserProfile("token");
        
        assertNotNull(profile);
        assertNotNull(profile.username());
        assertNotNull(profile.email());
    }

    @Test
    void testCreateSuperAdminWithNullUsername() {
        UserCreationDto request = new UserCreationDto(null, "email@test.com", "Name", "Surname", "password", "sadmin");
        
        assertThrows(InvalidRequestException.class, () -> {
            userService.createSuperAdminIfNotExists(request);
        });
    }

    @Test
    void testCreateSuperAdminWithEmptyEmail() {
        UserCreationDto request = new UserCreationDto("username", "", "Name", "Surname", "password", "sadmin");
        
        assertThrows(InvalidRequestException.class, () -> {
            userService.createSuperAdminIfNotExists(request);
        });
    }

    @Test
    void testCreateSuperAdminWithNullPassword() {
        UserCreationDto request = new UserCreationDto("username", "email@test.com", "Name", "Surname", null, "sadmin");
        
        assertThrows(InvalidRequestException.class, () -> {
            userService.createSuperAdminIfNotExists(request);
        });
    }
}
