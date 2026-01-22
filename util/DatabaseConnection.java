package pharmacie.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL charg?? avec succ??s!");
        } catch (ClassNotFoundException e) {
            System.err.println("ERREUR: Driver MySQL non trouv??!");
            e.printStackTrace();
        }
    }

    private static final String URL = "jdbc:mysql://localhost:3306/pharmacie";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("DEBUG: Auto-commit = " + conn.getAutoCommit());
        return conn;
    }
}
