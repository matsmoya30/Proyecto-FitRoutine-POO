package modelo;

public class Ejercicio {
    private String idEjercicio;
    private String nombre;
    private String grupoMuscular; // pecho, espalda, hombro, biceps, triceps, piernas, gluteos, core
    private String tipo; // TREN_SUPERIOR, TREN_INFERIOR, CORE
    private String equipo;
    private boolean activo;

    public Ejercicio(String idEjercicio, String nombre, String grupoMuscular, String tipo, String equipo) {
        this.idEjercicio = idEjercicio;
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular.toUpperCase();
        this.tipo = tipo.toUpperCase();
        this.equipo = equipo.toUpperCase();
        this.activo = true;
    }

    public String getIdEjercicio() { return idEjercicio; }
    public String getNombre() { return nombre; }
    public String getGrupoMuscular() { return grupoMuscular; }
    public String getTipo() { return tipo; }
    public String getEquipo() { return equipo; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return String.format("[%s] %-22s | Grupo: %-10s | Tipo: %-13s | Equipo: %s",
                idEjercicio, nombre, grupoMuscular, tipo, equipo);
    }
}