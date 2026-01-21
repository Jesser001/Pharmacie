package pharmacie.model;

public class Utilisateur {

    private int idUtilisateur;
    private String login;
    private String motDePasse;
    private String role;

    public Utilisateur(int idUtilisateur, String login, String motDePasse, String role) {
        this.idUtilisateur = idUtilisateur;
        this.login = login;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getRole() {
        return role;
    }
}
