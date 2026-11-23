package modelo;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Deportista {
    private String idDeportista;
    private String idEntrenador;
    private String cedula;
    private String nombres;
    private LocalDate fechaNacimiento;
    private String sexo; // MASCULINO o FEMENINO
    private double pesoKg;
    private double tallaCm;
    private double porcentajeGrasa;
    private String nivelActividad; // SEDENTARIO, LIGERO, MODERADO, INTENSO, MUY_INTENSO
    private String objetivo; // DEFICIT, MANTENIMIENTO, SUPERAVIT
    private boolean activo;
    private List<String> historialPesos;

    public Deportista(String idDeportista, String idEntrenador, String cedula, String nombres,
                      LocalDate fechaNacimiento, String sexo, double pesoKg, double tallaCm,
                      double porcentajeGrasa, String nivelActividad, String objetivo) {
        this.idDeportista = idDeportista;
        this.idEntrenador = idEntrenador;
        this.cedula = cedula;
        this.nombres = nombres;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo.toUpperCase();
        this.pesoKg = pesoKg;
        this.tallaCm = tallaCm;
        this.porcentajeGrasa = porcentajeGrasa;
        this.nivelActividad = nivelActividad.toUpperCase();
        this.objetivo = objetivo.toUpperCase();
        this.activo = true;
        this.historialPesos = new ArrayList<>();
        this.historialPesos.add(LocalDate.now() + ": " + pesoKg + " kg");
    }

    public int calcularEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public void actualizarPeso(double nuevoPeso, LocalDate fecha) {
        this.pesoKg = nuevoPeso;
        this.historialPesos.add(fecha + ": " + nuevoPeso + " kg");
    }

    public String getIdDeportista() { return idDeportista; }
    public String getIdEntrenador() { return idEntrenador; }
    public String getCedula() { return cedula; }
    public String getNombres() { return nombres; }
    public String getSexo() { return sexo; }
    public double getPesoKg() { return pesoKg; }
    public double getTallaCm() { return tallaCm; }
    public String getNivelActividad() { return nivelActividad; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo.toUpperCase(); }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public List<String> getHistorialPesos() { return historialPesos; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Cédula: %s | Edad: %d | Peso: %.1f kg | Talla: %.0f cm | Obj: %s | %s",
                idDeportista, nombres, cedula, calcularEdad(), pesoKg, tallaCm, objetivo, activo ? "ACTIVO" : "INACTIVO");
    }
}