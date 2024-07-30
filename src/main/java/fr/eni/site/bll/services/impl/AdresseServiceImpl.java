package fr.eni.site.bll.services.impl;

import fr.eni.site.bll.services.AdresseService;
import fr.eni.site.bo.Adresse;
import fr.eni.site.dal.AdresseDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseServiceImpl implements AdresseService {

	private final AdresseDAO adresseDAO;

	public AdresseServiceImpl(AdresseDAO adresseDAO) {
		this.adresseDAO = adresseDAO;
	}

	@Override
	public long createAdresse(Adresse adresse) {
		return adresseDAO.create(adresse);
	}

	@Override
	public Adresse getById(long id) {
		return adresseDAO.read(id);
	}

	@Override
	public List<Adresse> getAll() {
		return adresseDAO.findAll();
	}

	@Override
	public void update(Adresse adresse) {
		adresseDAO.update(adresse);		
	}
}
