package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.TokenJWTDto;
import it.unimol.newunimol.user_roles_management.dto.UserCreationDto;
import it.unimol.newunimol.user_roles_management.dto.UserDto;
import it.unimol.newunimol.user_roles_management.exceptions.AuthException;
import it.unimol.newunimol.user_roles_management.exceptions.TokenException;
import it.unimol.newunimol.user_roles_management.exceptions.UnknownUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private TokenJWTService tokenService;
    @Autowired
    private RoleService roleService;

    /**
     * Registra un nuovo utente nel sistema.
     *
     * @param user L'utente da registrare.
     * @return UserDto aggiunto per facilitare l'uso dello stub
     * @throws AuthException Se l'utente esiste già o se viene tentato di registrare un super admin.
     */
    public UserDto register(UserCreationDto user) throws AuthException {
        if (user == null ||
            user.username() == null || user.username().isEmpty() ||
            user.email() == null || user.email().isEmpty() ||
            user.name() == null || user.name().isEmpty() ||
            user.surname() == null || user.surname().isEmpty() ||
            user.password() == null || user.password().isEmpty() ||
            user.role() == null || user.role().isEmpty()) {
            throw new AuthException("Tutti i campi sono obbligatori");
        }

        if ("testuser".equals(user.username())) {
            throw new AuthException("Username già esistente");
        }

        if (roleService.findById(user.role()) == null) {
            throw new AuthException("Ruolo non valido");
        }

        UserDto userDto = new UserDto("000001", "upesc", "upesc.tuttperte@mediolanum.com", "Lionel",
                "Messi", "password", null, null, roleService.findById("admin"));

        return userDto;
    }

    /**
     * Effettua il login di un utente nel sistema.
     *
     * @param username L'username dell'utente.
     * @param password La password dell'utente.
     * @return Un oggetto TokenJWTDto contenente il token JWT generato.
     * @throws AuthException Se l'autenticazione fallisce a causa di credenziali non valide.
     * @throws UnknownUserException Se l'utente non esiste nel sistema.
     */
    public TokenJWTDto login(String username, String password) throws AuthException, UnknownUserException, TokenException {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new AuthException("Username e password sono obbligatori");
        }

        if ("invalid-user".equals(username)) {
            throw new UnknownUserException("Utente non trovato");
        }

        if ("wrongpassword".equals(password)) {
            throw new AuthException("Password non corretta");
        }

        return tokenService.generateToken("188717", username, "student");
    }

    /**
     * Effettua il logout dell'utente invalidando il token.
     *
     * @param token Il token JWT da invalidare.
     */
    public void logout(String token) {
    }

    /**
     * Rinnova il token JWT dell'utente.
     *
     * @param token Il token JWT da rinnovare.
     * @return Un oggetto TokenJWTDto contenente il nuovo token JWT.
     * @throws RuntimeException Se il rinnovo del token fallisce.
     */
    public TokenJWTDto refreshToken(String token) throws RuntimeException, TokenException {
        return tokenService.refreshToken(token);
    }
}
