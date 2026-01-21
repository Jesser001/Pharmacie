package pharmacie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pharmacie.model.Utilisateur;
import pharmacie.util.DatabaseConnection;

public class UtilisateurDao {

    public Utilisateur authentifier(String login, String motDePasse) {
        System.out.println("=== DEBUG ===");
        System.out.println("Login reçu: [" + login + "]");
        System.out.println("Longueur login: " + login.length());
        System.out.println("Password reçu: [" + motDePasse + "]");
        System.out.println("Longueur password: " + motDePasse.length());

        String sql = "SELECT * FROM utilisateur WHERE login=? AND mot_de_passe=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, motDePasse);

            System.out.println("Requête SQL: SELECT * FROM utilisateur WHERE login='"
                    + login + "' AND mot_de_passe='" + motDePasse + "'");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("✅ Utilisateur trouvé!");
                return new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("login"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                );
            } else {
                System.out.println("❌ Aucun utilisateur trouvé");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Utilisateur> tousLesUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur ORDER BY id_utilisateur";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Utilisateur user = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("login"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                );
                utilisateurs.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    public void ajouterUtilisateur(String login, String motDePasse, String role) {
        String sql = "INSERT INTO utilisateur (login, mot_de_passe, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, motDePasse);
            ps.setString(3, role);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
