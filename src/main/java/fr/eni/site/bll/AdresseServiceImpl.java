package fr.eni.site.bll;

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
	public Adresse getAdresseById(long id) {
		return adresseDAO.read(id);
	}

	@Override
	public List<Adresse> getAllAdresses() {
		return adresseDAO.findAll();
	}
}
