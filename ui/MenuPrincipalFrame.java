package pharmacie.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pharmacie.model.Utilisateur;

public class MenuPrincipalFrame extends JFrame {

    public MenuPrincipalFrame(Utilisateur utilisateur) {
        setTitle("Pharmacie - " + utilisateur.getLogin());
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Menu Stock
        JMenu menuStock = new JMenu("Stock");
        JMenuItem itemVoirStock = new JMenuItem("Voir stock");
        menuStock.add(itemVoirStock);

        // Menu Ventes
        JMenu menuVentes = new JMenu("Ventes");
        JMenuItem itemNouvelleVente = new JMenuItem("Nouvelle vente");
        JMenuItem itemHistorique = new JMenuItem("Historique");
        menuVentes.add(itemNouvelleVente);
        menuVentes.add(itemHistorique);

        // Menu Rapports
        JMenu menuRapports = new JMenu("Rapports");
        JMenuItem itemRapports = new JMenuItem("Voir rapports");
        menuRapports.add(itemRapports);

        // Menu Admin (si ADMIN)
        if (utilisateur.getRole().equals("ADMIN")) {
            JMenu menuAdmin = new JMenu("Admin");
            JMenuItem itemGestionUsers = new JMenuItem("Gestion utilisateurs");
            menuAdmin.add(itemGestionUsers);
            menuBar.add(menuAdmin);
            itemGestionUsers.addActionListener(e -> new GestionUtilisateursFrame());
        }

        // Menu Aide
        JMenu menuAide = new JMenu("Aide");
        JMenuItem itemDeconnexion = new JMenuItem("Deconnexion");
        menuAide.add(itemDeconnexion);

        menuBar.add(menuStock);
        menuBar.add(menuVentes);
        menuBar.add(menuRapports);
        menuBar.add(menuAide);

        setJMenuBar(menuBar);

        // Actions
        itemVoirStock.addActionListener(e -> new GestionStockFrame());
        itemNouvelleVente.addActionListener(e -> new NouvelleVenteFrame());
        itemHistorique.addActionListener(e -> new HistoriqueVentesFrame());
        itemRapports.addActionListener(e -> new RapportsFrame());
        itemDeconnexion.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Bienvenue " + utilisateur.getLogin() + " (" + utilisateur.getRole() + ")",
                SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.CENTER);

        // Boutons rapides
        JPanel quickPanel = new JPanel(new FlowLayout());
        JButton btnStock = new JButton("Stock");
        JButton btnVente = new JButton("Vente");
        JButton btnRapport = new JButton("Rapport");

        btnStock.addActionListener(e -> new GestionStockFrame());
        btnVente.addActionListener(e -> new NouvelleVenteFrame());
        btnRapport.addActionListener(e -> new RapportsFrame());

        quickPanel.add(btnStock);
        quickPanel.add(btnVente);
        quickPanel.add(btnRapport);

        panel.add(quickPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
