package fr.eni.site.bll.services;

import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;

import java.util.List;

public interface ArticleAVendreService {
	long create(ArticleAVendre article);

	ArticleAVendre getById(long id);

	List<ArticleAVendre> getAll();

	List<ArticleAVendre> getByUtilisateur(String pseudo);

	List<ArticleAVendre> getAllActive();

	void setArticleStatus(long id, ArticleStatus statutEnchere);

}

