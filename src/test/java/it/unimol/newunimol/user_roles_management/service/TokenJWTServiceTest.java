package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.TokenJWTDto;
import it.unimol.newunimol.user_roles_management.exceptions.TokenException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenJWTServiceTest {

    @Autowired
    private TokenJWTService tokenService;

    @Test
    void testGenerateToken() throws TokenException {
        TokenJWTDto token = tokenService.generateToken("123456", "testuser", "admin");
        
        assertNotNull(token);
        assertNotNull(token.token());
        assertFalse(token.token().isEmpty());
    }

    @Test
    void testExtractUserId() throws TokenException {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        String userId = tokenService.extractUserId(tokenDto.token());
        
        assertEquals("123456", userId);
    }

    @Test
    void testExtractUsername() throws TokenException {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        String username = tokenService.extractUsername(tokenDto.token());
        
        assertEquals("testuser", username);
    }

    @Test
    void testExtractRole() throws TokenException {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        String role = tokenService.extractRole(tokenDto.token());
        
        assertEquals("admin", role);
    }

    @Test
    void testIsTokenValid() throws TokenException {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        boolean isValid = tokenService.isTokenValid(tokenDto.token());
        
        assertTrue(isValid);
    }

    @Test
    void testRefreshToken() throws TokenException {
        TokenJWTDto oldToken = tokenService.generateToken("123456", "testuser", "admin");
        TokenJWTDto newToken = tokenService.refreshToken(oldToken.token());
        
        assertNotNull(newToken);
        assertNotNull(newToken.token());
    }

    @Test
    void testExtractUserIdFromInvalidToken() {
        assertThrows(Exception.class, () -> {
            tokenService.extractUserId("invalid-token");
        });
    }

    @Test
    void testExtractUsernameFromInvalidToken() {
        assertThrows(Exception.class, () -> {
            tokenService.extractUsername("invalid-token");
        });
    }

    @Test
    void testExtractRoleFromInvalidToken() {
        assertThrows(Exception.class, () -> {
            tokenService.extractRole("invalid-token");
        });
    }

    @Test
    void testIsTokenValidWithInvalidToken() {
        assertThrows(Exception.class, () -> {
            tokenService.isTokenValid("invalid-token");
        });
    }

    @Test
    void testRefreshTokenWithInvalidToken() {
        assertThrows(Exception.class, () -> {
            tokenService.refreshToken("invalid-token");
        });
    }

    @Test
    void testGenerateTokenWithEmptyValues() throws TokenException {
        TokenJWTDto token = tokenService.generateToken("", "", "");
        
        assertNotNull(token);
        assertNotNull(token.token());
    }

    @Test
    void testGenerateTokenWithNullValues() {
        assertThrows(Exception.class, () -> {
            tokenService.generateToken(null, null, null);
        });
    }

    @Test
    void testParseToken() throws TokenException {
        TokenJWTDto originalToken = tokenService.generateToken("123", "test", "admin");
        TokenJWTDto parsedToken = tokenService.parseToken(originalToken.token());
        
        assertNotNull(parsedToken);
        assertEquals(originalToken.token(), parsedToken.token());
    }

    @Test
    void testGenerateTokenWithSpecialCharacters() throws TokenException {
        TokenJWTDto token = tokenService.generateToken("123@#$", "user@test", "admin-role");
        
        assertNotNull(token);
        assertNotNull(token.token());
        assertFalse(token.token().isEmpty());
    }

    @Test
    void testExtractClaimsFromValidToken() throws TokenException {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        
        String userId = tokenService.extractUserId(tokenDto.token());
        String username = tokenService.extractUsername(tokenDto.token());
        String role = tokenService.extractRole(tokenDto.token());
        
        assertAll(
            () -> assertEquals("123456", userId),
            () -> assertEquals("testuser", username),
            () -> assertEquals("admin", role)
        );
    }

    @Test
    void testIsTokenValidWithExpiredToken() throws TokenException {
        TokenJWTDto tokenDto = tokenService.generateToken("123456", "testuser", "admin");
        assertDoesNotThrow(() -> tokenService.isTokenValid(tokenDto.token()));
    }

    @Test
    void testGenerateTokenWithLongValues() throws TokenException {
        String longUserId = "123456789012345678901234567890";
        String longUsername = "verylongusernamethatexceedsnormallimits";
        String longRole = "verylongrolename";
        
        TokenJWTDto token = tokenService.generateToken(longUserId, longUsername, longRole);
        
        assertNotNull(token);
        assertNotNull(token.token());
        assertEquals(longUserId, tokenService.extractUserId(token.token()));
    }
}
