package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.TokenJWTDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenJWTServiceTest {

    @Autowired
    private TokenJWTService tokenService;

    @Test
    void testGenerateToken() {
        TokenJWTDto token = tokenService.generateToken("123456", "testuser", "admin");
        
        assertNotNull(token);
        assertNotNull(token.token());
        assertFalse(token.token().isEmpty());
    }

    @Test
    void testExtractUserId() {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        String userId = tokenService.extractUserId(tokenDto.token());
        
        assertEquals("123456", userId);
    }

    @Test
    void testExtractUsername() {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        String username = tokenService.extractUsername(tokenDto.token());
        
        assertEquals("testuser", username);
    }

    @Test
    void testExtractRole() {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        String role = tokenService.extractRole(tokenDto.token());
        
        assertEquals("admin", role);
    }

    @Test
    void testIsTokenValid() {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        boolean isValid = tokenService.isTokenValid(tokenDto.token());
        
        assertTrue(isValid);
    }

    @Test
    void testRefreshToken() {
        TokenJWTDto oldToken = tokenService.generateToken("123456", "testuser", "admin");
        TokenJWTDto newToken = tokenService.refreshToken(oldToken.token());
        
        assertNotNull(newToken);
        assertNotNull(newToken.token());
    }
}
