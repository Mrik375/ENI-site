package fr.eni.site.bll;

import fr.eni.site.bo.Adresse;

import java.util.List;

public interface AdresseService {
	void createAdresse(Adresse adresse);
	Adresse getAdresseById(long id);
	List<Adresse> getAllAdresses();
}
