package pharmacie.service;

import pharmacie.dao.VenteDao;
import pharmacie.model.Vente;

public class VenteService {

    private VenteDao venteDao = new VenteDao();

    public void enregistrerVente(Vente vente) {
        venteDao.ajouterVente(vente);
    }
}
