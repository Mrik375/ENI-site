package fr.eni.site.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.eni.site.bo.groupes.Enregistrer;
import fr.eni.site.bo.groupes.Modifier;
import jakarta.validation.constraints.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Utilisateur implements UserDetails {
	
	@NotBlank(groups = {Enregistrer.class, Modifier.class})
	@Size(min = 3, max = 50)
	@Pattern(regexp = "^[\\w_]+$", groups = {Enregistrer.class, Modifier.class})
	private String pseudo;
	
	@NotBlank(groups = {Enregistrer.class, Modifier.class})
	@Size(min = 3, max = 50, groups = {Enregistrer.class, Modifier.class})
	private String nom;
	
	@NotBlank(groups = {Enregistrer.class, Modifier.class})
	@Size(min = 3, max = 50, groups = {Enregistrer.class, Modifier.class})
	private String prenom;
	
	@NotBlank(groups = {Enregistrer.class, Modifier.class})
	@Email(groups = {Enregistrer.class, Modifier.class})
	private String email;
	
	private String telephone;
	
	@NotBlank(groups = Enregistrer.class)
	@Pattern(regexp = "^[\\w_]+$", groups = Enregistrer.class)
	private String motDePasse;
	private int credit;
	private boolean administrateur;
	private Adresse adresse;
	private List<ArticleAVendre> articles;

	public Utilisateur() {
		this.adresse = new Adresse();
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String motDePasse, int credit, boolean administrateur, Adresse adresse, List<ArticleAVendre> articles) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
		this.adresse = adresse;
		this.articles = articles;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<ArticleAVendre> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleAVendre> articles) {
		this.articles = articles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Utilisateur that = (Utilisateur) o;
		return getCredit() == that.getCredit() && isAdministrateur() == that.isAdministrateur() && Objects.equals(getPseudo(), that.getPseudo()) && Objects.equals(getNom(), that.getNom()) && Objects.equals(getPrenom(), that.getPrenom()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getTelephone(), that.getTelephone()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getAdresse(), that.getAdresse()) && Objects.equals(getArticles(), that.getArticles());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPseudo(), getNom(), getPrenom(), getEmail(), getTelephone(), getPassword(), getCredit(), isAdministrateur(), getAdresse(), getArticles());
	}

	@Override
	public String toString() {
		return "Utilisateur{" +
				"adresse=" + adresse +
				", administrateur=" + administrateur +
				", credit=" + credit +
				", motDePasse='" + motDePasse + '\'' +
				", telephone='" + telephone + '\'' +
				", email='" + email + '\'' +
				", prenom='" + prenom + '\'' +
				", nom='" + nom + '\'' +
				", pseudo='" + pseudo + '\'' +
				'}';
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (administrateur) {
			return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}

	@Override
	public String getUsername() {
		return pseudo;
	}

	@Override
	public String getPassword() {
		return motDePasse;
	}

}

