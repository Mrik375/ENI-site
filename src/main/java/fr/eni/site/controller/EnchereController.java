package fr.eni.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class EnchereController {

	
	@GetMapping("/accueil")
	public String accueil() {
		return "index";
	}
	
	@GetMapping("/connexion")
	public String connexion() {
		return "view-connexion";
	}
	
	
	//TODO
	@PostMapping("/connexion")
	public String seConnecter() {
		return "index";//NON
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
