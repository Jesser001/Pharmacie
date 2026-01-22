package pharmacie.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pharmacie.dao.ProduitDao;
import pharmacie.model.Produit;

public class AjoutProduitFrame extends JDialog {

    public AjoutProduitFrame(JFrame parent) {
        super(parent, "Ajouter Produit", true);

        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Nom:"));
        JTextField txtNom = new JTextField();
        panel.add(txtNom);

        panel.add(new JLabel("Prix:"));
        JTextField txtPrix = new JTextField();
        panel.add(txtPrix);

        panel.add(new JLabel("Quantite:"));
        JTextField txtQte = new JTextField();
        panel.add(txtQte);

        panel.add(new JLabel("Seuil:"));
        JTextField txtSeuil = new JTextField();
        panel.add(txtSeuil);

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnAnnuler = new JButton("Annuler");

        btnAjouter.addActionListener(e -> {
            try {
                String nom = txtNom.getText().trim();
                double prix = Double.parseDouble(txtPrix.getText());
                int qte = Integer.parseInt(txtQte.getText());
                int seuil = Integer.parseInt(txtSeuil.getText());

                
                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Le nom est obligatoire!");
                    return;
                }

                if (prix <= 0 || qte < 0 || seuil < 0) {
                    JOptionPane.showMessageDialog(this, "Les valeurs doivent être positives!");
                    return;
                }

                
                Produit produit = new Produit(0, nom, prix, qte, seuil);

               
                ProduitDao produitDao = new ProduitDao();
                produitDao.ajouterProduit(produit);

                JOptionPane.showMessageDialog(this, "Produit ajouté à la base de données!");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres valides!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        }); 

        btnAnnuler.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnAnnuler);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
