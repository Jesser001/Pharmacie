package pharmacie.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Commande {

    private int idCommande;
    private Date dateCommande;
    private String statut;
    private Fournisseur fournisseur;
    private List<LigneCommande> lignesCommande;

    public Commande() {
        this.lignesCommande = new ArrayList<>();
        this.dateCommande = new Date();
        this.statut = "EN_ATTENTE";
    }

    public Commande(int idCommande, Date dateCommande, String statut, Fournisseur fournisseur) {
        this();
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.fournisseur = fournisseur;
    }

    public void changerStatut(String nouveauStatut) {

        if (isTransitionValide(this.statut, nouveauStatut)) {
            this.statut = nouveauStatut;
            System.out.println("Statut de la commande " + idCommande + " changé à: " + nouveauStatut);

            if ("LIVREE".equals(nouveauStatut)) {
                mettreAJourStocks();
            }
        } else {
            throw new IllegalStateException(
                    "Transition de statut invalide: " + this.statut + " → " + nouveauStatut
            );
        }
    }

    private boolean isTransitionValide(String ancienStatut, String nouveauStatut) {

        switch (ancienStatut) {
            case "EN_ATTENTE":
                return "CONFIRMEE".equals(nouveauStatut) || "ANNULEE".equals(nouveauStatut);
            case "CONFIRMEE":
                return "LIVREE".equals(nouveauStatut) || "ANNULEE".equals(nouveauStatut);
            case "LIVREE":
                return false;
            case "ANNULEE":
                return false;
            default:
                return false;
        }
    }

    private void mettreAJourStocks() {
        for (LigneCommande ligne : lignesCommande) {
            Produit produit = ligne.getProduit();
            int nouvelleQuantite = produit.getQuantiteStock() + ligne.getQuantite();
            produit.setQuantiteStock(nouvelleQuantite);
            System.out.println("Stock de " + produit.getNom() + " mis à jour: +"
                    + ligne.getQuantite() + " unités");
        }
    }

    public double calculerTotal() {
        double total = 0.0;
        for (LigneCommande ligne : lignesCommande) {
            total += ligne.calculerSousTotal();
        }
        return total;
    }

    public void ajouterLigneCommande(Produit produit, int quantite) {

        for (LigneCommande ligne : lignesCommande) {
            if (ligne.getProduit().getIdProduit() == produit.getIdProduit()) {
                ligne.setQuantite(ligne.getQuantite() + quantite);
                return;
            }
        }

        LigneCommande nouvelleLigne = new LigneCommande(produit, quantite);
        lignesCommande.add(nouvelleLigne);
    }

    public void supprimerLigneCommande(int idProduit) {
        lignesCommande.removeIf(ligne
                -> ligne.getProduit().getIdProduit() == idProduit);
    }

    public LigneCommande getLigneCommande(int idProduit) {
        return lignesCommande.stream()
                .filter(ligne -> ligne.getProduit().getIdProduit() == idProduit)
                .findFirst()
                .orElse(null);
    }

    public boolean estDisponible() {
        return true;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<LigneCommande> getLignesCommande() {
        return new ArrayList<>(lignesCommande);
    }

    public void setLignesCommande(List<LigneCommande> lignesCommande) {
        this.lignesCommande = new ArrayList<>(lignesCommande);
    }

    @Override
    public String toString() {
        return "Commande #" + idCommande
                + " - " + statut
                + " - Fournisseur: " + (fournisseur != null ? fournisseur.getNom() : "N/A")
                + " - Total: " + calculerTotal() + "€";
    }
}
