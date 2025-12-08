package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.TokenJWTDto;
import it.unimol.newunimol.user_roles_management.dto.UserCreationDto;
import it.unimol.newunimol.user_roles_management.dto.UserDto;
import it.unimol.newunimol.user_roles_management.exceptions.AuthException;
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
    public TokenJWTDto login(String username, String password) throws AuthException, UnknownUserException {
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
    public TokenJWTDto refreshToken(String token) throws RuntimeException {
        return tokenService.refreshToken(token);
    }
}
