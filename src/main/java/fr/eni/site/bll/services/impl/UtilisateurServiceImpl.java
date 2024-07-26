package fr.eni.site.bll.services.impl;

import fr.eni.site.bll.services.UtilisateurService;
import fr.eni.site.bo.Utilisateur;
import fr.eni.site.dal.UtilisateurDAO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	private final UtilisateurDAO utilisateurDAO;
	private final PasswordEncoder passwordEncoder;

	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, PasswordEncoder passwordEncoder) {
		this.utilisateurDAO = utilisateurDAO;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void registerUtilisateur(Utilisateur utilisateur) throws Exception {
		if (getUtilisateurByPseudo(utilisateur.getPseudo()).isPresent()) {
			throw new Exception("Utilisateur avec ce pseudo existe déjà.");
		}
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
		try {
			utilisateurDAO.create(utilisateur);
		} catch (DuplicateKeyException e) {
			throw new Exception("Utilisateur avec ce pseudo existe déjà.", e);
		}
	}

	@Override
	public Optional<String> getUtilisateurByPseudo(String pseudo) {
		System.out.println("INSIDE getUtilisateurByPseudo()");
		return Optional.ofNullable(utilisateurDAO.readByPseudo(pseudo));
	}

	@Override
	public Optional<Utilisateur> getUtilisateur(String pseudo) {
		System.out.println("INSIDE getUtilisateurByPseudo()");
		return Optional.ofNullable(utilisateurDAO.read(pseudo));
	}

	@Override
	public List<Utilisateur> getAllUtilisateurs() {
		return utilisateurDAO.findAll();
	}

	@Override
	public Optional<Utilisateur> getCurrentUtilisateur() {
		return Optional.empty();
	}

//	@Override
//	public Optional<Utilisateur> getCurrentUtilisateur() {
//		return SecurityUtils.getCurrentUserLogin()
//				.flatMap(this::getUtilisateurByPseudo);
//	}
}
