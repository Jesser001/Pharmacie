package pharmacie.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import pharmacie.service.RapportsService;

public class RapportsFrame extends JFrame {

    public RapportsFrame() {
        setTitle("Rapports");
        setSize(700, 500);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        RapportsService service = new RapportsService();

        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.add(new JLabel("Rapport Global"), BorderLayout.NORTH);
        JTextArea areaGlobal = new JTextArea(service.rapportComplet(), 20, 50);
        areaGlobal.setEditable(false);
        globalPanel.add(areaGlobal, BorderLayout.CENTER);
        tabbedPane.addTab("Global", globalPanel);

        JPanel caPanel = new JPanel(new BorderLayout());
        caPanel.add(new JLabel("Chiffre d'affaires"), BorderLayout.NORTH);
        JTextArea areaCA = new JTextArea(service.chiffreAffairesTotal(), 10, 40);
        areaCA.setEditable(false);
        caPanel.add(areaCA, BorderLayout.CENTER);
        tabbedPane.addTab("CA", caPanel);

        JPanel stockPanel = new JPanel(new BorderLayout());
        stockPanel.add(new JLabel("État des stocks"), BorderLayout.NORTH);
        JTextArea areaStock = new JTextArea(service.etatDesStocks(), 20, 50);
        areaStock.setEditable(false);
        stockPanel.add(areaStock, BorderLayout.CENTER);
        tabbedPane.addTab("Stocks", stockPanel);

        add(tabbedPane);
        setVisible(true);
    }
}
