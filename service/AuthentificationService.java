package pharmacie.service;

import pharmacie.dao.UtilisateurDao;
import pharmacie.exception.AuthentificationException;
import pharmacie.model.Utilisateur;

public class AuthentificationService {

    private UtilisateurDao utilisateurDao = new UtilisateurDao();

    public Utilisateur login(String login, String motDePasse)
            throws AuthentificationException {

        Utilisateur utilisateur = utilisateurDao.authentifier(login, motDePasse);
        if (utilisateur == null) {
            throw new AuthentificationException("Login ou mot de passe incorrect");
        }
        return utilisateur;
    }
}
