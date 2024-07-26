package fr.eni.site.bll.services;

import fr.eni.site.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
	void registerUtilisateur(Utilisateur utilisateur) throws Exception;

	boolean utilisateurExists(String pseudo);

	Utilisateur getUtilisateur(String pseudo);

	List<Utilisateur> getAllUtilisateurs();

	Optional<Utilisateur> getCurrentUtilisateur();
}
