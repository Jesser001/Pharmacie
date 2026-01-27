package pharmacie.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pharmacie.model.Vente;
import pharmacie.model.LigneVente;
import pharmacie.model.Produit;
import pharmacie.util.DatabaseConnection;

public class VenteDao {

    public void ajouterVente(Vente vente, List<LigneVente> lignes) {
        String sqlVente = "INSERT INTO vente (date_vente, montant_total, id_client) VALUES (?, ?, ?)";
        String sqlLigne = "INSERT INTO ligne_vente (id_vente, id_produit, quantite, prix_unitaire) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psV = conn.prepareStatement(sqlVente, Statement.RETURN_GENERATED_KEYS)) {

                psV.setDate(1, Date.valueOf(vente.getDateVente()));
                psV.setDouble(2, vente.getMontantTotal());
                if (vente.getIdClient() > 0) {
                    psV.setInt(3, vente.getIdClient());
                } else {
                    psV.setNull(3, java.sql.Types.INTEGER);
                }

                psV.executeUpdate();
                try (ResultSet keys = psV.getGeneratedKeys()) {
                    if (keys.next()) {
                        int idVente = keys.getInt(1);
                        vente.setIdVente(idVente);

                        try (PreparedStatement psL = conn.prepareStatement(sqlLigne)) {
                            ProduitDao produitDao = new ProduitDao();

                            for (LigneVente lv : lignes) {
                                psL.setInt(1, idVente);
                                psL.setInt(2, lv.getIdProduit());
                                psL.setInt(3, lv.getQuantite());
                                psL.setDouble(4, lv.getPrixUnitaire());
                                psL.addBatch();

                                Produit p = produitDao.trouverProduit(lv.getIdProduit());
                                if (p != null) {
                                    int nouvelleQuantite = p.getQuantiteStock() - lv.getQuantite();
                                    if (nouvelleQuantite < 0) {
                                        nouvelleQuantite = 0;
                                    }
                                    produitDao.mettreAJourStock(lv.getIdProduit(), nouvelleQuantite);
                                }
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

    public List<Vente> toutesLesVentes() {
        List<Vente> ventes = new ArrayList<>();
        String sql = "SELECT * FROM vente ORDER BY date_vente DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vente vente = new Vente();
                vente.setIdVente(rs.getInt("id_vente"));
                vente.setDateVente(rs.getDate("date_vente").toLocalDate());
                vente.setMontantTotal(rs.getDouble("montant_total"));
                vente.setIdClient(rs.getInt("id_client"));
                ventes.add(vente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ventes;
    }
}