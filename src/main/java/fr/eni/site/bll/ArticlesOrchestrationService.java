package fr.eni.site.bll;

import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;
import fr.eni.site.bo.CategorieArticle;

import java.util.List;

public interface ArticlesOrchestrationService {

	List<ArticleAVendre> getAllArticles();

	List<ArticleAVendre> getAllActiveArticles();

	List<ArticleAVendre> getArticlesFiltre(ArticleStatus[] status, String pseudo, String filtre, CategorieArticle categorie, long[] idArticle, boolean notPseudo);

	List<ArticleAVendre> getMesEncheresEnCours(String pseudo, String nomArticle, CategorieArticle categorie);
}
