package fr.eni.site.bll.services;

import fr.eni.site.bo.Enchere;

import java.util.List;

public interface EnchereService {
	void createEnchere(Enchere enchere);

	Enchere getEnchere(String idUtilisateur, long noArticle, int montantEnchere);

	List<Enchere> getAll();

	List<Enchere> getByUtilisateur(String pseudo);

	List<Enchere> getByArticle(long noArticle);

	Enchere getHighestMontantByArticle(long noArticle);
}

