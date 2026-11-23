package negocio;

import modelo.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorRutinas {
    private List<Ejercicio> bancoEjercicios = new ArrayList<>();
    private Map<String, Rutina> rutinasVigentes = new HashMap<>();

    // REQ-2.1
    public void registrarEjercicio(Ejercicio e) throws ValidacionException {
        if (e.getNombre() == null || e.getNombre().trim().length() < 3 || e.getNombre().trim().length() > 60) {
            throw new ValidacionException("Nombre de ejercicio inválido (3 a 60 caracteres).");
        }
        for (Ejercicio ex : bancoEjercicios) {
            if (ex.getNombre().equalsIgnoreCase(e.getNombre().trim())) {
                throw new ValidacionException("Ya existe un ejercicio con ese nombre.");
            }
        }
        bancoEjercicios.add(e);
    }

    public Ejercicio buscarEjercicio(String id) {
        for (Ejercicio e : bancoEjercicios) {
            if (e.getIdEjercicio().equalsIgnoreCase(id.trim())) return e;
        }
        return null;
    }

    // REQ-2.3
    public List<String> asignarRutina(Rutina rutina) throws ValidacionException {
        if (rutina.getItems().isEmpty()) {
            throw new ValidacionException("La rutina debe contener al menos un ejercicio.");
        }
        Rutina anterior = rutinasVigentes.get(rutina.getIdDeportista());
        if (anterior != null) anterior.setVigente(false);
        rutinasVigentes.put(rutina.getIdDeportista(), rutina);
        return rutina.verificarGruposConsecutivos();
    }

    // REQ-2.2
    public List<String> aplicarSobrecargaProgresiva(String idDeportista, int repsLogradasPromedio, boolean indicadorDescarga) throws ValidacionException {
        Rutina r = rutinasVigentes.get(idDeportista.toUpperCase());
        if (r == null) throw new ValidacionException("El deportista no tiene una rutina vigente asignada.");

        List<String> resultados = new ArrayList<>();
        for (ItemRutina item : r.getItems()) {
            String detalle = item.evaluarSobrecarga(repsLogradasPromedio, indicadorDescarga);
            resultados.add(item.getEjercicio().getNombre() + " -> " + detalle);
        }
        return resultados;
    }

    public Rutina obtenerRutinaVigente(String idDeportista) {
        return rutinasVigentes.get(idDeportista.toUpperCase());
    }

    public List<Ejercicio> listarEjercicios() { return bancoEjercicios; }
}