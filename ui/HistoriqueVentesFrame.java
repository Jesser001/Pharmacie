package pharmacie.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HistoriqueVentesFrame extends JFrame {

    public HistoriqueVentesFrame() {
        setTitle("Historique Ventes");
        setSize(800, 500);
        setLocationRelativeTo(null);

        String[] columns = {"ID", "Date", "Client", "Total"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        model.addRow(new Object[]{1, "2024-01-15", "Client 1", 145.50});
        model.addRow(new Object[]{2, "2024-01-15", "Client 2", 89.99});
        model.addRow(new Object[]{3, "2024-01-16", "Client 3", 1200.75});

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnFermer = new JButton("Fermer");
        btnFermer.addActionListener(e -> dispose());

        add(scrollPane, BorderLayout.CENTER);
        add(btnFermer, BorderLayout.SOUTH);

        setVisible(true);
    }
}
