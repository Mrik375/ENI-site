package fr.eni.site.bll.impl;

import fr.eni.site.bll.ArticlesService;
import fr.eni.site.bll.EncheresService;
import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.UtilisateurService;

public class EncheresServiceImpl implements EncheresService {

	private final EncheresService encheresService;
	private final UtilisateursService utilisateursService;
	private final ArticlesService articlesService;

	public EncheresServiceImpl(EncheresService encheresService, UtilisateursService utilisateursService, ArticlesService articlesService) {
		this.encheresService = encheresService;
		this.utilisateursService = utilisateursService;
		this.articlesService = articlesService;
	}
}
