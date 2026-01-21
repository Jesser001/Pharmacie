package pharmacie.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import pharmacie.exception.AuthentificationException;
import pharmacie.model.Utilisateur;
import pharmacie.service.AuthentificationService;
public class LoginFrame extends JFrame {

    public LoginFrame() {
        JTextField loginField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Connexion");

        loginButton.addActionListener(e -> {
            try {
                AuthentificationService service = new AuthentificationService();
                Utilisateur utilisateur = service.login(
                        loginField.getText(),
                        new String(passwordField.getPassword())
                );
                new MenuPrincipalFrame(utilisateur);
                dispose();
            } catch (AuthentificationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Login"));
        panel.add(loginField);
        panel.add(new JLabel("Mot de passe"));
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
        setTitle("Connexion");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}


