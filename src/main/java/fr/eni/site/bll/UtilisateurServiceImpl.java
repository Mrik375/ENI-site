package fr.eni.site.bll;

import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;
import fr.eni.site.dal.UtilisateurDAO;
import fr.eni.site.util.SecurityUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	private final UtilisateurDAO utilisateurDAO;
	private final AdresseService adresseService;
	private final PasswordEncoder passwordEncoder;

	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseService adresseService, PasswordEncoder passwordEncoder) {
		this.utilisateurDAO = utilisateurDAO;
		this.adresseService = adresseService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void registerUtilisateur(Utilisateur utilisateur) throws Exception {
		if (getUtilisateurByPseudo(utilisateur.getPseudo()).isPresent()) {
			throw new Exception("Utilisateur avec ce pseudo existe déjà.");
		}

		Adresse adresse = utilisateur.getAdresse();
		if (adresse != null && adresse.getId() == 0) {
			adresseService.createAdresse(adresse);
		}

		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

		try {
			utilisateurDAO.create(utilisateur);
		} catch (DuplicateKeyException e) {
			throw new Exception("Utilisateur avec ce pseudo existe déjà.", e);
		}
	}

	@Override
	public Optional<Utilisateur> getUtilisateurByPseudo(String pseudo) {
		return Optional.ofNullable(utilisateurDAO.readByPseudo(pseudo));
	}

	@Override
	public List<Utilisateur> getAllUtilisateurs() {
		return utilisateurDAO.findAll();
	}

	@Override
	public Optional<Utilisateur> getCurrentUtilisateur() {
		return SecurityUtils.getCurrentUserLogin()
				.flatMap(this::getUtilisateurByPseudo);
	}
}
