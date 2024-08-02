package fr.eni.site.bll.services;

import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;
import fr.eni.site.bo.CategorieArticle;

import java.util.List;

public interface ArticleAVendreService {
	long create(ArticleAVendre article);

	ArticleAVendre getById(long id);

	List<ArticleAVendre> getAll();

	List<ArticleAVendre> getByUtilisateur(String pseudo);

	void setArticleStatus(long id, ArticleStatus statutEnchere);

	List<ArticleAVendre> getStatusByFiltre(ArticleStatus[] articleStatus, String pseudo, String nomArticle, CategorieArticle categorie, long[] idArticles, boolean notPseudo);
}

