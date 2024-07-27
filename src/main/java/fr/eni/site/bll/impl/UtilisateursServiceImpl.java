package fr.eni.site.bll.impl;

import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bll.services.AdresseService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.UtilisateurService;
import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur utilisateur = getUtilisateur(username);

		if (utilisateur == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return utilisateur;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void registerUtilisateur(Utilisateur utilisateur) throws Exception {
		utilisateur.getAdresse().setId(adresseService.createAdresse(utilisateur.getAdresse()));
		utilisateurService.create(utilisateur);
	}

	@Override
	public Utilisateur getUtilisateur(String pseudo) {
		Utilisateur utilisateur = utilisateurService.getByPseudo(pseudo);
		chargerDependancesUtilisateur(utilisateur);
		return utilisateur;
	}

	@Override
	public Adresse getAdresse(long id) {
		return adresseService.getById(id);
	}

	public void chargerDependancesUtilisateur(Utilisateur utilisateur) {
		chargeAdresseDansUtilisateur(utilisateur);
		chargeArticlesVendusDansUtilisateur(utilisateur);
	}

	public void chargeAdresseDansUtilisateur(Utilisateur utilisateur) {
		utilisateur.setAdresse(adresseService.getById(utilisateur.getAdresse().getId()));
	}

	public void chargeArticlesVendusDansUtilisateur(Utilisateur utilisateur) {
		utilisateur.setArticles(articleAVendreService.getByUtilisateur(utilisateur.getPseudo()));
	}

}
