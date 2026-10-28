<%--
    Document   : listerAthletes
    Created on : 25 août 2026, 12:27:51
    Author     : zakina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sio.la2028.model.Epreuve"%>
<%@page import="sio.la2028.model.Pays"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="sio.la2028.model.Epreuve" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>LOS ANGELES 2028</title>
  <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
        integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
        crossorigin="anonymous">

  <title>LOS ANGELES 2028</title>

  <style>
    body {
      padding-top: 50px;
    }
    .special {
      padding-top:50px;
    }
  </style>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <a  href ='../ServletEpreuve/lister' class="navbar-brand" href=".">Système de gestion des epreuves</a>
    </div>
  </div>
</nav>
<body>
<div class="container special">
  <h2 class="h2">Liste des Epreuves</h2>
  <div class="table-responsive">
      <%
                    ArrayList<Epreuve> lesEpreuves= (ArrayList)request.getAttribute("pLesEpreuve");
                %>
    <table class="table table-striped table-sm">
      <thead>
      <tr>
        <th>id</th>
        <th>nom</th>
        <th>participants</th>

      </tr>
      </thead>
      <tbody>
      <tr>
        <%
          for (Epreuve a : lesEpreuves)
          {
            out.println("<tr><td>");
            out.println(a.getId());
            out.println("</td>");

            out.println("<td><a href ='../ServletAthlete/consulter?idAthlete="+ a.getId()+ "'>");
            out.println(a.getNom());
            out.println("</a></td>");;

          }
        %>
      </tr>
      </tbody>
    </table>
</body>
</div>
</div>

</html>
