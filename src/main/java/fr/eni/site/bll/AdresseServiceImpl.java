package fr.eni.site.bll;

import fr.eni.site.bo.Adresse;
import fr.eni.site.dal.AdresseDAO;

import java.util.List;

public class AdresseServiceImpl implements AdresseService {

	private final AdresseDAO adresseDAO;

	public AdresseServiceImpl(AdresseDAO adresseDAO) {
		this.adresseDAO = adresseDAO;
	}

	@Override
	public void createAdresse(Adresse adresse) {
		adresseDAO.create(adresse);
	}

	@Override
	public Adresse getAdresseById(long id) {
		return adresseDAO.read(id);
	}

	@Override
	public List<Adresse> getAllAdresses() {
		return adresseDAO.findAll();
	}
}
