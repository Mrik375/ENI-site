package fr.eni.site.dal;

import fr.eni.site.bo.Enchere;

import java.util.List;

public interface EnchereDAO {
	void create(Enchere enchere);

	Enchere read(String idUtilisateur, long noArticle, int montantEnchere);

	List<Enchere> findAll();

	List<Enchere> findByUtilisateur(String pseudo);

	List<Enchere> findByArticle(long noArticle);

	Enchere findHighestMontantByArticle(long noArticle);
}
