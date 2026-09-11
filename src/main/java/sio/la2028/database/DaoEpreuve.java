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
        try {
            requeteSql = cnx.prepareStatement("select * from epreuve");
            resultatRequete = requeteSql.executeQuery();

            while (resultatRequete.next()) {
                Epreuve e = new Epreuve();

                e.setId(resultatRequete.getInt("id"));
                e.setNom(resultatRequete.getString("nom"));

                lesEpreuves.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("La requete de getLesEpreuves a genere une erreur");
        }

        return lesEpreuves;
    }
}
