package fr.eni.site.bll;

import fr.eni.site.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
	void registerUtilisateur(Utilisateur utilisateur) throws Exception;
	Optional<Utilisateur> getUtilisateurByPseudo(String pseudo);
	List<Utilisateur> getAllUtilisateurs();
	Optional<Utilisateur> getCurrentUtilisateur();
}
