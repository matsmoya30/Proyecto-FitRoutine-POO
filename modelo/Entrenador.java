package modelo;

public class Entrenador {
    private String idEntrenador;
    private String nombre;
    private String especialidad;

    public Entrenador(String idEntrenador, String nombre, String especialidad) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public String getIdEntrenador() { return idEntrenador; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
}