package pharmacie.model;

public class Client {

    private int idClient;
    private String nom;
    private String prenom;
    private String telephone;

    public Client(int idClient, String nom, String prenom, String telephone) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }
}
