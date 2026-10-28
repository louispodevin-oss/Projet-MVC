<%-- 
    Document   : ajouterAthlete
    Created on : 25/08/2026, 13:30:47
    Author     : zakina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sio.la2028.model.Pays"%>
<%@page import="sio.la2028.model.Athlete"%>
<%@page import="sio.la2028.form.FormAthlete"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOS ANGELES 2028</title>
    </head>
    <body>
        <h1>NOUVEL ATHLETE</h1>
        
            <%
                FormAthlete form = (FormAthlete)request.getAttribute("form");
                Athlete athlete = (Athlete)request.getAttribute("pAthlete");
            %>
        
        <form class="form-inline" action="ajouter" method="POST">
                <label for="nom">NOM : </label>
                <input id="nom" type="text" name="nom"  size="30" maxlength="30">
                </br>

                <label for="rang">Rang (1 = meilleur) : </label>
                <input id="rang" type="number" name="rang" min="1" step="1" required
                       value="<%= athlete != null && athlete.getRang() != null ? athlete.getRang() : "" %>">
                <% if (form != null && form.getErreurs().containsKey("rang")) { %>
                    <span><%= form.getErreurs().get("rang") %></span>
                <% } %>
                </br>
                            
                <%-- Champ Liste des pays --%>
                <label for="pays">Pays : </label>
                <select name="idPays">
                    <%
                        ArrayList<Pays> lesPays= (ArrayList)request.getAttribute("pLesPays");
                        for (int i=0; i<lesPays.size();i++){
                            Pays p = lesPays.get(i);
                            out.println("<option value='" + p.getId()+"'>" + p.getNom()+"</option>" );
                        }
                    %>
                </select>
                </br>            
                               
            <input type="submit" name="valider" id="valider" value="Valider"/>
            </form>
        
        
        
        
    </body>
</html>