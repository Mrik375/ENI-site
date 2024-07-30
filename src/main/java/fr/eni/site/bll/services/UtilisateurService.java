package fr.eni.site.bll.services;

import fr.eni.site.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
	void create(Utilisateur utilisateur) throws Exception;

	boolean exists(String pseudo);

	Utilisateur getByPseudo(String pseudo);

	List<Utilisateur> getAll();

	Optional<Utilisateur> getLogged();
	
	void update(Utilisateur utilisateur);
}
