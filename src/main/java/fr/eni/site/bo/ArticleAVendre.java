package fr.eni.site.bo;

import java.time.LocalDate;
import java.util.Objects;

public class ArticleAVendre {
	private long id;
	private String nom;
	private String description;
	private Integer photo;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int statutEnchere;
	private int prixInitial;
	private int prixVente;
	private String vendeurId;
	private long adresseRetraitId;
	private long categorieId;

	public ArticleAVendre() {
	}

	public ArticleAVendre(long id, String nom, String description, Integer photo, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int statutEnchere, int prixInitial, int prixVente, String vendeurId, long adresseRetraitId, long categorieId) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.photo = photo;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.statutEnchere = statutEnchere;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.vendeurId = vendeurId;
		this.adresseRetraitId = adresseRetraitId;
		this.categorieId = categorieId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPhoto() {
		return photo;
	}

	public void setPhoto(Integer photo) {
		this.photo = photo;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getStatutEnchere() {
		return statutEnchere;
	}

	public void setStatutEnchere(int statutEnchere) {
		this.statutEnchere = statutEnchere;
	}

	public int getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public String getVendeurId() {
		return vendeurId;
	}

	public void setVendeurId(String vendeur) {
		this.vendeurId = vendeur;
	}

	public long getAdresseRetraitId() {
		return adresseRetraitId;
	}

	public void setAdresseRetraitId(long retrait) {
		this.adresseRetraitId = retrait;
	}

	public long getCategorieId() {
		return categorieId;
	}

	public void setCategorieId(long categorie) {
		this.categorieId = categorie;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ArticleAVendre that = (ArticleAVendre) o;
		return getId() == that.getId() && getStatutEnchere() == that.getStatutEnchere() && getPrixInitial() == that.getPrixInitial() && getPrixVente() == that.getPrixVente() && Objects.equals(getNom(), that.getNom()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPhoto(), that.getPhoto()) && Objects.equals(getDateDebutEncheres(), that.getDateDebutEncheres()) && Objects.equals(getDateFinEncheres(), that.getDateFinEncheres()) && Objects.equals(getVendeurId(), that.getVendeurId()) && Objects.equals(getAdresseRetraitId(), that.getAdresseRetraitId()) && Objects.equals(getCategorieId(), that.getCategorieId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getNom(), getDescription(), getPhoto(), getDateDebutEncheres(), getDateFinEncheres(), getStatutEnchere(), getPrixInitial(), getPrixVente(), getVendeurId(), getAdresseRetraitId(), getCategorieId());
	}

	@Override
	public String toString() {
		return "ArticleAVendre{" +
				"id=" + id +
				", nom='" + nom + '\'' +
				", description='" + description + '\'' +
				", photo=" + photo +
				", dateDebutEncheres=" + dateDebutEncheres +
				", dateFinEncheres=" + dateFinEncheres +
				", statutEnchere=" + statutEnchere +
				", prixInitial=" + prixInitial +
				", prixVente=" + prixVente +
				", vendeurId='" + vendeurId + '\'' +
				", adresseRetraitId=" + adresseRetraitId +
				", categorieId=" + categorieId +
				'}';
	}
}
