package fr.eni.site.bll;

import fr.eni.site.bo.Categorie;

import java.util.List;

public interface CategorieService {
	long createCategorie(Categorie categorie);

	Categorie getCategorieById(long id);

	List<Categorie> getAllCategories();
}
