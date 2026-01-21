package pharmacie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pharmacie.model.Produit;
import pharmacie.util.DatabaseConnection;

public class ProduitDao {

    public Produit trouverProduit(int idProduit) {
        String sql = "SELECT * FROM produit WHERE id_produit=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProduit);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Produit(
                        rs.getInt("id_produit"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getInt("quantite_stock"),
                        rs.getInt("seuil_alerte")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void mettreAJourStock(int idProduit, int nouvelleQuantite) {
        String sql = "UPDATE produit SET quantite_stock=? WHERE id_produit=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nouvelleQuantite);
            ps.setInt(2, idProduit);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajouterProduit(Produit produit) {
        String sql = "INSERT INTO produit (nom, prix, quantite_stock, seuil_alerte) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produit.getNom());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getQuantiteStock());
            ps.setInt(4, produit.getSeuilAlerte());

            ps.executeUpdate();
            System.out.println("Produit ajouté à la base!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MÉTHODE CRITIQUE À AJOUTER
    public List<Produit> tousLesProduits() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit ORDER BY id_produit";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produit produit = new Produit(
                        rs.getInt("id_produit"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getInt("quantite_stock"),
                        rs.getInt("seuil_alerte")
                );
                produits.add(produit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return produits;
    }

    // Méthode pour mettre à jour un produit complet
    public void updateProduit(Produit produit) {
        String sql = "UPDATE produit SET nom=?, prix=?, quantite_stock=?, seuil_alerte=? WHERE id_produit=?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produit.getNom());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getQuantiteStock());
            ps.setInt(4, produit.getSeuilAlerte());
            ps.setInt(5, produit.getIdProduit());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un produit
    public void supprimerProduit(int idProduit) {
        String sql = "DELETE FROM produit WHERE id_produit=?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProduit);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
