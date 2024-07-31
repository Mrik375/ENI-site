package fr.eni.site.bll.impl;

import org.springframework.stereotype.Service;

import fr.eni.site.bll.ArticlesService;
import fr.eni.site.bll.EncheresService;
import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.EnchereService;
import fr.eni.site.bll.services.UtilisateurService;

@Service
public class EncheresServiceImpl implements EncheresService {

	private final EnchereService enchereService;
	private final UtilisateursService utilisateursService;
	private final ArticlesService articlesService;

	public EncheresServiceImpl(EnchereService enchereService, UtilisateursService utilisateursService, ArticlesService articlesService) {
		this.enchereService = enchereService;
		this.utilisateursService = utilisateursService;
		this.articlesService = articlesService;
	}

}
