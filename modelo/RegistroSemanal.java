package modelo;

import java.time.LocalDate;

public class RegistroSemanal {
    private String idDeportista;
    private int numeroSemana;
    private LocalDate fecha;
    private double pesoRegistrado;
    private double cinturaCm;
    private double volumenTotalKg;

    public RegistroSemanal(String idDeportista, int numeroSemana, LocalDate fecha,
                           double pesoRegistrado, double cinturaCm, double volumenTotalKg) {
        this.idDeportista = idDeportista;
        this.numeroSemana = numeroSemana;
        this.fecha = fecha;
        this.pesoRegistrado = pesoRegistrado;
        this.cinturaCm = cinturaCm;
        this.volumenTotalKg = volumenTotalKg;
    }

    public String getIdDeportista() { return idDeportista; }
    public int getNumeroSemana() { return numeroSemana; }
    public double getPesoRegistrado() { return pesoRegistrado; }
    public double getVolumenTotalKg() { return volumenTotalKg; }
}