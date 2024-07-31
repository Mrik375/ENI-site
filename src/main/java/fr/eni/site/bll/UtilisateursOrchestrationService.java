package fr.eni.site.bll;

import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UtilisateursOrchestrationService extends UserDetailsService {

	void registerUtilisateur(Utilisateur utilisateur) throws Exception;

	Utilisateur getUtilisateur(String pseudo);

	Adresse getAdresse(long id);

	void updateUtilisateur(Utilisateur utilisateur, String field, String oldPseudo);
}
