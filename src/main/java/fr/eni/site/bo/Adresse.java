package fr.eni.site.bo;

import java.util.Objects;

public class Adresse {
    private long id;
    
    
    private String rue;
    
    
    private String codePostal;
    
    
    private String ville;

    @Override
    public String toString() {
        return "Adresse{" +
                "id=" + id +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }

    public Adresse() {
    }

    public Adresse(long id, String rue, String codePostal, String ville) {
        this.id = id;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return getId() == adresse.getId() && Objects.equals(getRue(), adresse.getRue()) && Objects.equals(getCodePostal(), adresse.getCodePostal()) && Objects.equals(getVille(), adresse.getVille());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRue(), getCodePostal(), getVille());
    }

    // la table a un champ adresse_eni qui est un booléen, mais je ne connais pas son utilité
    public Object isAdresseEni() {
        return false;
    }
}
