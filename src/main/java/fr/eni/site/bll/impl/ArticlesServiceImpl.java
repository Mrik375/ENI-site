package fr.eni.site.bll.impl;

import fr.eni.site.bll.ArticlesService;
import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.CategorieService;
import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;
import fr.eni.site.bo.CategorieArticle;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.eni.site.bo.ArticleStatus.EN_COURS;

@Service
public class ArticlesServiceImpl implements ArticlesService {

	private final ArticleAVendreService articleAVendreService;
	private final CategorieService categorieService;
	private final UtilisateursService utilisateursService;

	public ArticlesServiceImpl(ArticleAVendreService articleAVendreService, CategorieService categorieService, UtilisateursService utilisateursService) {
		this.articleAVendreService = articleAVendreService;
		this.categorieService = categorieService;
		this.utilisateursService = utilisateursService;
	}

	@Override
	public List<ArticleAVendre> getAllArticles() {
		List<ArticleAVendre> articles = articleAVendreService.getAll();
		articles.forEach(this::chargerDependencesDansArticle);
		return articles;
	}

	@Override
	public List<ArticleAVendre> getAllActiveArticles() {
		List<ArticleAVendre> articles = getArticlesFiltre(new ArticleStatus[]{EN_COURS}, null, null, null);
		articles.forEach(this::chargerDependencesDansArticle);
		return articles;
	}

	@Override
	public List<ArticleAVendre> getArticlesFiltre(ArticleStatus[] status, String pseudo, String nomArticle, CategorieArticle categorie) {
		List<ArticleAVendre> articles = articleAVendreService.getStatusByFiltre(status, pseudo, nomArticle, categorie);
		articles.forEach(this::chargerDependencesDansArticle);
		return articles;
	}

	public void chargerDependencesDansArticle(ArticleAVendre article) {
		chargeCategorieDansArticle(article);
		chargeAdresseDansArticle(article);
		chargeUtilisateurDansArticle(article);
	}

	public void chargeCategorieDansArticle(ArticleAVendre article) {
		article.setCategorie(categorieService.getById(article.getCategorie().getId()));
	}

	public void chargeAdresseDansArticle(ArticleAVendre article) {
		article.setAdresseRetrait(utilisateursService.getAdresse(article.getAdresseRetrait().getId()));
	}

	public void chargeUtilisateurDansArticle(ArticleAVendre article) {
		article.setVendeur(utilisateursService.getUtilisateur(article.getVendeur().getPseudo()));
	}


}
