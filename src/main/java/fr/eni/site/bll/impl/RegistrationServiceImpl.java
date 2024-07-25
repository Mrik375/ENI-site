package fr.eni.site.bll.impl;

import fr.eni.site.bll.AdresseService;
import fr.eni.site.bll.RegistrationService;
import fr.eni.site.bll.UtilisateurService;
import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private final UtilisateurService utilisateurService;
	private final AdresseService adresseService;

	public RegistrationServiceImpl(UtilisateurService utilisateurService, AdresseService adresseService) {
		this.utilisateurService = utilisateurService;
		this.adresseService = adresseService;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void registerUtilisateur(Utilisateur utilisateur, Adresse adresse) throws Exception {
		utilisateur.setAdresse(adresseService.createAdresse(adresse));
		utilisateurService.registerUtilisateur(utilisateur);
	}
}
