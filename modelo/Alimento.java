package modelo;

public class Alimento {
    private String idAlimento;
    private String nombre;
    private String categoria;
    private double proteinas100g;
    private double carbos100g;
    private double grasas100g;
    private double calorias100g;
    private boolean ecuatoriano;

    public Alimento(String idAlimento, String nombre, String categoria, double proteinas100g,
                    double carbos100g, double grasas100g, double calorias100g, boolean ecuatoriano) {
        this.idAlimento = idAlimento;
        this.nombre = nombre;
        this.categoria = categoria.toUpperCase();
        this.proteinas100g = proteinas100g;
        this.carbos100g = carbos100g;
        this.grasas100g = grasas100g;
        this.calorias100g = calorias100g;
        this.ecuatoriano = ecuatoriano;
    }

    public boolean esCaloricamenteConsistente() {
        double calculadas = (4 * proteinas100g) + (4 * carbos100g) + (9 * grasas100g);
        if (calculadas == 0) return calorias100g == 0;
        double diferenciaRelativa = Math.abs(calorias100g - calculadas) / calculadas;
        return diferenciaRelativa <= 0.15; // Tolerancia ±15%
    }

    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return String.format("[%s] %-22s | P: %4.1fg | C: %4.1fg | G: %4.1fg | %5.0f kcal | Ecuatoriano: %s",
                idAlimento, nombre, proteinas100g, carbos100g, grasas100g, calorias100g, ecuatoriano ? "Sí" : "No");
    }
}