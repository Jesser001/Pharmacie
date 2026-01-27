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

import pharmacie.dao.FournisseurDao;
import pharmacie.model.Fournisseur;

public class GestionFournisseursFrame extends JFrame {

    private FournisseurDao fournisseurDao = new FournisseurDao();
    private DefaultTableModel model;
    private JTable table;

    public GestionFournisseursFrame() {
        setTitle("Gestion Fournisseurs");
        setSize(700, 450);
        setLocationRelativeTo(null);

        String[] cols = {"ID", "Nom", "Contact"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnFermer = new JButton("Fermer");

        btnAjouter.addActionListener(e -> {
            AjoutFournisseurDialog dlg = new AjoutFournisseurDialog(this, null);
            dlg.setVisible(true);
            rafraichir();
        });

        btnModifier.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez un fournisseur"); return; }
            int id = (int) model.getValueAt(sel, 0);
            Fournisseur f = fournisseurDao.trouverFournisseur(id);
            if (f == null) { JOptionPane.showMessageDialog(this, "Fournisseur introuvable"); return; }
            AjoutFournisseurDialog dlg = new AjoutFournisseurDialog(this, f);
            dlg.setVisible(true);
            rafraichir();
        });

        btnSupprimer.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez un fournisseur"); return; }
            int id = (int) model.getValueAt(sel, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Supprimer le fournisseur ID " + id + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                fournisseurDao.supprimerFournisseur(id);
                rafraichir();
            }
        });

        btnFermer.addActionListener(e -> dispose());

        buttons.add(btnAjouter);
        buttons.add(btnModifier);
        buttons.add(btnSupprimer);
        buttons.add(btnFermer);

        add(scroll, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        rafraichir();
        setVisible(true);
    }

    private void rafraichir() {
        model.setRowCount(0);
        List<Fournisseur> list = fournisseurDao.tousLesFournisseurs();
        for (Fournisseur f : list) {
            model.addRow(new Object[]{f.getIdFournisseur(), f.getNom(), f.getContact()});
        }
    }
}