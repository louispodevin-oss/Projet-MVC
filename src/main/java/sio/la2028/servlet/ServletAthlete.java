/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package sio.la2028.servlet;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sio.la2028.database.DaoAthlete;
import sio.la2028.database.DaoPays;
import sio.la2028.form.FormAthlete;
import sio.la2028.model.Athlete;
import sio.la2028.model.Pays;

/**
 *
 * @author zakina
 */
public class ServletAthlete extends HttpServlet {
    
    Connection cnx ;
            
    @Override
    public void init()
    {     
        ServletContext servletContext=getServletContext();
        
        System.out.println("SERVLKET CONTEXT=" + servletContext.getContextPath());
        cnx = (Connection)servletContext.getAttribute("connection"); 

        System.out.println(cnx);

        try {
            System.out.println("INIT SERVLET=" + cnx.getSchema());
            System.out.println("Base de données = " + cnx.getCatalog());
            System.out.println("Utilisateur = " + cnx.getMetaData().getUserName());
        } catch (SQLException ex) {
            Logger.getLogger(ServletAthlete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletAthlete</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletAthlete at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = request.getRequestURI();  
       
        // Récup et affichage les athletes 
        if(url.equals("/la2028_war/ServletAthlete/lister"))
        {              
            ArrayList<Athlete> lesAthletes = DaoAthlete.getLesAthletes(cnx);
            request.setAttribute("pLesAthletes", lesAthletes);
            //System.out.println("lister eleves - nombres d'élèves récupérés" + lesEleves.size() );
           getServletContext().getRequestDispatcher("/vues/athlete/listerAthletes.jsp").forward(request, response);
        }
        
        if(url.equals("/la2028_war/ServletAthlete/consulter"))
        { 
            int idAthlete = Integer.parseInt((String)request.getParameter("idAthlete"));
            Athlete a = DaoAthlete.getAthleteById(cnx, idAthlete);
            request.setAttribute("pAthlete", a);
            //System.out.println("lister eleves - nombres d'élèves récupérés" + lesEleves.size() );
           getServletContext().getRequestDispatcher("/vues/athlete/consulterAthlete.jsp").forward(request, response);
        }
        
          if(url.equals("/la2028_war/ServletAthlete/ajouter"))
        {                   
            ArrayList<Pays> lesPays = DaoPays.getLesPays(cnx);
            request.setAttribute("pLesPays", lesPays);
            this.getServletContext().getRequestDispatcher("/vues/athlete/ajouterAthlete.jsp" ).forward( request, response );
        }
        
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             
        
         FormAthlete form = new FormAthlete();
		
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        Athlete ath = form.ajouterAthlete(request);
        
        /* Stockage du formulaire et de l'objet dans l'objet request */
        request.setAttribute( "form", form );
        request.setAttribute( "pAthlete", ath );
		
        if (form.getErreurs().isEmpty()){
            Athlete athleteInsere =  DaoAthlete.addAthlete(cnx, ath);
            if (athleteInsere != null ){
                request.setAttribute( "pAthlete", athleteInsere );
                this.getServletContext().getRequestDispatcher("/vues/athlete/consulterAthlete.jsp" ).forward( request, response );
            }
            else 
            {
                // Cas oùl'insertion en bdd a échoué
                //renvoyer vers une page d'erreur 
            }
           
        }
        else
        { 
            // il y a des erreurs. On réaffiche le formulaire avec des messages d'erreurs
            ArrayList<Pays> lesCasernes = DaoPays.getLesPays(cnx);
            request.setAttribute("pLesPays", lesCasernes);
            this.getServletContext().getRequestDispatcher("/vues/athlete/ajouterAthlete.jsp" ).forward( request, response );
        }
        
        
        
        
        
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
