package pharmacie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pharmacie.model.Vente;
import pharmacie.util.DatabaseConnection;

public class VenteDao {

    public void ajouterVente(Vente vente) {
        String sql = "INSERT INTO vente(date_vente, montant_total, id_client) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(vente.getDateVente()));
            ps.setDouble(2, vente.getMontantTotal());
            ps.setInt(3, vente.getIdClient());
            ps.executeUpdate();
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
