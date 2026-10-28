/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sio.la2028.test;

import java.sql.Connection;
import java.sql.SQLException;
import sio.la2028.database.ConnexionBdd;
import sio.la2028.database.DaoAthlete;
import sio.la2028.model.Athlete;

/**
 *
 * @author zakina
 */
public class TestConnexionBdd {
      public static void main (String args[]) throws SQLException {
        
        Connection cnx = ConnexionBdd.ouvrirConnexion();
        System.out.println ("nombre d athletes=" + DaoAthlete.getLesAthletes(cnx).size());
        
        //Test DaoAthlete getAthleteById 
        Athlete a = DaoAthlete.getAthleteById(cnx,1);
        
         System.out.println ("NOM athlete 1 " + a.getNom());
        
    }
}
