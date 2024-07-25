package fr.eni.site.bll.impl;

import fr.eni.site.bll.CategorieService;
import fr.eni.site.bo.Categorie;
import fr.eni.site.dal.CategorieDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {
	private final CategorieDAO categorieDAO;

	public CategorieServiceImpl(CategorieDAO categorieDAO) {
		this.categorieDAO = categorieDAO;
	}

	@Override
	public long createCategorie(Categorie categorie) {
		return categorieDAO.create(categorie);
	}

	@Override
	public Categorie getCategorieById(long id) {
		return categorieDAO.read(id);
	}

	@Override
	public List<Categorie> getAllCategories() {
		return categorieDAO.findAll();
	}
}
