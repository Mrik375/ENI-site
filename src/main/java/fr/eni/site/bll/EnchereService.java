package fr.eni.site.bll;

import fr.eni.site.bo.Enchere;

import java.util.List;

public interface EnchereService {
	void createEnchere(Enchere enchere);

	Enchere getEnchere(String idUtilisateur, long noArticle, int montantEnchere);

	List<Enchere> getAllEncheres();

	List<Enchere> getEncheresByUtilisateur(String pseudo);

	List<Enchere> getEncheresByArticle(long noArticle);

	Enchere getHighestEnchereForArticle(long noArticle);
}

