package negocio;

import modelo.Deportista;
import modelo.RegistroSemanal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorProgreso {
    private List<RegistroSemanal> registros = new ArrayList<>();

    // REQ-4.1
    public void registrarMetricaSemanal(Deportista d, int semana, LocalDate fecha,
                                        double peso, double cintura, double volumenSemanal) throws ValidacionException {
        if (fecha.isAfter(LocalDate.now())) {
            throw new ValidacionException("La fecha de registro no puede ser futura.");
        }
        if (peso < 30 || peso > 300) {
            throw new ValidacionException("El peso debe estar entre 30 y 300 kg.");
        }
        for (RegistroSemanal r : registros) {
            if (r.getIdDeportista().equals(d.getIdDeportista()) && r.getNumeroSemana() == semana) {
                throw new ValidacionException("Ya existe un registro para la semana " + semana + " de este deportista.");
            }
        }
        registros.add(new RegistroSemanal(d.getIdDeportista(), semana, fecha, peso, cintura, volumenSemanal));
        d.actualizarPeso(peso, fecha);
    }

    // REQ-4.2
    public String evaluarEstancamiento(Deportista d) {
        List<RegistroSemanal> hist = new ArrayList<>();
        for (RegistroSemanal r : registros) {
            if (r.getIdDeportista().equals(d.getIdDeportista())) hist.add(r);
        }
        if (hist.size() < 3) {
            return "SEMÁFORO GRIS: Sin datos suficientes (se requieren al menos 3 semanas completas; actuales: " + hist.size() + ").";
        }
        RegistroSemanal s1 = hist.get(hist.size() - 3);
        RegistroSemanal s3 = hist.get(hist.size() - 1);

        double varPesoPct = ((s3.getPesoRegistrado() - s1.getPesoRegistrado()) / s1.getPesoRegistrado()) * 100.0;
        double varVolPct = ((s3.getVolumenTotalKg() - s1.getVolumenTotalKg()) / s1.getVolumenTotalKg()) * 100.0;

        boolean cumplePeso = switch (d.getObjetivo()) {
            case "DEFICIT" -> varPesoPct <= -0.5;
            case "SUPERAVIT" -> varPesoPct >= 0.2 && varPesoPct <= 1.5;
            default -> Math.abs(varPesoPct) <= 1.0;
        };
        boolean cumpleVolumen = varVolPct >= 2.5;

        if (cumplePeso && cumpleVolumen) {
            return String.format("SEMÁFORO VERDE (Óptimo): Variación peso %.2f%% | Volumen +%.2f%%. Continuar plan actual.", varPesoPct, varVolPct);
        } else if (!cumplePeso && !cumpleVolumen) {
            return String.format("SEMÁFORO ROJO (Estancamiento): Peso %.2f%% | Volumen %.2f%%. ACCIÓN: Activar semana de descarga (REQ-2.2) y reajustar calorías 5%%.", varPesoPct, varVolPct);
        } else if (!cumplePeso) {
            return String.format("SEMÁFORO AMARILLO (Precaución por peso %.2f%%): Sugerencia -> Reajustar calorías en 5%% (REQ-1.2).", varPesoPct);
        } else {
            return String.format("SEMÁFORO AMARILLO (Precaución por volumen %.2f%%): Sugerencia -> Revisar descanso y técnica.", varVolPct);
        }
    }
}