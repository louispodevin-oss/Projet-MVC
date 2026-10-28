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
        String path = request.getServletPath();
        try {
            if ("/ServletAthlete/lister".equals(path)) {
                ArrayList<Athlete> lesAthletes = DaoAthlete.getLesAthletes(cnx);
                request.setAttribute("pLesAthletes", lesAthletes);
                getServletContext().getRequestDispatcher("/vues/athlete/listerAthletes.jsp").forward(request, response);
            } else if ("/ServletAthlete/consulter".equals(path)) {
                int idAthlete = Integer.parseInt(request.getParameter("idAthlete"));
                Athlete athlete = DaoAthlete.getAthleteById(cnx, idAthlete);
                if (athlete == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                request.setAttribute("pAthlete", athlete);
                getServletContext().getRequestDispatcher("/vues/athlete/consulterAthlete.jsp").forward(request, response);
            } else if ("/ServletAthlete/ajouter".equals(path)) {
                afficherFormulaireAjout(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identifiant d'athlète invalide.");
        } catch (SQLException e) {
            throw new ServletException("Impossible de récupérer les données des athlètes.", e);
        }
    }

    private void afficherFormulaireAjout(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        request.setAttribute("pLesPays", DaoPays.getLesPays(cnx));
        getServletContext().getRequestDispatcher("/vues/athlete/ajouterAthlete.jsp").forward(request, response);
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
        String path = request.getServletPath();
        if ("/ServletAthlete/classer".equals(path)) {
            modifierRang(request, response);
        } else if ("/ServletAthlete/ajouter".equals(path)) {
            ajouterAthlete(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void modifierRang(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idAthlete = Integer.parseInt(request.getParameter("idAthlete"));
            int rang = Integer.parseInt(request.getParameter("rang"));
            if (idAthlete < 1 || rang < 1) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "L'identifiant et le rang doivent être positifs.");
                return;
            }
            DaoAthlete.modifierRang(cnx, idAthlete, rang);
            response.sendRedirect(request.getContextPath() + "/ServletAthlete/lister");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identifiant ou rang invalide.");
        } catch (SQLException e) {
            throw new ServletException("Impossible de modifier le rang de l'athlète.", e);
        }
    }

    private void ajouterAthlete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FormAthlete form = new FormAthlete();
        Athlete athlete = form.ajouterAthlete(request);
        request.setAttribute("form", form);
        request.setAttribute("pAthlete", athlete);

        try {
            if (!form.getErreurs().isEmpty()) {
                request.setAttribute("pLesPays", DaoPays.getLesPays(cnx));
                getServletContext().getRequestDispatcher("/vues/athlete/ajouterAthlete.jsp").forward(request, response);
                return;
            }
            Athlete athleteInsere = DaoAthlete.addAthlete(cnx, athlete);
            if (athleteInsere == null) {
                throw new ServletException("L'athlète enregistré est introuvable.");
            }
            request.setAttribute("pAthlete", athleteInsere);
            getServletContext().getRequestDispatcher("/vues/athlete/consulterAthlete.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Impossible d'enregistrer l'athlète.", e);
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
