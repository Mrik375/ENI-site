package fr.eni.site.dal;

import fr.eni.site.bo.Adresse;

import java.util.List;

public interface AdresseDAO {
	long create(Adresse adresse);

	Adresse read(long adresseId);

	List<Adresse> findAll();
}
