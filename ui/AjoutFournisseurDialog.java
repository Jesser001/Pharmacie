package pharmacie.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pharmacie.dao.FournisseurDao;
import pharmacie.model.Fournisseur;

public class AjoutFournisseurDialog extends JDialog {

    public AjoutFournisseurDialog(JFrame owner, Fournisseur f) {
        super(owner, true);
        setTitle(f == null ? "Ajouter Fournisseur" : "Modifier Fournisseur");
        setSize(400, 180);
        setLocationRelativeTo(owner);

        JPanel p = new JPanel(new GridLayout(3, 2, 8, 8));
        JTextField txtNom = new JTextField();
        JTextField txtContact = new JTextField();

        if (f != null) {
            txtNom.setText(f.getNom());
            txtContact.setText(f.getContact());
        }

        p.add(new JLabel("Nom:"));
        p.add(txtNom);
        p.add(new JLabel("Contact:"));
        p.add(txtContact);

        JButton btnOk = new JButton("OK");
        JButton btnAnnuler = new JButton("Annuler");

        btnOk.addActionListener(e -> {
            String nom = txtNom.getText().trim();
            String contact = txtContact.getText().trim();
            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nom requis");
                return;
            }
            FournisseurDao dao = new FournisseurDao();
            if (f == null) {
                dao.ajouterFournisseur(new Fournisseur(0, nom, contact));
            } else {
                Fournisseur fUpdated = new Fournisseur(f.getIdFournisseur(), nom, contact);
                dao.updateFournisseur(f);
            }
            dispose();
        });

        btnAnnuler.addActionListener(e -> dispose());

        p.add(btnOk);
        p.add(btnAnnuler);

        add(p);
    }
}
