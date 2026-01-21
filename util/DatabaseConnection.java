package pharmacie.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Charge le driver MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL charg?? avec succ??s!");
        } catch (ClassNotFoundException e) {
            System.err.println("ERREUR: Driver MySQL non trouv??!");
            e.printStackTrace();
        }
    }

    // URL CORRECTE: avec le nom de la base
    private static final String URL = "jdbc:mysql://localhost:3306/pharmacie";

    // Identifiants MySQL (pas de ton application)
    // PAR D??FAUT MySQL a: root/sans mot de passe
    private static final String USER = "root";       // Utilisateur MySQL
    private static final String PASSWORD = "lahmerkun1234";       // Mot de passe MySQL (vide par d??faut)

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

