package fr.eni.site.controller;

import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller

public class EnchereController {
	private final UtilisateursService utilisateursService;

	public EnchereController(UtilisateursService utilisateursService) {
		this.utilisateursService = utilisateursService;
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
	public String creerCompte(@RequestParam(name = "pseudo") String pseudo,
							  @RequestParam(name = "prenom") String prenom,
							  @RequestParam(name = "nom") String nom,
							  @RequestParam(name = "email") String email,
							  @RequestParam(name = "telephone") String telephone,
							  @RequestParam(name = "motDePasse") String motDePasse,
							  @RequestParam(name = "confirmMDP") String confirmMDP,
							  @RequestParam(name = "rue") String rue,
							  @RequestParam(name = "ville") String ville,
							  @RequestParam(name = "codePostal") String codePostal) {
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

		utilisateur.setAdresse(adresse);
		try {
			utilisateursService.registerUtilisateur(utilisateur);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/connexion";
	}

	@GetMapping("/vendre")
	public String vendreArticle() {
		return "view-vendre";
	}

	@GetMapping("/profil/{pseudo}")
	public String profil(@PathVariable("pseudo") String pseudo, Model model, Principal principal) {
		if (principal != null && principal.getName().equals(pseudo)) {
			return "redirect:/profil";
		}
		Utilisateur utilisateur = utilisateursService.getUtilisateur(pseudo);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("editable", false);
		return "view-profil";
	}

	@GetMapping("/profil")
	public String monProfil(Model model) {
		model.addAttribute("editable", true);
		model.addAttribute("editField", null);
		return "view-profil";
	}

	@PostMapping("/profil")
	public String modifierProfil(Model model, @RequestParam(value = "editField") String editField) {
		model.addAttribute("editable", true);
		model.addAttribute("editField", editField);
		return "view-profil";
	}

	@PostMapping("/profil/modifier")
	public String enregistrerProfil(Model model, @RequestParam(value = "action") String action) {
		if (action.equals("modifier")) {
			// TODO logique de modification du profil
			return "redirect:/profil";
		} else if (action.equals("annuler")) {
			return "redirect:/profil";
		}
		return "redirect:/profil";
	}
}
