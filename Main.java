package interfaz;

import modelo.*;
import negocio.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        GestorDeportistas gd = new GestorDeportistas();
        GestorRutinas gr = new GestorRutinas();
        GestorNutricion gn = new GestorNutricion();
        GestorProgreso gp = new GestorProgreso();

        try {
            // Datos iniciales precargados para pruebas inmediatas
            Deportista dep1 = new Deportista("DEP-01", "ENT-01", "1723456789", "Carlos Andrade",
                    LocalDate.of(2001, 5, 10), "MASCULINO", 75.0, 175.0, 16.0, "MODERADO", "DEFICIT");
            gd.registrarDeportista(dep1);
            gd.calcularGastoEnergetico("DEP-01", null);

            Ejercicio ej1 = new Ejercicio("EJ-01", "Press de Banca", "PECHO", "TREN_SUPERIOR", "PESO_LIBRE");
            Ejercicio ej2 = new Ejercicio("EJ-02", "Sentadilla Libre", "PIERNAS", "TREN_INFERIOR", "PESO_LIBRE");
            gr.registrarEjercicio(ej1);
            gr.registrarEjercicio(ej2);

            Rutina rut = new Rutina("RUT-01", "DEP-01", 3);
            rut.agregarItem(new ItemRutina(1, ej1, 3, 8, 12, 60.0));
            rut.agregarItem(new ItemRutina(3, ej2, 4, 8, 12, 80.0));
            gr.asignarRutina(rut);

            gn.registrarAlimento(new Alimento("ALI-01", "Pechuga de Pollo", "PROTEINA", 23.0, 0.0, 1.5, 110.0, true));
            gn.registrarAlimento(new Alimento("ALI-02", "Mote Cocido", "CARBOHIDRATO", 3.5, 22.0, 1.0, 112.0, true));
            gn.registrarAlimento(new Alimento("ALI-03", "Menestra de Lenteja", "PREPARADO", 7.5, 18.0, 2.5, 125.0, true));

            gp.registrarMetricaSemanal(dep1, 1, LocalDate.now().minusDays(14), 75.8, 84.0, 4200.0);
            gp.registrarMetricaSemanal(dep1, 2, LocalDate.now().minusDays(7), 75.4, 83.5, 4250.0);
            gp.registrarMetricaSemanal(dep1, 3, LocalDate.now(), 75.0, 83.0, 4350.0);
        } catch (Exception e) {
            System.out.println("Error al cargar datos iniciales: " + e.getMessage());
        }

        new MenuConsola(gd, gr, gn, gp).iniciar();
    }
}