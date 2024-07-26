package fr.eni.site.bll.impl;

import fr.eni.site.bll.ArticlesService;
import fr.eni.site.bll.services.AdresseService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.CategorieService;
import fr.eni.site.bll.services.UtilisateurService;

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
}
