package pharmacie.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import pharmacie.model.LigneVente;
import pharmacie.model.Vente;
import pharmacie.model.Produit;
import pharmacie.service.VenteService;
import pharmacie.dao.ProduitDao;

public class NouvelleVenteFrame extends JFrame {

    private DefaultTableModel tableModel;
    private List<LigneVente> lignesVente = new ArrayList<>();
    private double total = 0.0;
    private JLabel lblTotal;

    public NouvelleVenteFrame() {
        setTitle("Nouvelle Vente");
        setSize(1000, 600);
        setLocationRelativeTo(null);

        String[] columns = {"ID Produit", "Nom", "Prix", "Quantité", "Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        inputPanel.add(new JLabel("ID Produit:"));
        JTextField txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Quantité:"));
        JTextField txtQte = new JTextField();
        inputPanel.add(txtQte);

        inputPanel.add(new JLabel("Nom:"));
        JTextField txtNom = new JTextField();
        txtNom.setEditable(false);
        inputPanel.add(txtNom);

        inputPanel.add(new JLabel("Prix:"));
        JTextField txtPrix = new JTextField();
        txtPrix.setEditable(false);
        inputPanel.add(txtPrix);

        JButton btnRechercher = new JButton("🔍 Rechercher");
        btnRechercher.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                ProduitDao produitDao = new ProduitDao();
                Produit p = produitDao.trouverProduit(id);
                if (p != null) {
                    txtNom.setText(p.getNom());
                    txtPrix.setText(String.valueOf(p.getPrix()));
                } else {
                    JOptionPane.showMessageDialog(this, "Produit introuvable");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID invalide");
            }
        });

        JButton btnAjouter = new JButton("➕ Ajouter");
        JButton btnFinaliser = new JButton("✅ Finaliser");
        JButton btnAnnuler = new JButton("❌ Annuler");

        btnAjouter.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                int qte = Integer.parseInt(txtQte.getText());
                String nom = txtNom.getText();
                double prix = Double.parseDouble(txtPrix.getText());
                double sousTotal = prix * qte;

                LigneVente ligne = new LigneVente(0, id, qte, prix);
                lignesVente.add(ligne);
                tableModel.addRow(new Object[]{id, nom, prix, qte, sousTotal});
                total += sousTotal;
                lblTotal.setText("Total: " + String.format("%.2f", total) + " EUR");

                txtId.setText("");
                txtQte.setText("");
                txtNom.setText("");
                txtPrix.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        });

        btnFinaliser.addActionListener(e -> {
            if (lignesVente.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ajoutez des produits");
                return;
            }

            Vente vente = new Vente();
            vente.setDateVente(LocalDate.now());
            vente.setMontantTotal(total);
            vente.setIdClient(1); // TODO: gestion client

            VenteService service = new VenteService();
            service.enregistrerVente(vente, lignesVente);

            // alertes
            ProduitDao produitDao = new ProduitDao();
            List<String> alertes = lignesVente.stream()
                    .map(lv -> produitDao.trouverProduit(lv.getIdProduit()))
                    .filter(p -> p != null && p.getQuantiteStock() <= p.getSeuilAlerte())
                    .map(p -> p.getNom() + " (stock: " + p.getQuantiteStock() + ", seuil: " + p.getSeuilAlerte() + ")")
                    .collect(Collectors.toList());

            String msg = "Vente enregistrée!\n"
                    + "Montant total: " + String.format("%.2f", total) + " EUR\n"
                    + "Nombre d'articles: " + lignesVente.size();

            if (!alertes.isEmpty()) {
                msg += "\n\n⚠️ Produits en dessous du seuil d'alerte:\n" + String.join("\n", alertes);
            }

            JOptionPane.showMessageDialog(this, msg);
            dispose();
        });

        btnAnnuler.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRechercher);
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnFinaliser);
        buttonPanel.add(btnAnnuler);

        JPanel totalPanel = new JPanel();
        lblTotal = new JLabel("Total: 0.00 EUR");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(lblTotal);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(totalPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
