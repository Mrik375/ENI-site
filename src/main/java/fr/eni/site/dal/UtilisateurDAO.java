package fr.eni.site.dal;

import fr.eni.site.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {
	void create(Utilisateur utilisateur);

	Utilisateur read(String pseudo);

	boolean exists(String pseudo);

	List<Utilisateur> findAll();
	
	void update(Utilisateur utilisateur);
}
