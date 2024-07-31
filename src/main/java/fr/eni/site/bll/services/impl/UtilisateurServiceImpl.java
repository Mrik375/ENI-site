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
	public void create(Utilisateur utilisateur){
		if (exists(utilisateur.getPseudo())) {
			throw new IllegalArgumentException("Utilisateur avec ce pseudo existe déjà.");
		}

		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getPassword()));
		try {
			utilisateurDAO.create(utilisateur);
		} catch (DuplicateKeyException e) {
			throw new RuntimeException("Utilisateur avec ce pseudo existe déjà.", e);
		}
	}

	@Override
	public boolean exists(String pseudo) {
		return utilisateurDAO.exists(pseudo);
	}

	@Override
	public Utilisateur getByPseudo(String pseudo) {
		return Optional.ofNullable(utilisateurDAO.read(pseudo))
				.orElseThrow(() -> new NoSuchElementException("Utilisateur  " + pseudo + " non trouvé."));
	}

	@Override
	public List<Utilisateur> getAll() {
		return utilisateurDAO.findAll();
	}

	@Override
	public Optional<Utilisateur> getLogged() {
		return Optional.empty();
	}

	@Override
	public void update(Utilisateur utilisateur) {
		utilisateurDAO.update(utilisateur);
	}

	@Override
	public void updatePseudo(String pseudo, String oldPseudo) {
		utilisateurDAO.updatePseudo(pseudo, oldPseudo);
		
	}
	


//	@Override
//	public Optional<Utilisateur> getCurrentUtilisateur() {
//		return SecurityUtils.getCurrentUserLogin()
//				.flatMap(this::getUtilisateurByPseudo);
//	}
	
}
