/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sio.la2028.form;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import sio.la2028.model.Athlete;
import sio.la2028.model.Pays;

/**
 *
 * @author zakina
 */
public class FormAthlete {
    
    private String resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public void setErreurs(Map<String, String> erreurs) {
        this.erreurs = erreurs;
    }
    
    //méthode de validation du champ de saisie nom
    private void validationNom( String nom ) throws Exception {
        if ( nom != null && nom.length() < 3 ) {
        throw new Exception( "Le nom d'athlete doit contenir au moins 3 caractères." );
        }
    }

    private void setErreur( String champ, String message ) {
    erreurs.put(champ, message );
    }    
    
    private static String getDataForm( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }   
    }
    
    
    public Athlete ajouterAthlete( HttpServletRequest request ) {
      
        Athlete ath  = new Athlete();
         
        String nom = getDataForm( request, "nom" );
        String idPaysForm = getDataForm(request, "idPays");
        String rangForm = getDataForm(request, "rang");

        try {
             validationNom( nom );
        } catch ( Exception e ) {
            setErreur( "nom", e.getMessage() );
        }
        ath.setNom(nom);

        try {
            int rang = Integer.parseInt(rangForm);
            if (rang < 1) {
                setErreur("rang", "Le rang doit être supérieur ou égal à 1.");
            } else {
                ath.setRang(rang);
            }
        } catch (NumberFormatException e) {
            setErreur("rang", "Veuillez saisir un rang entier supérieur ou égal à 1.");
        }

        int idPays = 0;
        try {
            idPays = Integer.parseInt(idPaysForm);
            if (idPays < 1) {
                setErreur("idPays", "Veuillez sélectionner un pays valide.");
            }
        } catch (NumberFormatException e) {
            setErreur("idPays", "Veuillez sélectionner un pays.");
        }

        if ( erreurs.isEmpty() ) {
            resultat = "Succès de l'ajout.";
        } else {
            resultat = "Échec de l'ajout.";
        }
         
      
     
        Pays p = new Pays(idPays);
        ath.setPays(p);
        
        return ath ;
    }
    
}
