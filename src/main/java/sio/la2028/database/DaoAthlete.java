/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sio.la2028.database;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import sio.la2028.model.Athlete;
import sio.la2028.model.Pays;

/**
 *
 * @author zakina
 */
public class DaoAthlete {
    
    Connection cnx;
    static PreparedStatement requeteSql = null;
    static ResultSet resultatRequete = null;
    
    public static ArrayList<Athlete> getLesAthletes(Connection cnx){
        
        ArrayList<Athlete> lesAthletes = new ArrayList<Athlete>();
        try{
            requeteSql = cnx.prepareStatement(
                    "SELECT a.id AS a_id, " +
                            "a.nom AS a_nom, " +
                            "a.prenom AS a_prenom, " +
                            "a.date_naissance AS a_date_naissance, " +
                            "p.id AS p_id, " +
                            "p.nom AS p_nom " +
                            "FROM athlete a INNER JOIN pays p " +
                            "ON a.pays_id = p.id"
            );
            //System.out.println("REQ="+ requeteSql);
            resultatRequete = requeteSql.executeQuery();
            
            while (resultatRequete.next()){
                
                Athlete a = new Athlete();
                   a.setId(resultatRequete.getInt("a_id"));
                   a.setNom(resultatRequete.getString("a_nom"));
                   a.setPrenom(resultatRequete.getString("a_prenom"));
                   Date dateSql = resultatRequete.getDate("a_date_naissance");

                   if (dateSql != null) {
                       LocalDateTime dateNaissance = dateSql.toLocalDate().atStartOfDay();
                       a.setDataNaissance(dateNaissance);
                   }

                Pays p = new Pays();
                   p.setId(resultatRequete.getInt("p_id"));
                   p.setNom(resultatRequete.getString("p_nom"));
                
                    a.setPays(p);
                
                lesAthletes.add(a);
            }
           
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("La requête de getLesPompiers e généré une erreur");
        }
        return lesAthletes;
    }
    
    public static Athlete getAthleteById(Connection cnx, int idAthlete){
        
        Athlete a = new Athlete();
        try{
            requeteSql = cnx.prepareStatement("select a.id as a_id, a.nom as a_nom,  p.id as p_id, p.nom as p_nom " +
                         " from athlete a inner join pays p " +
                         " on a.pays_id = p.id " + 
                         " where a.id = ? ");
            //System.out.println("REQ="+ requeteSql);
            requeteSql.setInt(1, idAthlete);
            resultatRequete = requeteSql.executeQuery();
            
            if (resultatRequete.next()){
                
                   a.setId(resultatRequete.getInt("a_id"));
                   a.setNom(resultatRequete.getString("a_nom"));
                    
                   Pays p = new Pays();
                   p.setId(resultatRequete.getInt("p_id"));
                   p.setNom(resultatRequete.getString("p_nom"));
                
                    a.setPays(p);
                
            }
           
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("La requête de getLesPompiers e généré une erreur");
        }
        return a;
    }
    
     public static Athlete addAthlete(Connection connection, Athlete ath){      
        int idGenere = -1;
        try
        {
            //preparation de la requete
            // id (clé primaire de la table athlete) est en auto_increment,donc on ne renseigne pas cette valeur
            // la paramètre RETURN_GENERATED_KEYS est ajouté à la requête afin de pouvoir récupérer l'id généré par la bdd (voir ci-dessous)
            // supprimer ce paramètre en cas de requête sans auto_increment.
            requeteSql=connection.prepareStatement("INSERT INTO athlete (nom, pays_id)\n" +
                    "VALUES (?,?)", requeteSql.RETURN_GENERATED_KEYS );
            requeteSql.setString(1, ath.getNom());      
            requeteSql.setInt(2, ath.getPays().getId());

           /* Exécution de la requête */
            requeteSql.executeUpdate();
            
             // Récupération de id auto-généré par la bdd dans la table client
            resultatRequete = requeteSql.getGeneratedKeys();
            while ( resultatRequete.next() ) {
                idGenere = resultatRequete.getInt( 1 );
                ath.setId(idGenere);
                
                ath = DaoAthlete.getAthleteById(connection, ath.getId());
            }
            
         
        }   
        catch (SQLException e) 
        {
            e.printStackTrace();
            //out.println("Erreur lors de l’établissement de la connexion");
        }
        return ath ;    
    }
    
    
}
