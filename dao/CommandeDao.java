package pharmacie.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pharmacie.model.Commande;
import pharmacie.model.LigneCommande;
import pharmacie.util.DatabaseConnection;

public class CommandeDao {

    public void ajouterCommande(Commande commande) {
        String sqlCommande = "INSERT INTO commande_fournisseur (date_commande, statut, id_fournisseur) VALUES (?, ?, ?)";
        String sqlLigne = "INSERT INTO ligne_commande (id_commande, id_produit, quantite) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psC = conn.prepareStatement(sqlCommande, Statement.RETURN_GENERATED_KEYS)) {
                psC.setDate(1, new Date(commande.getDateCommande().getTime()));
                psC.setString(2, commande.getStatut());
                if (commande.getFournisseur() != null) {
                    psC.setInt(3, commande.getFournisseur().getIdFournisseur());
                } else {
                    psC.setNull(3, java.sql.Types.INTEGER);
                }
                psC.executeUpdate();
                try (ResultSet keys = psC.getGeneratedKeys()) {
                    if (keys.next()) {
                        int idCommande = keys.getInt(1);
                        commande.setIdCommande(idCommande);
                        try (PreparedStatement psL = conn.prepareStatement(sqlLigne)) {
                            for (LigneCommande lc : commande.getLignesCommande()) {
                                psL.setInt(1, idCommande);
                                psL.setInt(2, lc.getProduit().getIdProduit());
                                psL.setInt(3, lc.getQuantite());
                                psL.addBatch();
                            }
                            psL.executeBatch();
                        }
                    }
                }
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Commande> toutesLesCommandes() {
        List<Commande> list = new ArrayList<>();
        String sql = "SELECT c.id_commande, c.date_commande, c.statut, c.id_fournisseur, f.nom AS fournisseur_nom, f.contact AS fournisseur_contact FROM commande_fournisseur c LEFT JOIN fournisseur f ON c.id_fournisseur=f.id_fournisseur ORDER BY c.id_commande DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            FournisseurDao fournisseurDao = new FournisseurDao();
            ProduitDao produitDao = new ProduitDao();

            while (rs.next()) {
                int id = rs.getInt("id_commande");
                Commande c = new Commande();
                c.setIdCommande(id);
                c.setDateCommande(rs.getDate("date_commande"));
                c.setStatut(rs.getString("statut"));
                int idF = rs.getInt("id_fournisseur");
                if (idF > 0) {
                    c.setFournisseur(fournisseurDao.trouverFournisseur(idF));
                }
                // charger lignes
                String sqlLignes = "SELECT id_produit, quantite FROM ligne_commande WHERE id_commande=?";
                try (PreparedStatement psL = conn.prepareStatement(sqlLignes)) {
                    psL.setInt(1, id);
                    try (ResultSet rsL = psL.executeQuery()) {
                        while (rsL.next()) {
                            int idProd = rsL.getInt("id_produit");
                            int qte = rsL.getInt("quantite");
                            var produit = produitDao.trouverProduit(idProd);
                            if (produit != null) {
                                c.ajouterLigneCommande(produit, qte);
                            }
                        }
                    }
                }
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void modifierStatut(int idCommande, String statut) {
        String sql = "UPDATE commande_fournisseur SET statut=? WHERE id_commande=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statut);
            ps.setInt(2, idCommande);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recevoirCommande(int idCommande) {
        String sqlGetLignes = "SELECT id_produit, quantite FROM ligne_commande WHERE id_commande=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlGetLignes)) {
            ps.setInt(1, idCommande);
            try (ResultSet rs = ps.executeQuery()) {
                ProduitDao produitDao = new ProduitDao();
                while (rs.next()) {
                    int idProduit = rs.getInt("id_produit");
                    int qte = rs.getInt("quantite");
                    var produit = produitDao.trouverProduit(idProduit);
                    if (produit != null) {
                        int nouvelle = produit.getQuantiteStock() + qte;
                        produitDao.mettreAJourStock(idProduit, nouvelle);
                    }
                }
            }
            modifierStatut(idCommande, "REÇUE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Commande trouverCommande(int idCommande) {
        return toutesLesCommandes().stream()
                .filter(c -> c.getIdCommande() == idCommande)
                .findFirst()
                .orElse(null);
    }
}