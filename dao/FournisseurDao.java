package pharmacie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pharmacie.model.Fournisseur;
import pharmacie.util.DatabaseConnection;

public class FournisseurDao {

    public List<Fournisseur> tousLesFournisseurs() {
        List<Fournisseur> list = new ArrayList<>();
        String sql = "SELECT * FROM fournisseur ORDER BY id_fournisseur";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Fournisseur f = new Fournisseur(
                        rs.getInt("id_fournisseur"),
                        rs.getString("nom"),
                        rs.getString("contact")
                );
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Fournisseur trouverFournisseur(int id) {
        String sql = "SELECT * FROM fournisseur WHERE id_fournisseur=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Fournisseur(
                            rs.getInt("id_fournisseur"),
                            rs.getString("nom"),
                            rs.getString("contact")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ajouterFournisseur(Fournisseur f) {
        String sql = "INSERT INTO fournisseur (nom, contact) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getNom());
            ps.setString(2, f.getContact());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateFournisseur(Fournisseur f) {
        String sql = "UPDATE fournisseur SET nom=?, contact=? WHERE id_fournisseur=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getNom());
            ps.setString(2, f.getContact());
            ps.setInt(3, f.getIdFournisseur());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerFournisseur(int id) {
        String sql = "DELETE FROM fournisseur WHERE id_fournisseur=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}