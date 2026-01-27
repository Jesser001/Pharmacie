package pharmacie.model;

public class LigneCommande {

    private int idCommande;
    private Produit produit;
    private int quantite;

    public LigneCommande(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public LigneCommande(int idCommande, Produit produit, int quantite) {
        this.idCommande = idCommande;
        this.produit = produit;
        this.quantite = quantite;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int q) {
        this.quantite = q;
    }

    public double calculerSousTotal() {
        return produit.getPrix() * quantite;
    }
}
