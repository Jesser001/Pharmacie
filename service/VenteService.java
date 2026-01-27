package pharmacie.service;

import java.util.List;

import pharmacie.dao.VenteDao;
import pharmacie.model.Vente;
import pharmacie.model.LigneVente;

public class VenteService {

    private VenteDao venteDao = new VenteDao();

    public void enregistrerVente(Vente vente) {
        venteDao.ajouterVente(vente, List.of());
    }

    public void enregistrerVente(Vente vente, List<LigneVente> lignes) {
        venteDao.ajouterVente(vente, lignes);
    }
}