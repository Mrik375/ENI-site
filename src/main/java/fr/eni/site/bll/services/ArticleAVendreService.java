package fr.eni.site.bll.services;

import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;

import java.util.List;

public interface ArticleAVendreService {
	long createArticle(ArticleAVendre article);

	ArticleAVendre getArticleById(long id);

	List<ArticleAVendre> getAllArticles();

	List<ArticleAVendre> getArticlesByUtilisateur(String pseudo);

	List<ArticleAVendre> getAllActiveArticles();

	void setArticleStatus(long id, ArticleStatus statutEnchere);

}

