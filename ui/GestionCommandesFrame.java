package pharmacie.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pharmacie.dao.CommandeDao;
import pharmacie.model.Commande;

public class GestionCommandesFrame extends JFrame {

    private CommandeDao commandeDao = new CommandeDao();
    private DefaultTableModel model;
    private JTable table;

    public GestionCommandesFrame() {
        setTitle("Gestion Commandes Fournisseurs");
        setSize(900, 500);
        setLocationRelativeTo(null);

        String[] cols = {"ID", "Date", "Statut", "Fournisseur", "Total"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Nouvelle commande");
        JButton btnVoir = new JButton("Voir détails");
        JButton btnRecevoir = new JButton("Marquer reçue");
        JButton btnAnnuler = new JButton("Annuler");
        JButton btnFermer = new JButton("Fermer");

        btnAjouter.addActionListener(e -> {
            AjoutCommandeDialog dlg = new AjoutCommandeDialog(this);
            dlg.setVisible(true);
            rafraichir();
        });

        btnVoir.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez une commande"); return; }
            int id = (int) model.getValueAt(sel, 0);
            Commande c = commandeDao.trouverCommande(id);
            if (c == null) { JOptionPane.showMessageDialog(this, "Commande introuvable"); return; }
            JOptionPane.showMessageDialog(this, c.toString() + "\nLignes:\n" + c.getLignesCommande());
        });

        btnRecevoir.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez une commande"); return; }
            int id = (int) model.getValueAt(sel, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Recevoir la commande ID " + id + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                commandeDao.recevoirCommande(id);
                JOptionPane.showMessageDialog(this, "Commande reçue — stocks mis à jour.");
                rafraichir();
            }
        });

        btnAnnuler.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez une commande"); return; }
            int id = (int) model.getValueAt(sel, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Annuler la commande ID " + id + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                commandeDao.modifierStatut(id, "ANNULEE");
                rafraichir();
            }
        });

        btnFermer.addActionListener(e -> dispose());

        buttons.add(btnAjouter);
        buttons.add(btnVoir);
        buttons.add(btnRecevoir);
        buttons.add(btnAnnuler);
        buttons.add(btnFermer);

        add(scroll, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        rafraichir();
        setVisible(true);
    }

    private void rafraichir() {
        model.setRowCount(0);
        List<Commande> list = commandeDao.toutesLesCommandes();
        for (Commande c : list) {
            model.addRow(new Object[]{
                    c.getIdCommande(),
                    c.getDateCommande(),
                    c.getStatut(),
                    c.getFournisseur() != null ? c.getFournisseur().getNom() : "N/A",
                    String.format("%.2f", c.calculerTotal())
            });
        }
    }
}