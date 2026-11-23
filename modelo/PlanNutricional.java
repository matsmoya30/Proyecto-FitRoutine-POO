package modelo;

import java.time.LocalDate;

public class PlanNutricional {
    private String idDeportista;
    private LocalDate fecha;
    private int tmb;
    private int tdee;
    private int caloriasObjetivo;
    private boolean reglaSeguridadAplicada;
    private double proteinasG;
    private double carbohidratosG;
    private double grasasG;
    private int comidasDia;

    public PlanNutricional(String idDeportista, int tmb, int tdee, int caloriasObjetivo, boolean reglaSeguridadAplicada) {
        this.idDeportista = idDeportista;
        this.fecha = LocalDate.now();
        this.tmb = tmb;
        this.tdee = tdee;
        this.caloriasObjetivo = caloriasObjetivo;
        this.reglaSeguridadAplicada = reglaSeguridadAplicada;
    }

    public void asignarMacros(double proteinasG, double carbohidratosG, double grasasG, int comidasDia) {
        this.proteinasG = proteinasG;
        this.carbohidratosG = carbohidratosG;
        this.grasasG = grasasG;
        this.comidasDia = comidasDia;
    }

    public int getCaloriasObjetivo() { return caloriasObjetivo; }
    public double getCarbohidratosG() { return carbohidratosG; }
    public boolean isReglaSeguridadAplicada() { return reglaSeguridadAplicada; }

    @Override
    public String toString() {
        String base = String.format("Fecha: %s | TMB: %d kcal | TDEE: %d kcal | Objetivo: %d kcal/día",
                fecha, tmb, tdee, caloriasObjetivo);
        if (comidasDia > 0) {
            base += String.format("\n  -> Macros diarios: %.0fg Prot | %.0fg Carb | %.0fg Grasas (%d comidas/día: %.0fg P / %.0fg C / %.0fg G por comida)",
                    proteinasG, carbohidratosG, grasasG, comidasDia,
                    proteinasG / comidasDia, carbohidratosG / comidasDia, grasasG / comidasDia);
        }
        return base;
    }
}