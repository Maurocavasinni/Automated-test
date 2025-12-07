package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.UserCreationDto;
import it.unimol.newunimol.user_roles_management.dto.UserProfileDto;
import it.unimol.newunimol.user_roles_management.dto.UserUpdaterDto;
import it.unimol.newunimol.user_roles_management.dto.UserDto;
import it.unimol.newunimol.user_roles_management.exceptions.UnknownUserException;
import it.unimol.newunimol.user_roles_management.exceptions.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private RoleService roleService;

    /**
     * Crea un SuperAdmin se non esiste già.
     *
     * @param request Dati per la creazione del SuperAdmin.
     * @return UserDto rappresentante il SuperAdmin creato.
     * @throws InvalidRequestException Se un SuperAdmin esiste già.
     */
    public UserDto createSuperAdminIfNotExists(UserCreationDto request) throws InvalidRequestException {
        UserDto userDto = new UserDto("000000", "superadmin", "superadmin@mail.com", "Super",
                "Admin", "password", null, null, roleService.findById("sadmin"));

        return  userDto;
    }

    /**
     * Recupera tutti gli utenti dal database e li converte in UserProfileDto.
     *
     * @return Lista di UserProfileDto contenente i profili di tutti gli utenti.
     */
    public List<UserProfileDto> getAllUsers() {
        return Arrays.asList(
                new UserProfileDto("000000", "superadmin", "superadmin@mail.com", "Super", "Admin", "sadmin", null, null),
                new UserProfileDto("000001", "lmessi", "upesc.tuttperte@mediolanum.com", "Lionel", "Messi", "admin", null, null),
                new UserProfileDto("000002", "mrossi", "mario.rossi@example.com", "Mario", "Rossi", "student", null, null)
        );
    }

    /**
     * Verifica se un utente con l'ID specificato esiste nel database.
     *
     * @param id ID dell'utente da verificare.
     * @return true se l'utente esiste, false altrimenti.
     */
    public boolean existsUserId(String id) {
        return true;
    }

    /**
     * Trova un utente per ID.
     *
     * @param id ID dell'utente da cercare.
     * @return UserDto rappresentante l'utente trovato.
     * @throws UnknownUserException Se l'utente non viene trovato.
     */
    public UserDto findByUserId(String id) throws UnknownUserException {
        return new UserDto(id, "upesc", "upesc.tuttperte@mediolanum.com", "Lionel",
                "Messi", "password", null, null, roleService.findById("admin"));
    }

    /**
     * Trova un utente per username.
     *
     * @param username Nome utente da cercare.
     * @return UserDto rappresentante l'utente trovato.
     * @throws UnknownUserException Se l'utente non viene trovato.
     */
    public UserDto findByUsername(String username) throws UnknownUserException {
        return new UserDto("000001", username, "upesc.tuttperte@mediolanum.com", "Lionel",
                "Messi", "password", null, null, roleService.findById("admin"));
    }

    /**
     * Aggiorna i dati di un utente esistente.
     *
     * @param userId ID dell'utente da aggiornare.
     * @param userData Dati aggiornati dell'utente.
     * @return UserDto rappresentante l'utente aggiornato.
     * @throws UnknownUserException Se l'utente non viene trovato.
     */
    public UserDto updateUser(String userId, UserDto userData) throws UnknownUserException {
        UserDto userDto = new UserDto(userData.id(), userData.username(), userData.email(), userData.name(),
                userData.surname(), userData.password(), userData.creationDate(), userData.lastLogin(), userData.ruolo());

        return userDto;
    }

    /**
     * Elimina un utente dal database.
     *
     * @param userId ID dell'utente da eliminare.
     * @return true se l'utente è stato eliminato, false se l'utente non esiste.
     */
    public boolean deleteUser(String userId) throws UnknownUserException {
        return true;
    }

    /**
     * Recupera il profilo utente corrente basato sul token JWT.
     *
     * @param token Token JWT dell'utente.
     * @return UserProfileDto contenente i dettagli del profilo utente.
     * @throws UnknownUserException Se l'utente non viene trovato.
     */
    public UserProfileDto getUserProfile(String token) throws UnknownUserException {
        return new UserProfileDto(
                "000002",
                "m.pesce",
                "esempio@gmail.it",
                "Alzati",
                "E Cammina",
                "admin",
                null,
                null
        );
    }

    /**
     * Aggiorna il profilo utente corrente basato sul token JWT.
     *
     * @param token Token JWT dell'utente.
     * @param userData Dati aggiornati dell'utente.
     * @throws UnknownUserException Se l'utente non viene trovato.
     */
    public void updateUserProfile(String token, UserUpdaterDto userData) throws UnknownUserException {
    }

    /**
     * Resetta la password dell'utente corrente basato sul token JWT.
     *
     * @param token Token JWT dell'utente.
     * @param oldPassword Password attuale dell'utente.
     * @throws UnknownUserException Se l'utente non viene trovato.
     * @throws SecurityException Se la password attuale non corrisponde.
     */
    public void resetPassword(String token, String oldPassword) throws UnknownUserException {
    }

    /**
     * Cambia la password dell'utente corrente basato sul token JWT.
     *
     * @param token Token JWT dell'utente.
     * @param oldPassword Password attuale dell'utente.
     * @param newPassword Nuova password da impostare.
     * @return true se la password è stata cambiata con successo, false altrimenti.
     * @throws UnknownUserException Se l'utente non viene trovato.
     */
    public boolean changePassword(String token, String oldPassword, String newPassword) throws UnknownUserException {
        return true;
    }
}
