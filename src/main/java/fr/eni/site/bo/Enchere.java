package fr.eni.site.bo;

import java.time.LocalDate;
import java.util.Objects;

public class Enchere {
    private LocalDate date;
    private int montant;
    private ArticleAVendre articleAVendre;
    private Utilisateur acquereur;

    public Enchere() {
    }

    public Enchere(LocalDate date, int montant, ArticleAVendre articleAVendre, Utilisateur acquereur) {
        this.date = date;
        this.montant = montant;
        this.articleAVendre = articleAVendre;
        this.acquereur = acquereur;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public ArticleAVendre getArticleAVendre() {
        return articleAVendre;
    }

    public void setArticleAVendre(ArticleAVendre articleAVendre) {
        this.articleAVendre = articleAVendre;
    }

    public Utilisateur getAcquereur() {
        return acquereur;
    }

    public void setAcquereur(Utilisateur acquereur) {
        this.acquereur = acquereur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enchere enchere = (Enchere) o;
        return getMontant() == enchere.getMontant() && Objects.equals(getDate(), enchere.getDate()) && Objects.equals(getArticleAVendre(), enchere.getArticleAVendre()) && Objects.equals(getAcquereur(), enchere.getAcquereur());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getMontant(), getArticleAVendre(), getAcquereur());
    }
}
