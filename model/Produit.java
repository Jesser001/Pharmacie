package pharmacie.model;

public class Produit {

    private int idProduit;
    private String nom;
    private double prix;
    private int quantiteStock;
    private int seuilAlerte;

    public Produit(int idProduit, String nom, double prix, int quantiteStock, int seuilAlerte) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
        this.seuilAlerte = seuilAlerte;
    }

    // Getters principaux
    public int getIdProduit() {
        return idProduit;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public int getSeuilAlerte() {
        return seuilAlerte;
    }

    // Getters alias pour compatibilité
    public int getId() {
        return idProduit;
    }

    public int getQuantite() {
        return quantiteStock;
    }

    public int getSeuil() {
        return seuilAlerte;
    }

    // Setters
    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public void setSeuilAlerte(int seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    // Setters alias
    public void setId(int id) {
        this.idProduit = id;
    }

    public void setQuantite(int quantite) {
        this.quantiteStock = quantite;
    }

    public void setSeuil(int seuil) {
        this.seuilAlerte = seuil;
    }
}
