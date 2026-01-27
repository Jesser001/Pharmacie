package pharmacie.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import pharmacie.dao.CommandeDao;
import pharmacie.dao.FournisseurDao;
import pharmacie.dao.ProduitDao;
import pharmacie.model.Commande;
import pharmacie.model.Fournisseur;
import pharmacie.model.Produit;

public class AjoutCommandeDialog extends JDialog {

    private ProduitDao produitDao = new ProduitDao();
    private FournisseurDao fournisseurDao = new FournisseurDao();
    private CommandeDao commandeDao = new CommandeDao();

    private DefaultTableModel model;

    public AjoutCommandeDialog(JFrame owner) {
        super(owner, true);
        setTitle("Nouvelle commande fournisseur");
        setSize(800, 500);
        setLocationRelativeTo(owner);

        JPanel top = new JPanel(new GridLayout(2, 4, 8, 8));
        List<Fournisseur> fournisseurs = fournisseurDao.tousLesFournisseurs();
        JComboBox<Fournisseur> cbFournisseurs = new JComboBox<>(fournisseurs.toArray(new Fournisseur[0]));
        top.add(new JLabel("Fournisseur:"));
        top.add(cbFournisseurs);

        List<Produit> produits = produitDao.tousLesProduits();
        JComboBox<Produit> cbProduits = new JComboBox<>(produits.toArray(new Produit[0]));
        JTextField txtQte = new JTextField();
        JButton btnAjouterLigne = new JButton("Ajouter ligne");

        top.add(new JLabel("Produit:"));
        top.add(cbProduits);
        top.add(new JLabel("Quantité:"));
        top.add(txtQte);
        top.add(btnAjouterLigne);

        String[] cols = {"ID Produit", "Nom", "Quantité", "Prix Unitaire"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        btnAjouterLigne.addActionListener(e -> {
            Produit p = (Produit) cbProduits.getSelectedItem();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un produit");
                return;
            }
            int q = 0;
            try {
                q = Integer.parseInt(txtQte.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Quantité invalide");
                return;
            }
            boolean exists = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                int id = (int) model.getValueAt(i, 0);
                if (id == p.getIdProduit()) {
                    int prev = (int) model.getValueAt(i, 2);
                    model.setValueAt(prev + q, i, 2);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                model.addRow(new Object[]{p.getIdProduit(), p.getNom(), q, p.getPrix()});
            }
            txtQte.setText("");
        });

        JButton btnValider = new JButton("Valider commande");
        JButton btnAnnuler = new JButton("Annuler");

        btnValider.addActionListener(e -> {
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Ajoutez au moins une ligne");
                return;
            }
            Commande commande = new Commande();
            commande.setStatut("EN_ATTENTE");
            Fournisseur f = (Fournisseur) cbFournisseurs.getSelectedItem();
            if (f != null) {
                commande.setFournisseur(f);
            }
            for (int i = 0; i < model.getRowCount(); i++) {
                int idProd = (int) model.getValueAt(i, 0);
                int qte = (int) model.getValueAt(i, 2);
                Produit p = produitDao.trouverProduit(idProd);
                if (p != null) {
                    commande.ajouterLigneCommande(p, qte);
                }
            }
            commandeDao.ajouterCommande(commande);
            JOptionPane.showMessageDialog(this, "Commande créée (ID: " + commande.getIdCommande() + ")");
            dispose();
        });

        btnAnnuler.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new BorderLayout());
        JPanel actions = new JPanel(new FlowLayout());
        actions.add(btnValider);
        actions.add(btnAnnuler);
        bottom.add(actions, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}
