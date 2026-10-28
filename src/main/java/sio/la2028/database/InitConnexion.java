package sio.la2028.database;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InitConnexion implements ServletContextListener {
    private Connection cnx;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String url = System.getenv().getOrDefault(
                "LA2028_DB_URL",
                "jdbc:mariadb://127.0.0.1:3306/la2028?allowPublicKeyRetrieval=true&useSSL=false");
        String user = System.getenv().getOrDefault("LA2028_DB_USER", "root");
        String password = System.getenv().getOrDefault("LA2028_DB_PASSWORD", "password");

        try {
            if (url.startsWith("jdbc:h2:")) {
                Class.forName("org.h2.Driver");
            } else {
                Class.forName("org.mariadb.jdbc.Driver");
            }
            cnx = DriverManager.getConnection(url, user, password);
            if (url.startsWith("jdbc:h2:")) {
                DemoDatabase.initialize(cnx);
            }
            context.setAttribute("connection", cnx);
            context.log("Connexion à la base de données établie.");
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException("Impossible de se connecter à la base de données.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (cnx != null) {
            try {
                cnx.close();
            } catch (SQLException e) {
                event.getServletContext().log("Impossible de fermer la connexion à la base de données.", e);
            }
        }
    }
}
