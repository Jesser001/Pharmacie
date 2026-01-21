package pharmacie.service;

import pharmacie.dao.ProduitDao;
import pharmacie.exception.StockInsuffisantException;
import pharmacie.model.Produit;

public class GestionStockService {

    private ProduitDao produitDao = new ProduitDao();

    public void retirerStock(int idProduit, int quantite)
            throws StockInsuffisantException {

        Produit produit = produitDao.trouverProduit(idProduit);

        if (produit.getQuantiteStock() < quantite) {
            throw new StockInsuffisantException("Stock insuffisant");
        }

        produitDao.mettreAJourStock(
                idProduit,
                produit.getQuantiteStock() - quantite
        );
    }
}
