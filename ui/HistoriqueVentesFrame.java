package pharmacie.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pharmacie.dao.VenteDao;
import pharmacie.model.Vente;

public class HistoriqueVentesFrame extends JFrame {

    public HistoriqueVentesFrame() {
        setTitle("Historique Ventes");
        setSize(800, 500);
        setLocationRelativeTo(null);

        String[] columns = {"ID", "Date", "Client", "Total"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        VenteDao venteDao = new VenteDao();
        List<Vente> ventes = venteDao.toutesLesVentes();
        for (Vente v : ventes) {
            model.addRow(new Object[]{v.getIdVente(), v.getDateVente(), v.getIdClient(), v.getMontantTotal()});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnFermer = new JButton("Fermer");
        btnFermer.addActionListener(e -> dispose());

        add(scrollPane, BorderLayout.CENTER);
        add(btnFermer, BorderLayout.SOUTH);

        setVisible(true);
    }
}