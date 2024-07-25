package fr.eni.site.bll;

import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
	void registerUtilisateur(Utilisateur utilisateur, Adresse adresse) throws Exception;

	Optional<String> getUtilisateurByPseudo(String pseudo);

	Optional<Utilisateur> getUtilisateur(String pseudo);

	List<Utilisateur> getAllUtilisateurs();

	Optional<Utilisateur> getCurrentUtilisateur();
}
