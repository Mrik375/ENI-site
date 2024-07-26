package fr.eni.site.bll.services;

import fr.eni.site.bo.Adresse;

import java.util.List;

public interface AdresseService {
	long createAdresse(Adresse adresse);

	Adresse getAdresseById(long id);

	List<Adresse> getAllAdresses();
}
