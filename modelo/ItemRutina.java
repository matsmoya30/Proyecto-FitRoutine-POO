package modelo;

public class ItemRutina {
    private int diaSemana;
    private Ejercicio ejercicio;
    private int series;
    private int repMin;
    private int repMax;
    private double cargaActualKg;
    private int sesionesTopeConsecutivas;
    private int sesionesBajoMinConsecutivas;

    public ItemRutina(int diaSemana, Ejercicio ejercicio, int series, int repMin, int repMax, double cargaActualKg) {
        this.diaSemana = diaSemana;
        this.ejercicio = ejercicio;
        this.series = series;
        this.repMin = repMin;
        this.repMax = repMax;
        this.cargaActualKg = cargaActualKg;
        this.sesionesTopeConsecutivas = 0;
        this.sesionesBajoMinConsecutivas = 0;
    }

    public String evaluarSobrecarga(int repsLogradas, boolean indicadorDescarga) {
        if (indicadorDescarga) {
            this.cargaActualKg = redondear125(this.cargaActualKg * 0.90);
            this.series = Math.max(2, this.series - 1);
            this.sesionesTopeConsecutivas = 0;
            this.sesionesBajoMinConsecutivas = 0;
            return "DESCARGA: Carga al 90% (" + cargaActualKg + " kg) y series reducidas a " + series;
        }

        if (repsLogradas >= repMax) {
            sesionesTopeConsecutivas++;
            sesionesBajoMinConsecutivas = 0;
            if (sesionesTopeConsecutivas >= 2) {
                double factor = ejercicio.getTipo().equals("TREN_INFERIOR") ? 1.05 : 1.025;
                this.cargaActualKg = redondear125(this.cargaActualKg * factor);
                sesionesTopeConsecutivas = 0;
                return "SUBIR: Nueva carga " + cargaActualKg + " kg iniciando en " + repMin + " reps";
            }
            return "MANTENER (1/2 sesiones en tope): Mantener " + cargaActualKg + " kg";
        } else if (repsLogradas < repMin) {
            sesionesBajoMinConsecutivas++;
            sesionesTopeConsecutivas = 0;
            if (sesionesBajoMinConsecutivas >= 2) {
                this.cargaActualKg = redondear125(this.cargaActualKg * 0.95);
                sesionesBajoMinConsecutivas = 0;
                return "BAJAR: Carga reducida 5% a " + cargaActualKg + " kg";
            }
            return "MANTENER (1/2 sesiones bajo mínimo): Mantener " + cargaActualKg + " kg";
        } else {
            sesionesTopeConsecutivas = 0;
            sesionesBajoMinConsecutivas = 0;
            return "MANTENER: Dentro de rango (" + cargaActualKg + " kg)";
        }
    }

    private double redondear125(double valor) {
        return Math.round(valor / 1.25) * 1.25;
    }

    public int getDiaSemana() { return diaSemana; }
    public Ejercicio getEjercicio() { return ejercicio; }
    public int getSeries() { return series; }
    public int getRepMin() { return repMin; }
    public int getRepMax() { return repMax; }
    public double getCargaActualKg() { return cargaActualKg; }
}