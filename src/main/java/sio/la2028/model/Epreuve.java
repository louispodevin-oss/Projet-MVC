package sio.la2028.model;

import java.util.List;

public class Epreuve {
    private int id;
    private String nom;
    private List<Sport> sport;

    public Epreuve() {
    }

    public Epreuve(int id, String nom, List<Sport> sport) {
        this.id = id;
        this.nom = nom;
        this.sport = sport;
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

    public List<Sport> getSport() {
        return sport;
    }

    public void setSport(List<Sport> sport) {
        this.sport = sport;
    }
}
