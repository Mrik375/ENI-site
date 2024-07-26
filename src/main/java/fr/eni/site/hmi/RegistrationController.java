package fr.eni.site.hmi;

import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bll.services.CategorieService;
import fr.eni.site.bll.UtilisateursService;
import fr.eni.site.bo.Utilisateur;
import fr.eni.site.bo.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

	private final JdbcTemplate jdbcTemplate;

	private final UtilisateursService utilisateursService;
	private final ArticleAVendreService articleAVendreService;
	private final CategorieService categorieService;

	@Autowired
	public RegistrationController(JdbcTemplate jdbcTemplate, UtilisateursService utilisateursService, ArticleAVendreService articleAVendreService, CategorieService categorieService) {
		this.jdbcTemplate = jdbcTemplate;
		this.utilisateursService = utilisateursService;
		this.articleAVendreService = articleAVendreService;
		this.categorieService = categorieService;
	}

	@RequestMapping("/register")
	public String showRegistrationPage() {
		articleAVendreService.getAllActive().forEach(System.out::println);
		categorieService.getAll().forEach(System.out::println);


//		utilisateurService.getAllUtilisateurs().forEach(System.out::println);
		return "register"; // name of the HTML file (register.html)
	}

	@PostMapping("/register")
	public String registerUtilisateur(@RequestParam String pseudo,
											@RequestParam String nom,
											@RequestParam String prenom,
											@RequestParam String email,
											@RequestParam(required = false) String telephone,
											@RequestParam String mot_de_passe,
											@RequestParam String rue,
											@RequestParam String code_postal,
											@RequestParam String ville) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPseudo(pseudo);
		utilisateur.setNom(nom);
		utilisateur.setPrenom(prenom);
		utilisateur.setEmail(email);
		utilisateur.setTelephone(telephone);
		utilisateur.setMotDePasse(mot_de_passe);


		Adresse adresse = new Adresse();
		adresse.setRue(rue);
		adresse.setCodePostal(code_postal);
		adresse.setVille(ville);

		System.out.println(utilisateur);

		try {
			utilisateursService.registerUtilisateur(utilisateur);
		} catch (Exception e) {
		}
		return "redirect:/register"; // redirect to a success page
	}
}

