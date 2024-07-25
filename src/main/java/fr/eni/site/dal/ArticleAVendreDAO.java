package fr.eni.site.dal;

import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;

import java.util.List;

public interface ArticleAVendreDAO {
	long create(ArticleAVendre article);

	ArticleAVendre read(long id);

	List<ArticleAVendre> findAll();

	List<ArticleAVendre> findByUtilisateur(String pseudo);

	List<ArticleAVendre> findAllActive();

	void setStatus(long id, ArticleStatus statutEnchere);


}
