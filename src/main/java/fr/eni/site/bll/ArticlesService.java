package fr.eni.site.bll;

import fr.eni.site.bo.ArticleAVendre;

import java.util.List;

public interface ArticlesService {

	List<ArticleAVendre> findAll();
}
