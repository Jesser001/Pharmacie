package pharmacie.model;

public class Fournisseur {

    private int idFournisseur;
    private String nom;
    private String contact;

    public Fournisseur(int idFournisseur, String nom, String contact) {
        this.idFournisseur = idFournisseur;
        this.nom = nom;
        this.contact = contact;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public String getNom() {
        return nom;
    }

    public String getContact() {
        return contact;
    }
}
