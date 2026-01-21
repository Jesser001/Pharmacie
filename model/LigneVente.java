package pharmacie.model;

public class LigneVente {

    private int idVente;
    private int idProduit;
    private int quantite;
    private double prixUnitaire;

    public LigneVente(int idVente, int idProduit, int quantite, double prixUnitaire) {
        this.idVente = idVente;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public int getIdVente() {
        return idVente;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }
}
