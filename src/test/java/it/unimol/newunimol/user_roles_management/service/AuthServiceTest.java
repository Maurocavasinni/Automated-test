package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.TokenJWTDto;
import it.unimol.newunimol.user_roles_management.dto.UserCreationDto;
import it.unimol.newunimol.user_roles_management.dto.UserDto;
import it.unimol.newunimol.user_roles_management.exceptions.AuthException;
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
        UserCreationDto userRequest = new UserCreationDto("testuser", "test@mail.com",
                "Test", "User", "password", "student");
        
        UserDto result = authService.register(userRequest);
        
        assertNotNull(result);
        assertEquals("000001", result.id());
        assertEquals("upesc", result.username());
    }

    @Test
    void testLogin() throws AuthException, UnknownUserException {
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
}
