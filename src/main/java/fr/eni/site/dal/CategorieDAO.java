package fr.eni.site.dal;

import fr.eni.site.bo.Categorie;

import java.util.List;

public interface CategorieDAO {
	long create(Categorie categorie);

	Categorie read(long noCategorie);

	List<Categorie> findAll();
}
