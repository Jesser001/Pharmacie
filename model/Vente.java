package pharmacie.model;

import java.time.LocalDate;

public class Vente {

    private int idVente;
    private LocalDate dateVente;
    private double montantTotal;
    private int idClient;

    
    public Vente(int idVente, LocalDate dateVente, double montantTotal, int idClient) {
        this.idVente = idVente;
        this.dateVente = dateVente;
        this.montantTotal = montantTotal;
        this.idClient = idClient;
    }

    
    public Vente() {
        
    }

    
    public int getIdVente() {
        return idVente;
    }

    public LocalDate getDateVente() {
        return dateVente;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public int getIdClient() {
        return idClient;
    }

    
    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
