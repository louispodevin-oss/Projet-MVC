package sio.la2028.database;

import sio.la2028.model.Epreuve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoEpreuve {

    Connection cnx;
    static PreparedStatement requeteSql = null;
    static ResultSet resultatRequete = null;

    public static ArrayList<Epreuve> getLesEpreuves(Connection cnx) {
        ArrayList<Epreuve> lesEpreuves = new ArrayList<>();

        String sql = "SELECT e.id AS id, e.nom AS epreuve_nom, s.nom AS sport_nom " +
                "FROM epreuve AS e " +
                "INNER JOIN sport AS s ON e.sport_id = s.id";

        try (PreparedStatement requeteSql = cnx.prepareStatement(sql);
             ResultSet resultatRequete = requeteSql.executeQuery()) {

            while (resultatRequete.next()) {
                Epreuve e = new Epreuve();

                e.setId(resultatRequete.getInt("id"));
                e.setNom(resultatRequete.getString("epreuve_nom"));

                lesEpreuves.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("La requete de getLesEpreuves a genere une erreur");
        }

        return lesEpreuves;
    }
}
