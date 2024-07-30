package fr.eni.site.controller;

import fr.eni.site.bll.ArticlesService;
import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;
import fr.eni.site.bo.CategorieArticle;
import fr.eni.site.bo.Utilisateur;
import fr.eni.site.bo.groupes.Enregistrer;
import fr.eni.site.bo.groupes.Modifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static fr.eni.site.bo.ArticleStatus.*;

@Controller
public class EnchereController {
	private final UtilisateursService utilisateursService;
	private final ArticlesService articlesService;
	private final Validator validator;
	private List<ArticleAVendre> articles;

	public EnchereController(UtilisateursService utilisateursService, Validator validator, ArticlesService articlesService) {
		this.utilisateursService = utilisateursService;
		this.articlesService = articlesService;
		this.validator = validator;
	}

	@GetMapping({"/accueil", "/"})
	public String accueil(Model model) {
		// TODO : remplacer getAllArticles() par getAllActiveArticles()
		List<ArticleAVendre> articles = articlesService.getAllArticles();
		model.addAttribute("articles", articles);
		return "index";
	}

	@PostMapping("/filtre")
	public String filtre(Model model, Principal principal,
						 @RequestParam(name = "nom_article", required = false) String nomArticle,
						 @RequestParam(name = "categorie", required = false) CategorieArticle categorie,
						 @RequestParam(name = "achats_dropdown", required = false) String achatsDropdown,
						 @RequestParam(name = "ventes_dropdown", required = false) String selectVentes,
						 @RequestParam(name = "section", required = false) String section
	) {
		if (section == null) {
			articles = filtre(nomArticle, categorie);
		} else {
			switch (section) {
				case "achats":
					articles = filtreAchats(model, principal.getName(), achatsDropdown, nomArticle, categorie);
					break;
				case "ventes":
					articles = filtreVentes(model, principal.getName(), selectVentes, nomArticle, categorie);
					break;
				default:
					articles = filtre(nomArticle, categorie);
			}
		}
		model.addAttribute("ventes", true);
		model.addAttribute("nomArticle", nomArticle);
		model.addAttribute("selectedCategory", categorie);
		model.addAttribute("articles", articles);
		model.addAttribute("section", section);
		return "index";
	}

	private List<ArticleAVendre> filtre(String nomArticle, CategorieArticle categorie) {
		return articlesService.getArticlesFiltre(new ArticleStatus[]{EN_COURS}, null, nomArticle, categorie);
	}

	public List<ArticleAVendre> filtreAchats(Model model,
											 String pseudo,
											 String select,
											 String nomArticle,
											 CategorieArticle categorie
	) {
		switch (select) {
			case "encheres_ouvertes":
				articles = articlesService.getArticlesFiltre(new ArticleStatus[]{EN_COURS}, null, nomArticle, categorie);
				break;
			case "mes_encheres_en_cours":
				//articles = encheresService.getMesEncheresEnCours(pseudo, nomArticle, categorie);
				break;
			case "mes_encheres_remportees":
				//articles = encheresService.getMesEncheresRemportees(pseudo, nomArticle, categorie);
				break;
			default:
				articles = articlesService.getArticlesFiltre(new ArticleStatus[]{EN_COURS}, null, nomArticle, categorie);
		}
		model.addAttribute("select", select);
		return articles;
	}

	public List<ArticleAVendre> filtreVentes(Model model,
											 String pseudo,
											 String select,
											 String nomArticle,
											 CategorieArticle categorie
	) {
		switch (select) {
			case "mes_ventes_en_cours":
				articles = articlesService.getArticlesFiltre(new ArticleStatus[]{EN_COURS}, pseudo, nomArticle, categorie);
				break;
			case "mes_ventes_non_debutees":
				articles = articlesService.getArticlesFiltre(new ArticleStatus[]{PAS_COMMENCEE}, pseudo, nomArticle, categorie);
				break;
			case "mes_ventes_terminees":
				articles = articlesService.getArticlesFiltre(new ArticleStatus[]{CLOTUREE, LIVREE}, pseudo, nomArticle, categorie);
				break;
			default:
				articles = articlesService.getArticlesFiltre(new ArticleStatus[]{EN_COURS}, pseudo, nomArticle, categorie);
		}
		model.addAttribute("select", select);
		return articles;
	}

	@GetMapping("/connexion")
	public String connexion() {
		return "view-connexion";
	}


	//CREER NOUVEAU COMPTE(Page5 PDF - Leo)
	@GetMapping("/creercompte")
	public String creerCompte(Model model) {
		profilModel(model, new Utilisateur(), true, null);
		return "view-creer-compte";
	}

	@PostMapping("/creercompte")
	public String creerCompte(@Validated(Enregistrer.class) @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult bindingResult, Model model,
							  @RequestParam(name = "confirmMDP") String confirmMDP) {
		if (bindingResult.hasErrors()) {
			profilModel(model, utilisateur, true, null);
			return "view-creer-compte";
		} else {
			try {
				utilisateursService.registerUtilisateur(utilisateur);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/connexion";
		}
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
		try {
			Utilisateur utilisateur = utilisateursService.getUtilisateur(principal.getName());
			updateField(utilisateur, params);
			BindingResult bindingResult = valider(utilisateur, Modifier.class);

			if (bindingResult.hasErrors()) {
				model.addAttribute("org.springframework.validation.BindingResult.utilisateur", bindingResult);
				profilModel(model, utilisateur, true, params.get("field"));
				return "view-profil";
			}
			// Enregistrer les modifications en base de données
			utilisateursService.updateUtilisateur(utilisateur);
			return "redirect:/profil";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Une erreur s'est produite lors de la mise à jour du profil. Veuillez réessayer.");
			return "view-profil";
		}
	}

	private void profilModel(Model model, Utilisateur utilisateur, boolean editable, String editField) {
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("editable", editable);
		model.addAttribute("editField", editField);
	}


	private BindingResult valider(Utilisateur utilisateur, Class<?> validationGroup) {
		DataBinder dataBinder = new DataBinder(utilisateur);
		dataBinder.setValidator(validator);
		dataBinder.validate(validationGroup);
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
