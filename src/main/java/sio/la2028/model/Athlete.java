/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sio.la2028.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author zakina
 */
public class Athlete {
    
    private int id;
    private String nom ;
    private Pays pays ;
    private LocalDateTime dataNaissance;
    private String prenom;
    private List<Epreuve> epreuves;
    private Integer rang;

    public Athlete() {
    }

    public Athlete(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Athlete(int id, String nom, String prenom, Pays pays, LocalDateTime dateNaissance, List<Epreuve> epreuves) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.pays = pays;
        this.dataNaissance = dateNaissance;
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

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public LocalDateTime getDataNaissance() {
        return dataNaissance;
    }

    public void setDataNaissance(LocalDateTime dataNaissance) {
        this.dataNaissance = dataNaissance;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }
}
