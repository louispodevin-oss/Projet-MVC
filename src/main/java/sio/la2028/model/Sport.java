package sio.la2028.model;

import java.util.List;

public class Sport {
    private int id;
    private String nom;
    private List<Epreuve> epreuves;

    public Sport(int id, String nom, List<Epreuve> epreuves) {
        this.id = id;
        this.nom = nom;
        this.epreuves = epreuves;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Epreuve> getEpreuves() {
        return epreuves;
    }

    public void setEpreuves(List<Epreuve> epreuves) {
        this.epreuves = epreuves;
    }
}
