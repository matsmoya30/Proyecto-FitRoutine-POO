package negocio;

import modelo.Alimento;
import modelo.Deportista;
import modelo.PlanNutricional;
import java.util.ArrayList;
import java.util.List;

public class GestorNutricion {
    private List<Alimento> catalogoAlimentos = new ArrayList<>();

    // REQ-3.1
    public void registrarAlimento(Alimento a) throws ValidacionException {
        for (Alimento ex : catalogoAlimentos) {
            if (ex.getNombre().equalsIgnoreCase(a.getNombre().trim())) {
                throw new ValidacionException("El alimento '" + a.getNombre() + "' ya existe en el catálogo.");
            }
        }
        if (!a.esCaloricamenteConsistente()) {
            throw new ValidacionException("Inconsistencia calórica: las calorías declaradas difieren más del ±15% respecto a 4P + 4C + 9G.");
        }
        catalogoAlimentos.add(a);
    }

    // REQ-3.2
    public String distribuirMacronutrientes(Deportista d, PlanNutricional plan, int comidasDia) throws ValidacionException {
        if (plan == null || plan.getCaloriasObjetivo() < 1000) {
            throw new ValidacionException("Debe calcular primero el gasto energético (mínimo 1000 kcal).");
        }
        if (comidasDia < 3 || comidasDia > 6) {
            throw new ValidacionException("El número de comidas al día debe estar entre 3 y 6.");
        }
        double factorProt = switch (d.getObjetivo()) {
            case "DEFICIT" -> 2.0;
            case "SUPERAVIT" -> 1.8;
            default -> 1.6;
        };
        double pctGrasa = d.getObjetivo().equals("MANTENIMIENTO") ? 0.30 : 0.25;

        double proteinasG = d.getPesoKg() * factorProt;
        double kcalProt = proteinasG * 4.0;
        double kcalGrasa = plan.getCaloriasObjetivo() * pctGrasa;
        double grasasG = kcalGrasa / 9.0;
        double carbohidratosG = (plan.getCaloriasObjetivo() - kcalProt - kcalGrasa) / 4.0;

        plan.asignarMacros(proteinasG, carbohidratosG, grasasG, comidasDia);
        String aviso = (carbohidratosG < 100) ? "\n  [ADVERTENCIA]: Carbohidratos inferiores a 100 g/día." : "";
        return plan.toString() + aviso;
    }

    public List<Alimento> listarAlimentos() { return catalogoAlimentos; }
}