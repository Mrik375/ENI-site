package fr.eni.site.dal;

import fr.eni.site.bo.ArticleAVendre;

import java.util.List;

public interface ArticleAVendreDAO {
	void create(ArticleAVendre article);

	ArticleAVendre read(long id);

	List<ArticleAVendre> findAll();

	List<ArticleAVendre> findByUtilisateur(String pseudo);
}
