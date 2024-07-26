package fr.eni.site.bll.impl;

import fr.eni.site.bll.services.AdresseService;
import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.UtilisateurService;
import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UtilisateursServiceImpl implements UtilisateursService {

	private final UtilisateurService utilisateurService;
	private final AdresseService adresseService;
	private final ArticleAVendreService articleAVendreService;

	public UtilisateursServiceImpl(UtilisateurService utilisateurService, AdresseService adresseService, ArticleAVendreService articleAVendreService) {
		this.utilisateurService = utilisateurService;
		this.adresseService = adresseService;
		this.articleAVendreService = articleAVendreService;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void registerUtilisateur(Utilisateur utilisateur) throws Exception {
		utilisateur.getAdresse().setId(adresseService.createAdresse(utilisateur.getAdresse()));
		utilisateurService.registerUtilisateur(utilisateur);
	}

	@Override
	public Utilisateur getUtilisateur(String pseudo) {
		Utilisateur utilisateur = utilisateurService.getUtilisateur(pseudo);
		chargerDependancesUtilisateur(utilisateur);
		return utilisateur;
	}

	@Override
	public Adresse getAdresse(long id) {
		return adresseService.getAdresseById(id);
	}

	public void chargerDependancesUtilisateur(Utilisateur utilisateur) {
		chargeAdresseDansUtilisateur(utilisateur);
		chargeArticlesVendusDansUtilisateur(utilisateur);
	}

	public void chargeAdresseDansUtilisateur(Utilisateur utilisateur) {
		utilisateur.setAdresse(adresseService.getAdresseById(utilisateur.getAdresse().getId()));
	}

	public void chargeArticlesVendusDansUtilisateur(Utilisateur utilisateur) {
		utilisateur.setArticles(articleAVendreService.getArticlesByUtilisateur(utilisateur.getPseudo()));
	}
}
