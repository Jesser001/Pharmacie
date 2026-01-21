package pharmacie.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionUtilisateursFrame extends JFrame {

    public GestionUtilisateursFrame() {
        setTitle("Gestion Utilisateurs");
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columns = {"ID", "Login", "Role"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        model.addRow(new Object[]{1, "admin", "ADMIN"});
        model.addRow(new Object[]{2, "user1", "USER"});

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnFermer = new JButton("Fermer");

        btnAjouter.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ajouter utilisateur"));
        btnSupprimer.addActionListener(e -> JOptionPane.showMessageDialog(this, "Supprimer utilisateur"));
        btnFermer.addActionListener(e -> dispose());

        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnFermer);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
