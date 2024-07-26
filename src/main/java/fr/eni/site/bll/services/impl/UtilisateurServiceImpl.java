package fr.eni.site.bll.services.impl;

import fr.eni.site.bll.services.UtilisateurService;
import fr.eni.site.bo.Utilisateur;
import fr.eni.site.dal.UtilisateurDAO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
	public void registerUtilisateur(Utilisateur utilisateur){
		if (utilisateurExists(utilisateur.getPseudo())) {
			throw new IllegalArgumentException("Utilisateur avec ce pseudo existe déjà.");
		}

		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
		try {
			utilisateurDAO.create(utilisateur);
		} catch (DuplicateKeyException e) {
			throw new RuntimeException("Utilisateur avec ce pseudo existe déjà.", e);
		}
	}

	@Override
	public boolean utilisateurExists(String pseudo) {
		return utilisateurDAO.existsByPseudo(pseudo);
	}

	@Override
	public Utilisateur getUtilisateur(String pseudo) {
		System.out.println("INSIDE getUtilisateurByPseudo()");
		return Optional.ofNullable(utilisateurDAO.read(pseudo))
				.orElseThrow(() -> new NoSuchElementException("Utilisateur  " + pseudo + " non trouvé."));
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
