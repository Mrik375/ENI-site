package fr.eni.site.controller;

import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller

public class EnchereController {
	private final UtilisateursService utilisateursService;
	private final Validator validator;

	public EnchereController(UtilisateursService utilisateursService, Validator validator) {
		this.utilisateursService = utilisateursService;
		this.validator = validator;
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
		profilModel(model, utilisateur, false, null);
		return "view-profil";
	}

	@GetMapping("/profil")
	public String monProfil(Model model) {
		profilModel(model, new Utilisateur(), true, null);
		return "view-profil";
	}

	@PostMapping("/profil")
	public String modifierProfil(Model model, @RequestParam(value = "editField") String editField) {
		profilModel(model, new Utilisateur(), true, editField);
		return "view-profil";
	}

	@PostMapping("/profil/modifier")
	public String enregistrerProfil(Principal principal, Model model,
									@RequestParam Map<String, String> params) {
		if (params.get("action").equals("modifier")) {
			return handleModifierAction(principal, model, params);
		} else if (params.get("action").equals("annuler")) {
			return "redirect:/profil";
		}
		return "redirect:/profil";
	}

	private String handleModifierAction(Principal principal, Model model,
										@RequestParam Map<String, String> params) {
		Utilisateur utilisateur = utilisateursService.getUtilisateur(principal.getName());
		updateField(utilisateur, params);
		BindingResult bindingResult = valider(utilisateur);

		if (bindingResult.hasErrors()) {
			model.addAttribute("org.springframework.validation.BindingResult.utilisateur", bindingResult);
			profilModel(model, utilisateur, true, params.get("field"));
			return "view-profil";
		}
		// TODO appel au service de modification du profil
		return "redirect:/profil";
	}

	private void profilModel(Model model, Utilisateur utilisateur, boolean editable, String editField) {
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("editable", editable);
		model.addAttribute("editField", editField);
	}


	private BindingResult valider(Utilisateur utilisateur) {
		DataBinder dataBinder = new DataBinder(utilisateur);
		dataBinder.setValidator(validator);
		dataBinder.validate();
		return dataBinder.getBindingResult();
	}

	private void updateField(Utilisateur utilisateur, Map<String, String> params) {
		String[] fieldParts = params.get("field").split("\\.");
		if (fieldParts.length == 1) {
			switch (params.get("field")) {
				case "pseudo":
					utilisateur.setPseudo(params.get("pseudo"));
					break;
				case "nom":
					utilisateur.setNom(params.get("nom"));
					break;
				case "prenom":
					utilisateur.setPrenom(params.get("prenom"));
					break;
				case "email":
					utilisateur.setEmail(params.get("email"));
					break;
				case "telephone":
					utilisateur.setTelephone(params.get("telephone"));
					break;
			}
		} else if (fieldParts.length == 2 && fieldParts[0].equals("adresse")) {
			switch (fieldParts[1]) {
				case "rue":
					utilisateur.getAdresse().setRue(params.get("adresse.rue"));
					break;
				case "ville":
					utilisateur.getAdresse().setVille(params.get("adresse.ville"));
					break;
				case "codePostal":
					utilisateur.getAdresse().setCodePostal(params.get("adresse.codePostal"));
					break;
			}
		}
	}
}
