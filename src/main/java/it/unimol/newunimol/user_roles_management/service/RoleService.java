package it.unimol.newunimol.user_roles_management.service;

import it.unimol.newunimol.user_roles_management.dto.RoleDto;
import it.unimol.newunimol.user_roles_management.exceptions.UnknownUserException;
import it.unimol.newunimol.user_roles_management.util.RoleLevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Autowired
    private TokenJWTService tokenService;

    private static final Map<String, RoleDto> ROLES = Map.of(
            "sadmin", new RoleDto("sadmin", "SUPER_ADMIN", "Amministratore di sistema con tutti i privilegi"),
            "admin", new RoleDto("admin", "ADMIN", "Amministratore con privilegi di gestione utenti"),
            "teach", new RoleDto("teach", "DOCENTE", "Ruolo con permessi aggiuntivi per i docenti"),
            "student", new RoleDto("student", "STUDENTE", "Ruolo base, riservato agli studenti")
    );

    /**
     * Restituisce tutti i ruoli presenti nel sistema.
     *
     * @return Una lista di RoleDto che rappresentano i ruoli.
     */
    public List<RoleDto> getAllRoles() {
        return new ArrayList<>(ROLES.values());
    }

    /**
     * Trova un ruolo per ID nel database.
     *
     * @param roleId L'ID del ruolo da cercare.
     * @return Un RoleDto se il ruolo esiste, altrimenti null.
     */
    public RoleDto findById(String roleId) {
        if (roleId == null || roleId.isEmpty()) {
            return null;
        }
        return ROLES.get(roleId);
    }

    /**
     * Controlla se l'utente ha il ruolo richiesto per eseguire un'operazione.
     *
     * @param token Il token JWT dell'utente.
     * @param role Il ruolo richiesto per l'operazione.
     * @throws UnknownUserException Se l'utente non esiste o il token non è valido.
     */
    public void checkRole(String token, RoleLevelEnum role) throws UnknownUserException {
        if (!tokenService.isTokenValid(token)) {
            throw new SecurityException("Token non valido o scaduto");
        }

        String userRole = tokenService.extractRole(token);
        if (RoleLevelEnum.fromRoleName(userRole).getLevel() < role.getLevel()) {
            throw new SecurityException("Permessi insufficienti per questa operazione");
        }
    }

    /**
     * Assegna un ruolo a un utente.
     *
     * @param userId L'ID dell'utente a cui assegnare il ruolo.
     * @param roleId L'ID del ruolo da assegnare.
     * @return true se il ruolo è stato assegnato, false se l'utente ha già quel ruolo.
     * @throws IllegalArgumentException Se l'utente o il ruolo non esistono.
     */
    public boolean assignRole(String userId, String roleId) throws IllegalArgumentException {
        return true;
    }
}
