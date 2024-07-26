package fr.eni.site.bll.impl;

import fr.eni.site.bll.ArticlesService;
import fr.eni.site.bll.services.AdresseService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.CategorieService;
import fr.eni.site.bll.services.UtilisateurService;
import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.Categorie;
import fr.eni.site.bo.Utilisateur;

import java.util.List;

public class ArticlesServiceImpl implements ArticlesService {

	private final ArticleAVendreService articleAVendreService;
	private final CategorieService categorieService;
	private final UtilisateurService utilisateurService;
	private final AdresseService adresseService;

	public ArticlesServiceImpl(ArticleAVendreService articleAVendreService, CategorieService categorieService, UtilisateurService utilisateurService, AdresseService adresseService) {
		this.articleAVendreService = articleAVendreService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;
		this.adresseService = adresseService;
	}

	@Override
	public List<ArticleAVendre> findAll() {
		List<ArticleAVendre> articles = articleAVendreService.getAllArticles();

		articles.forEach(this::chargeCategorieDansArticle);
		articles.forEach(this::chargeUtilisateurDansArticle);

		return articles;
	}

	public void chargeCategorieDansArticle(ArticleAVendre article) {
		article.setCategorie(categorieService.getCategorieById(article.getCategorie().getId()));
	}

	public void chargeUtilisateurDansArticle(ArticleAVendre article) {
		Utilisateur vendeur = utilisateurService.getUtilisateur(article.getVendeur().getPseudo());
		chargeAdresseDansUtilisateur(vendeur);
		article.setVendeur(vendeur);
	}

	public void chargeAdresseDansUtilisateur(Utilisateur utilisateur) {
		utilisateur.setAdresse(adresseService.getAdresseById(utilisateur.getAdresse().getId()));
	}


}
