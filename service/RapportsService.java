package pharmacie.service;

import java.util.List;

import pharmacie.dao.ProduitDao;
import pharmacie.dao.VenteDao;
import pharmacie.model.Produit;
import pharmacie.model.Vente;

public class RapportsService {

    private ProduitDao produitDao = new ProduitDao();
    private VenteDao venteDao = new VenteDao();

    public String etatDesStocks() {
        List<Produit> produits = produitDao.tousLesProduits();
        StringBuilder sb = new StringBuilder();
        sb.append("État des stocks:\n");
        for (Produit p : produits) {
            sb.append(String.format("- %s (id:%d) : %d unités (seuil: %d) %s\n",
                    p.getNom(), p.getIdProduit(), p.getQuantiteStock(), p.getSeuilAlerte(),
                    (p.getQuantiteStock() <= p.getSeuilAlerte() ? "⚠️ (stock faible)" : "")));
        }
        return sb.toString();
    }

    public String chiffreAffairesTotal() {
        List<Vente> ventes = venteDao.toutesLesVentes();
        double total = ventes.stream().mapToDouble(Vente::getMontantTotal).sum();
        return "Chiffre d'affaires total: " + String.format("%.2f", total) + " EUR";
    }

    public String rapportComplet() {
        StringBuilder sb = new StringBuilder();
        sb.append(etatDesStocks()).append("\n");
        sb.append(chiffreAffairesTotal()).append("\n");
        return sb.toString();
    }
}