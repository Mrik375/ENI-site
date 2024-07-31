package fr.eni.site.bll.impl;

import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bll.services.AdresseService;
import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.UtilisateurService;
import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;
import fr.eni.site.util.SecurityUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) {
		utilisateurService.update(utilisateur);
		updatePrincipal(utilisateur);
		
	}

	public void updatePrincipal(Utilisateur utilisateur) {
		Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();

		if (currentUserLogin.isPresent() && currentUserLogin.get().equals(utilisateur.getPseudo())) {
			UserDetails updatedUserDetails = loadUserByUsername(utilisateur.getPseudo());

			Authentication newAuth = new UsernamePasswordAuthenticationToken(
					updatedUserDetails,
					null,
					updatedUserDetails.getAuthorities()
			);

			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}
	}
}