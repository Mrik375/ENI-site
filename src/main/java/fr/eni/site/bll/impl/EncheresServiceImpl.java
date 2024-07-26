package fr.eni.site.bll.impl;

import fr.eni.site.bll.EncheresService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.UtilisateurService;

public class EncheresServiceImpl implements EncheresService {

	private final EncheresService encheresService;
	private final UtilisateurService utilisateurService;
	private final ArticleAVendreService articleAVendreService;

	public EncheresServiceImpl(EncheresService encheresService, UtilisateurService utilisateurService, ArticleAVendreService articleAVendreService) {
		this.encheresService = encheresService;
		this.utilisateurService = utilisateurService;
		this.articleAVendreService = articleAVendreService;
	}
}
