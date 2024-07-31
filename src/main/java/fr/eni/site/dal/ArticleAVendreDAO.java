package fr.eni.site.dal;

import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;
import fr.eni.site.bo.CategorieArticle;

import java.util.List;

public interface ArticleAVendreDAO {
	long create(ArticleAVendre article);

	ArticleAVendre read(long id);

	List<ArticleAVendre> findAll();

	List<ArticleAVendre> findByUtilisateur(String pseudo);

	void setStatus(long id, ArticleStatus statutEnchere);

	List<ArticleAVendre> findByFiltre(ArticleStatus[] articleStatus, String pseudo, String nomArticle, CategorieArticle categorie, long[] idArticles);
}
