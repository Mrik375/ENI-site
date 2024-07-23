package fr.eni.site.bo;

import java.util.Objects;

public class Categorie {
    private long id;
    private String libelle;

    public Categorie() {
    }

    public Categorie(long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return getId() == categorie.getId() && Objects.equals(getLibelle(), categorie.getLibelle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLibelle());
    }
}
