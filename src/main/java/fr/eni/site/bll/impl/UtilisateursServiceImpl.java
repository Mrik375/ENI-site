package fr.eni.site.bll.impl;

import fr.eni.site.bll.services.AdresseService;
import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bll.services.UtilisateurService;
import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilisateursServiceImpl implements UtilisateursService {

	private final UtilisateurService utilisateurService;
	private final AdresseService adresseService;

	public UtilisateursServiceImpl(UtilisateurService utilisateurService, AdresseService adresseService) {
		this.utilisateurService = utilisateurService;
		this.adresseService = adresseService;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void registerUtilisateur(Utilisateur utilisateur) throws Exception {
		utilisateur.getAdresse().setId(adresseService.createAdresse(utilisateur.getAdresse()));
		utilisateurService.registerUtilisateur(utilisateur);
	}
}
