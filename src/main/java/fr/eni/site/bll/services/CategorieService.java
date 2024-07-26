package fr.eni.site.bll.services;

import fr.eni.site.bo.Categorie;

import java.util.List;

public interface CategorieService {
	long create(Categorie categorie);

	Categorie getById(long id);

	List<Categorie> getAll();
}
