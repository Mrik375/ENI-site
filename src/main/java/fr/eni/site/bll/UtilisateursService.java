package fr.eni.site.bll;

import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;

public interface UtilisateursService {

	void registerUtilisateur(Utilisateur utilisateur, Adresse adresse) throws Exception;

}
