package fr.eni.site.bll.impl;

import fr.eni.site.bll.ArticlesOrchestrationService;
import fr.eni.site.bll.UtilisateursOrchestrationService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.CategorieService;
import fr.eni.site.bll.services.EnchereService;
import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;
import fr.eni.site.bo.CategorieArticle;
import fr.eni.site.bo.Enchere;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.eni.site.bo.ArticleStatus.EN_COURS;

@Service
public class ArticlesOrchestrationServiceImpl implements ArticlesOrchestrationService {

	private final ArticleAVendreService articleAVendreService;
	private final CategorieService categorieService;
	private final UtilisateursOrchestrationService utilisateursOrchestrationService;
	private final EnchereService enchereService;

	public ArticlesOrchestrationServiceImpl(ArticleAVendreService articleAVendreService, CategorieService categorieService, UtilisateursOrchestrationService utilisateursOrchestrationService, EnchereService enchereService) {
		this.articleAVendreService = articleAVendreService;
		this.categorieService = categorieService;
		this.utilisateursOrchestrationService = utilisateursOrchestrationService;
		this.enchereService = enchereService;
	}

	@Override
	public List<ArticleAVendre> getAllArticles() {
		List<ArticleAVendre> articles = articleAVendreService.getAll();
		articles.forEach(this::chargerDependencesDansArticle);
		return articles;
	}

	@Override
	public List<ArticleAVendre> getAllActiveArticles() {
		List<ArticleAVendre> articles = getArticlesFiltre(new ArticleStatus[]{EN_COURS}, null, null, null, null);
		articles.forEach(this::chargerDependencesDansArticle);
		return articles;
	}

	@Override
	public List<ArticleAVendre> getArticlesFiltre(ArticleStatus[] status, String pseudo, String nomArticle, CategorieArticle categorie, long[] idArticle) {
		List<ArticleAVendre> articles = articleAVendreService.getStatusByFiltre(status, pseudo, nomArticle, categorie, idArticle);
		articles.forEach(this::chargerDependencesDansArticle);
		return articles;
	}

	@Override
	public List<ArticleAVendre> getMesEncheresEnCours(String pseudo, String nomArticle, CategorieArticle categorie) {
		List<Enchere> encheres = enchereService.getEncheresByPseudo(pseudo);
		long[] idArticles = encheres.stream().mapToLong(Enchere::getArticleAVendreId).toArray();
		List<ArticleAVendre> articles = getArticlesFiltre(new ArticleStatus[]{EN_COURS}, pseudo, nomArticle, categorie, idArticles);
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
		article.setAdresseRetrait(utilisateursOrchestrationService.getAdresse(article.getAdresseRetrait().getId()));
	}

	public void chargeUtilisateurDansArticle(ArticleAVendre article) {
		article.setVendeur(utilisateursOrchestrationService.getUtilisateur(article.getVendeur().getPseudo()));
	}


}
