package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.TokenJWTDto;
import it.unimol.newunimol.user_roles_management.dto.UserCreationDto;
import it.unimol.newunimol.user_roles_management.dto.UserDto;
import it.unimol.newunimol.user_roles_management.exceptions.AuthException;
import it.unimol.newunimol.user_roles_management.exceptions.TokenException;
import it.unimol.newunimol.user_roles_management.exceptions.UnknownUserException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void testRegister() throws AuthException {
        UserCreationDto userRequest = new UserCreationDto("upesc", "test@mail.com",
                "Test", "User", "password", "student");
        
        UserDto result = authService.register(userRequest);
        
        assertNotNull(result);
        assertEquals("000001", result.id());
        assertEquals("upesc", result.username());
    }

    @Test
    void testLogin() throws AuthException, UnknownUserException, TokenException {
        TokenJWTDto token = authService.login("testuser", "password");
        
        assertNotNull(token);
        assertNotNull(token.token());
        assertFalse(token.token().isEmpty());
    }

    @Test
    void testLogout() {
        String token = "test-token";
        assertDoesNotThrow(() -> authService.logout(token));
    }

    @Test
    void testLoginWithInvalidUsername() {
        assertThrows(Exception.class, () -> {
            authService.login("invalid-user", "password");
        });
    }

    @Test
    void testLoginWithInvalidPassword() {
        assertThrows(Exception.class, () -> {
            authService.login("testuser", "wrongpassword");
        });
    }

    @Test
    void testLoginWithEmptyCredentials() {
        assertThrows(Exception.class, () -> {
            authService.login("", "");
        });
    }

    @Test
    void testLoginWithNullCredentials() {
        assertThrows(Exception.class, () -> {
            authService.login(null, null);
        });
    }

    @Test
    void testRegisterWithDuplicateUsername() {
        UserCreationDto userRequest = new UserCreationDto("testuser", "duplicate@mail.com",
                "Test", "User", "password", "student");
        
        assertThrows(AuthException.class, () -> {
            authService.register(userRequest);
        });
    }

    @Test
    void testRegisterWithInvalidRole() {
        UserCreationDto userRequest = new UserCreationDto("newuser", "new@mail.com",
                "New", "User", "password", "invalid-role");
        
        assertThrows(AuthException.class, () -> {
            authService.register(userRequest);
        });
    }

    @Test
    void testRegisterWithEmptyFields() {
        UserCreationDto userRequest = new UserCreationDto("", "", "", "", "", "");
        
        assertThrows(AuthException.class, () -> {
            authService.register(userRequest);
        });
    }

    @Test
    void testLogoutWithValidToken() {
        assertDoesNotThrow(() -> authService.logout("valid-token"));
    }

    @Test
    void testLogoutWithNullToken() {
        assertDoesNotThrow(() -> authService.logout(null));
    }

    @Test
    void testLogoutWithEmptyToken() {
        assertDoesNotThrow(() -> authService.logout(""));
    }

    @Test
    void testRefreshTokenWithValidToken() throws AuthException, UnknownUserException, TokenException {
        TokenJWTDto loginToken = authService.login("testuser", "password");
        TokenJWTDto refreshedToken = authService.refreshToken(loginToken.token());
        
        assertNotNull(refreshedToken);
        assertNotNull(refreshedToken.token());
    }

    @Test
    void testRefreshTokenWithInvalidToken() {
        assertThrows(RuntimeException.class, () -> {
            authService.refreshToken("invalid-token");
        });
    }

    @Test
    void testLoginReturnsValidTokenStructure() throws AuthException, UnknownUserException, TokenException {
        TokenJWTDto token = authService.login("testuser", "password");
        
        assertNotNull(token);
        assertNotNull(token.token());
        assertTrue(token.token().split("\\.").length == 3); // JWT has 3 parts
    }

    @Test
    void testRegisterWithNullUser() {
        assertThrows(AuthException.class, () -> {
            authService.register(null);
        });
    }

    @Test
    void testRegisterWithNullUsername() {
        UserCreationDto userRequest = new UserCreationDto(null, "email@mail.com", "Test", "User", "password", "student");
        
        assertThrows(AuthException.class, () -> {
            authService.register(userRequest);
        });
    }

    @Test
    void testRegisterWithEmptyEmail() {
        UserCreationDto userRequest = new UserCreationDto("username", "", "Test", "User", "password", "student");
        
        assertThrows(AuthException.class, () -> {
            authService.register(userRequest);
        });
    }

    @Test
    void testRegisterWithNullPassword() {
        UserCreationDto userRequest = new UserCreationDto("username", "email@mail.com", "Test", "User", null, "student");
        
        assertThrows(AuthException.class, () -> {
            authService.register(userRequest);
        });
    }
}
