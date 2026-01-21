package pharmacie.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import pharmacie.dao.ProduitDao;
import pharmacie.model.Produit;

public class GestionStockFrame extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private ProduitDao produitDao = new ProduitDao();

    public GestionStockFrame() {
        setTitle("Gestion de Stock - Pharmacie");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configuration du tableau
        String[] columns = {"ID", "Nom", "Prix (€)", "Quantité", "Seuil", "Statut"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);

        // Panneau de boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAjouter = new JButton("➕ Ajouter");
        JButton btnModifier = new JButton("✏️ Modifier");
        JButton btnSupprimer = new JButton("🗑️ Supprimer");
        JButton btnRafraichir = new JButton("🔄 Rafraîchir");
        JButton btnFermer = new JButton("❌ Fermer");

        // Styliser les boutons
        Color btnColor = new Color(70, 130, 180);
        Font btnFont = new Font("Arial", Font.BOLD, 12);

        for (JButton btn : new JButton[]{btnAjouter, btnModifier, btnSupprimer, btnRafraichir, btnFermer}) {
            btn.setBackground(btnColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        }

        // Action Listeners
        btnAjouter.addActionListener(e -> ajouterProduit());

        btnModifier.addActionListener(e -> modifierProduit());

        btnSupprimer.addActionListener(e -> supprimerProduit());

        btnRafraichir.addActionListener(e -> rafraichirTable());

        btnFermer.addActionListener(e -> dispose());

        // AJOUTER LE BOUTON AJOUTER QUI MANQUAIT
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnRafraichir);
        buttonPanel.add(btnFermer);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Titre
        JLabel titre = new JLabel("📦 GESTION DES PRODUITS EN STOCK", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setForeground(new Color(30, 60, 90));

        mainPanel.add(titre, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Charger les données initiales DEPUIS LA BASE
        rafraichirTable();

        // Rafraîchir automatiquement toutes les 30 secondes
        Timer timer = new Timer(30000, e -> rafraichirTable());
        timer.setRepeats(true);
        timer.start();

        setVisible(true);
    }

    private void ajouterProduit() {
        AjoutProduitFrame dialog = new AjoutProduitFrame(this);
        dialog.setVisible(true);

        // Rafraîchir après fermeture
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(300);
                rafraichirTable(); // MAINTENANT CELA FONCTIONNE CAR ON A LA MÉTHODE
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void modifierProduit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un produit à modifier.",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        String nom = (String) model.getValueAt(selectedRow, 1);

        // Récupérer le produit complet depuis la base
        Produit produit = produitDao.trouverProduit(id);
        if (produit == null) {
            JOptionPane.showMessageDialog(this, "Produit introuvable!");
            return;
        }

        // Dialog pour modifier
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        JTextField txtNom = new JTextField(produit.getNom());
        JTextField txtPrix = new JTextField(String.valueOf(produit.getPrix()));
        JTextField txtQuantite = new JTextField(String.valueOf(produit.getQuantite()));
        JTextField txtSeuil = new JTextField(String.valueOf(produit.getSeuil()));

        panel.add(new JLabel("Nom:"));
        panel.add(txtNom);
        panel.add(new JLabel("Prix:"));
        panel.add(txtPrix);
        panel.add(new JLabel("Quantité:"));
        panel.add(txtQuantite);
        panel.add(new JLabel("Seuil:"));
        panel.add(txtSeuil);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Modifier Produit: " + nom,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                produit.setNom(txtNom.getText());
                produit.setPrix(Double.parseDouble(txtPrix.getText()));
                produit.setQuantite(Integer.parseInt(txtQuantite.getText()));
                produit.setSeuil(Integer.parseInt(txtSeuil.getText()));

                // MAINTENANT CETTE MÉTHODE EXISTE DANS ProduitDao
                produitDao.updateProduit(produit);

                JOptionPane.showMessageDialog(this,
                        "Produit modifié avec succès!");

                rafraichirTable();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Valeurs numériques invalides!",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerProduit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un produit à supprimer.",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        String nom = (String) model.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir supprimer ce produit?\n\n"
                + "ID: " + id + "\n"
                + "Nom: " + nom,
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // MAINTENANT CETTE MÉTHODE EXISTE
            produitDao.supprimerProduit(id);

            JOptionPane.showMessageDialog(this,
                    "Produit supprimé avec succès!");

            rafraichirTable();
        }
    }

    public void rafraichirTable() {
        // Afficher un indicateur de chargement
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            model.setRowCount(0); // Vider la table

            // MAINTENANT ON UTILISE LA VRAIE MÉTHODE DE LA BASE
            List<Produit> produits = produitDao.tousLesProduits();

            if (produits.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Aucun produit dans la base de données.\n"
                        + "Utilisez le bouton 'Ajouter' pour créer un produit.",
                        "Base vide",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // Ajouter chaque produit depuis la base
            for (Produit p : produits) {
                String statut;
                if (p.getQuantite() == 0) {
                    statut = "❌ Rupture";
                } else if (p.getQuantite() <= p.getSeuil()) {
                    statut = "⚠️ Stock faible";
                } else {
                    statut = "✅ En stock";
                }

                model.addRow(new Object[]{
                    p.getId(),
                    p.getNom(),
                    String.format("%.2f", p.getPrix()),
                    p.getQuantite(),
                    p.getSeuil(),
                    statut
                });
            }

            // Mettre à jour le titre avec le nombre de produits
            setTitle("Gestion de Stock - " + model.getRowCount() + " produits");

            // Avertir si stock faible
            int stockFaible = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                int quantite = (int) model.getValueAt(i, 3);
                int seuil = (int) model.getValueAt(i, 4);
                if (quantite <= seuil) {
                    stockFaible++;
                }
            }

            if (stockFaible > 0) {
                table.getTableHeader().setBackground(new Color(255, 200, 200));
            } else {
                table.getTableHeader().setBackground(UIManager.getColor("TableHeader.background"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement des produits:\n" + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
}
