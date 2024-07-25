package fr.eni.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.site.bll.RegistrationService;
import fr.eni.site.bo.Utilisateur;
import fr.eni.site.bo.Adresse;
@Controller

public class EnchereController {
	private final RegistrationService registrationService;
	
	public EnchereController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	@GetMapping("/accueil")
	public String accueil() {
		return "index";
	}
	
	@GetMapping("/connexion")
	public String connexion() {
		return "view-connexion";
	}
	
	
	
	//CREER NOUVEAU COMPTE(Page5 PDF - Leo)
	@GetMapping("/creercompte")
	public String creerCompte() {
		return "view-creer-compte";
	}
	
	@PostMapping("/creercompte")
	public String creerCompte(@RequestParam(name="pseudo") String pseudo,
							@RequestParam(name="prenom") String prenom,
							@RequestParam(name="nom") String nom,
							@RequestParam(name="email") String email,
							@RequestParam(name="telephone") String telephone,
							@RequestParam(name="motDePasse") String motDePasse,
							@RequestParam(name="confirmMDP") String confirmMDP,
							@RequestParam(name="rue") String rue,
							@RequestParam(name="ville") String ville,
							@RequestParam(name="codePostal") String codePostal){
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPseudo(pseudo);
		utilisateur.setPrenom(prenom);
		utilisateur.setNom(nom);
		utilisateur.setEmail(email);
		utilisateur.setTelephone(telephone);
		utilisateur.setMotDePasse(motDePasse);
		
		Adresse adresse = new Adresse();
		adresse.setRue(rue);
		adresse.setCodePostal(codePostal);
		adresse.setVille(ville);
		
		try {
			registrationService.registerUtilisateur(utilisateur, adresse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return"redirect:/connexion";
	}
	
	@GetMapping("/vendre")
	public String vendreArticle() {
		return "view-vendre";
	}
	
	@GetMapping("/profil")
	public String profil() {
		return "view-profil";
	}
}
