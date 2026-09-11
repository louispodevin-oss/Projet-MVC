package sio.la2028.model;

import java.util.List;

public class Epreuve {
    private int id;
    private String nom;
    private List<Athlete> participants;

    public Epreuve() {
    }

    public Epreuve(int id, String nom, List<Athlete> participants) {
        this.id = id;
        this.nom = nom;
        this.participants = participants;
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

    public List<Athlete> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Athlete> participants) {
        this.participants = participants;
    }
}
