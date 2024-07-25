package fr.eni.site.bo;

import java.time.LocalDate;
import java.util.Objects;

public class Enchere {
	private String acquereurId;
	private long articleAVendreId;
	private int montant;
	private LocalDate date;

	public Enchere() {
	}

	public Enchere(String acquereurId, long articleAVendreId, int montant, LocalDate date) {
		this.acquereurId = acquereurId;
		this.articleAVendreId = articleAVendreId;
		this.montant = montant;
		this.date = date;
	}

	public String getAcquereurId() {
		return acquereurId;
	}

	public void setAcquereurId(String acquereurId) {
		this.acquereurId = acquereurId;
	}

	public long getArticleAVendreId() {
		return articleAVendreId;
	}

	public void setArticleAVendreId(long articleAVendreId) {
		this.articleAVendreId = articleAVendreId;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Enchere enchere = (Enchere) o;
		return getArticleAVendreId() == enchere.getArticleAVendreId() && getMontant() == enchere.getMontant() && Objects.equals(getAcquereurId(), enchere.getAcquereurId()) && Objects.equals(getDate(), enchere.getDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAcquereurId(), getArticleAVendreId(), getMontant(), getDate());
	}
}
