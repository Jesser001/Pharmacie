package pharmacie.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class RapportsFrame extends JFrame {

    public RapportsFrame() {
        setTitle("Rapports");
        setSize(700, 500);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Journalier
        JPanel dailyPanel = new JPanel();
        dailyPanel.add(new JLabel("Rapport Journalier"));
        dailyPanel.add(new JTextArea("Ventes aujourd'hui: 15\nTotal: 1245.75 EUR", 10, 40));
        tabbedPane.addTab("Journalier", dailyPanel);

        // Tab 2: Mensuel
        JPanel monthlyPanel = new JPanel();
        monthlyPanel.add(new JLabel("Rapport Mensuel"));
        monthlyPanel.add(new JTextArea("Ventes ce mois: 205\nTotal: 17131.50 EUR", 10, 40));
        tabbedPane.addTab("Mensuel", monthlyPanel);

        add(tabbedPane);
        setVisible(true);
    }
}
