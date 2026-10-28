/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sio.la2028.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import sio.la2028.model.Athlete;
import sio.la2028.model.Pays;

/**
 *
 * @author zakina
 */
public class DaoAthlete {
    
    private static final String SELECT_ATHLETE =
            "SELECT a.id AS a_id, a.nom AS a_nom, a.prenom AS a_prenom, " +
            "a.date_naissance AS a_date_naissance, a.rang AS a_rang, " +
            "p.id AS p_id, p.nom AS p_nom " +
            "FROM athlete a INNER JOIN pays p ON a.pays_id = p.id";

    public static ArrayList<Athlete> getLesAthletes(Connection cnx) throws SQLException {
        ArrayList<Athlete> lesAthletes = new ArrayList<>();
        String sql = SELECT_ATHLETE +
                " ORDER BY CASE WHEN a.rang IS NULL THEN 1 ELSE 0 END, a.rang, a.nom";
        try (PreparedStatement requete = cnx.prepareStatement(sql);
             ResultSet resultat = requete.executeQuery()) {
            while (resultat.next()) {
                lesAthletes.add(lireAthlete(resultat));
            }
        }
        return lesAthletes;
    }

    public static Athlete getAthleteById(Connection cnx, int idAthlete) throws SQLException {
        try (PreparedStatement requete = cnx.prepareStatement(SELECT_ATHLETE + " WHERE a.id = ?")) {
            requete.setInt(1, idAthlete);
            try (ResultSet resultat = requete.executeQuery()) {
                return resultat.next() ? lireAthlete(resultat) : null;
            }
        }
    }

    public static Athlete addAthlete(Connection cnx, Athlete athlete) throws SQLException {
        String sql = "INSERT INTO athlete (nom, pays_id, rang) VALUES (?, ?, ?)";
        int idGenere;
        try (PreparedStatement requete = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            requete.setString(1, athlete.getNom());
            requete.setInt(2, athlete.getPays().getId());
            requete.setInt(3, athlete.getRang());
            requete.executeUpdate();
            try (ResultSet keys = requete.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("La base n'a pas renvoyé l'identifiant de l'athlète créé.");
                }
                idGenere = keys.getInt(1);
            }
        }
        return getAthleteById(cnx, idGenere);
    }

    public static void modifierRang(Connection cnx, int idAthlete, int rang) throws SQLException {
        try (PreparedStatement requete = cnx.prepareStatement(
                "UPDATE athlete SET rang = ? WHERE id = ?")) {
            requete.setInt(1, rang);
            requete.setInt(2, idAthlete);
            if (requete.executeUpdate() == 0) {
                throw new SQLException("Aucun athlète trouvé pour l'identifiant " + idAthlete + ".");
            }
        }
    }

    private static Athlete lireAthlete(ResultSet resultat) throws SQLException {
        Athlete athlete = new Athlete();
        athlete.setId(resultat.getInt("a_id"));
        athlete.setNom(resultat.getString("a_nom"));
        athlete.setPrenom(resultat.getString("a_prenom"));
        Date dateNaissance = resultat.getDate("a_date_naissance");
        if (dateNaissance != null) {
            athlete.setDataNaissance(dateNaissance.toLocalDate().atStartOfDay());
        }
        int rang = resultat.getInt("a_rang");
        athlete.setRang(resultat.wasNull() ? null : rang);

        Pays pays = new Pays();
        pays.setId(resultat.getInt("p_id"));
        pays.setNom(resultat.getString("p_nom"));
        athlete.setPays(pays);
        return athlete;
    }
}
