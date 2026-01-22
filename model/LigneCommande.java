package pharmacie.model;

public class LigneCommande {

    private int quantite;
    private Produit produit;

    public LigneCommande() {
        this.quantite = 1;
    }

    public LigneCommande(Produit produit, int quantite) {
        this.produit = produit;
        setQuantite(quantite);
    }

    public double calculerSousTotal() {
        if (produit != null) {
            return produit.getPrix() * quantite;
        }
        return 0.0;
    }

    public boolean estDisponible() {
        if (produit != null) {
            return produit.getQuantiteStock() >= quantite;
        }
        return false;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public void incrementerQuantite() {
        this.quantite++;
    }

    public void decrementerQuantite() {
        if (this.quantite > 1) {
            this.quantite--;
        }
    }

    @Override
    public String toString() {
        if (produit != null) {
            return produit.getNom()
                    + " x" + quantite
                    + " = " + calculerSousTotal() + "€"
                    + " (Prix unitaire: " + produit.getPrix() + "€)";
        }
        return "Ligne commande (produit non spécifié)";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        LigneCommande that = (LigneCommande) obj;

        if (produit == null || that.produit == null) {
            return false;
        }

        return produit.getIdProduit() == that.produit.getIdProduit();
    }

    @Override
    public int hashCode() {
        return produit != null ? produit.getIdProduit() : 0;
    }
}
