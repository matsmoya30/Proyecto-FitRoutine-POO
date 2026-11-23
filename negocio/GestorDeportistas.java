package negocio;

import modelo.Deportista;
import modelo.PlanNutricional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorDeportistas {
    private List<Deportista> deportistas = new ArrayList<>();
    private Map<String, PlanNutricional> planesPorDeportista = new HashMap<>();

    // REQ-1.1
    public void registrarDeportista(Deportista d) throws ValidacionException {
        if (d.getNombres() == null || d.getNombres().trim().length() < 2 || d.getNombres().trim().length() > 60) {
            throw new ValidacionException("Nombres inválidos (2 a 60 caracteres).");
        }
        if (d.getCedula() == null || !d.getCedula().matches("\\d{10}")) {
            throw new ValidacionException("La cédula debe tener 10 dígitos.");
        }
        for (Deportista existente : deportistas) {
            if (existente.getCedula().equals(d.getCedula())) {
                throw new ValidacionException("Ya existe un deportista con cédula " + d.getCedula());
            }
        }
        int edad = d.calcularEdad();
        if (edad < 18 || edad > 80) {
            throw new ValidacionException("La edad debe estar entre 18 y 80 años (actual: " + edad + ").");
        }
        if (d.getPesoKg() < 30 || d.getPesoKg() > 300 || d.getTallaCm() < 120 || d.getTallaCm() > 230) {
            throw new ValidacionException("Peso (30-300 kg) o talla (120-230 cm) fuera de rango.");
        }
        deportistas.add(d);
    }

    public Deportista buscarPorId(String id) {
        for (Deportista d : deportistas) {
            if (d.getIdDeportista().equalsIgnoreCase(id.trim())) return d;
        }
        return null;
    }

    // REQ-1.2
    public PlanNutricional calcularGastoEnergetico(String idDeportista, Double ajustePorcentual) throws ValidacionException {
        Deportista d = buscarPorId(idDeportista);
        if (d == null || !d.isActivo()) {
            throw new ValidacionException("Deportista no encontrado o inactivo.");
        }
        double tmbExacta = (10 * d.getPesoKg()) + (6.25 * d.getTallaCm()) - (5 * d.calcularEdad())
                + (d.getSexo().equals("MASCULINO") ? 5 : -161);

        double factor = switch (d.getNivelActividad()) {
            case "SEDENTARIO" -> 1.2;
            case "LIGERO" -> 1.375;
            case "MODERADO" -> 1.55;
            case "INTENSO" -> 1.725;
            case "MUY_INTENSO" -> 1.9;
            default -> throw new ValidacionException("Nivel de actividad no válido.");
        };

        double tdeeExacto = tmbExacta * factor;
        double porcentaje = (ajustePorcentual != null) ? (ajustePorcentual / 100.0)
                : (d.getObjetivo().equals("DEFICIT") ? 0.15 : d.getObjetivo().equals("SUPERAVIT") ? 0.10 : 0.0);

        if (ajustePorcentual != null && (ajustePorcentual < 5 || ajustePorcentual > 25)) {
            throw new ValidacionException("El ajuste porcentual opcional debe estar entre 5% y 25%.");
        }

        double caloriasObj = switch (d.getObjetivo()) {
            case "DEFICIT" -> tdeeExacto * (1.0 - porcentaje);
            case "SUPERAVIT" -> tdeeExacto * (1.0 + porcentaje);
            default -> tdeeExacto;
        };

        boolean reglaSeguridad = false;
        if (caloriasObj < tmbExacta) {
            caloriasObj = tmbExacta;
            reglaSeguridad = true;
        }

        PlanNutricional plan = new PlanNutricional(
                d.getIdDeportista(), (int) Math.round(tmbExacta), (int) Math.round(tdeeExacto),
                (int) Math.round(caloriasObj), reglaSeguridad
        );
        planesPorDeportista.put(d.getIdDeportista(), plan);
        return plan;
    }

    public PlanNutricional obtenerPlan(String idDeportista) {
        return planesPorDeportista.get(idDeportista.toUpperCase());
    }

    public List<Deportista> listarDeportistas() { return deportistas; }
}