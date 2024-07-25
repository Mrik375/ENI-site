package fr.eni.site.bll.impl;

import fr.eni.site.bll.EnchereService;
import fr.eni.site.bo.Enchere;
import fr.eni.site.dal.EnchereDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {
	private final EnchereDAO enchereDAO;

	public EnchereServiceImpl(EnchereDAO enchereDAO) {
		this.enchereDAO = enchereDAO;
	}

	@Override
	public void createEnchere(Enchere enchere) {
		enchereDAO.create(enchere);
	}

	@Override
	public Enchere getEnchere(String idUtilisateur, long noArticle, int montantEnchere) {
		return enchereDAO.read(idUtilisateur, noArticle, montantEnchere);
	}

	@Override
	public List<Enchere> getAllEncheres() {
		return enchereDAO.findAll();
	}

	@Override
	public List<Enchere> getEncheresByUtilisateur(String pseudo) {
		return enchereDAO.findByUtilisateur(pseudo);
	}

	@Override
	public List<Enchere> getEncheresByArticle(long noArticle) {
		return enchereDAO.findByArticle(noArticle);
	}

	@Override
	public Enchere getHighestEnchereForArticle(long noArticle) {
		return enchereDAO.findHighestEnchereForArticle(noArticle);
	}
}
