package interfaz;

import modelo.*;
import negocio.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuConsola {
    private GestorDeportistas gDeportistas;
    private GestorRutinas gRutinas;
    private GestorNutricion gNutricion;
    private GestorProgreso gProgreso;
    private Scanner sc = new Scanner(System.in);

    public MenuConsola(GestorDeportistas gd, GestorRutinas gr, GestorNutricion gn, GestorProgreso gp) {
        this.gDeportistas = gd;
        this.gRutinas = gr;
        this.gNutricion = gn;
        this.gProgreso = gp;
    }

    public void iniciar() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n=== FITCOACH ECUADOR - GESTIÓN DE ENTRENAMIENTO Y NUTRICIÓN ===");
            System.out.println("1. [Mód 1 - REQ-1.1] Registrar Deportista");
            System.out.println("2. [Mód 1 - REQ-1.2] Calcular Gasto Energético (TMB/TDEE)");
            System.out.println("3. [Mód 2 - REQ-2.1/2.3] Ver Banco de Ejercicios y Asignar Rutina");
            System.out.println("4. [Mód 2 - REQ-2.2] Calcular Sobrecarga Progresiva");
            System.out.println("5. [Mód 3 - REQ-3.1] Registrar Alimento Ecuatoriano");
            System.out.println("6. [Mód 3 - REQ-3.2] Generar Distribución de Macronutrientes");
            System.out.println("7. [Mód 4 - REQ-4.1] Registrar Métrica Semanal y Volumen");
            System.out.println("8. [Mód 4 - REQ-4.2] Evaluar Semáforo de Estancamiento");
            System.out.println("9. Listar Deportistas y Catálogo de Alimentos");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            try {
                op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1 -> registrarDeportista();
                    case 2 -> calcularGasto();
                    case 3 -> asignarRutina();
                    case 4 -> sobrecargaProgresiva();
                    case 5 -> registrarAlimento();
                    case 6 -> distribuirMacros();
                    case 7 -> registrarMetrica();
                    case 8 -> evaluarEstancamiento();
                    case 9 -> listarTodo();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error de entrada: " + e.getMessage());
            }
        }
    }

    private void registrarDeportista() throws ValidacionException {
        System.out.print("ID (ej. DEP-02): "); String id = sc.nextLine();
        System.out.print("Cédula (10 dígitos): "); String ced = sc.nextLine();
        System.out.print("Nombres y apellidos: "); String nom = sc.nextLine();
        System.out.print("Fecha nacimiento (AAAA-MM-DD): "); LocalDate fn = LocalDate.parse(sc.nextLine());
        System.out.print("Sexo (MASCULINO/FEMENINO): "); String sex = sc.nextLine();
        System.out.print("Peso (kg): "); double peso = Double.parseDouble(sc.nextLine());
        System.out.print("Talla (cm): "); double talla = Double.parseDouble(sc.nextLine());
        System.out.print("Actividad (SEDENTARIO/LIGERO/MODERADO/INTENSO/MUY_INTENSO): "); String act = sc.nextLine();
        System.out.print("Objetivo (DEFICIT/MANTENIMIENTO/SUPERAVIT): "); String obj = sc.nextLine();

        Deportista d = new Deportista(id, "ENT-01", ced, nom, fn, sex, peso, talla, 18.0, act, obj);
        gDeportistas.registrarDeportista(d);
        System.out.println("Deportista registrado correctamente.");
    }

    private void calcularGasto() throws ValidacionException {
        System.out.print("ID Deportista (ej. DEP-01): "); String id = sc.nextLine();
        PlanNutricional plan = gDeportistas.calcularGastoEnergetico(id, null);
        System.out.println("Cálculo exitoso:\n" + plan);
        if (plan.isReglaSeguridadAplicada()) {
            System.out.println("[ADVERTENCIA]: Se aplicó la regla de seguridad (calorías ajustadas al mínimo de TMB).");
        }
    }

    private void asignarRutina() throws ValidacionException {
        System.out.println("Ejercicios disponibles en banco:");
        for (Ejercicio e : gRutinas.listarEjercicios()) System.out.println("  " + e);
        System.out.print("ID Deportista: "); String idDep = sc.nextLine();
        Deportista d = gDeportistas.buscarPorId(idDep);
        if (d == null) throw new ValidacionException("Deportista no encontrado.");

        Rutina rutina = new Rutina("RUT-" + idDep, idDep, 3);
        System.out.print("ID Ejercicio 1: "); Ejercicio e1 = gRutinas.buscarEjercicio(sc.nextLine());
        System.out.print("Día semana (1-6): "); int dia1 = Integer.parseInt(sc.nextLine());
        System.out.print("Carga inicial (kg): "); double c1 = Double.parseDouble(sc.nextLine());
        if (e1 != null) rutina.agregarItem(new ItemRutina(dia1, e1, 3, 8, 12, c1));

        List<String> adv = gRutinas.asignarRutina(rutina);
        System.out.println("Rutina asignada correctamente.");
        for (String a : adv) System.out.println(a);
    }

    private void sobrecargaProgresiva() throws ValidacionException {
        System.out.print("ID Deportista: "); String id = sc.nextLine();
        System.out.print("Repeticiones logradas en todas las series: "); int reps = Integer.parseInt(sc.nextLine());
        System.out.print("¿Activar indicador de descarga? (true/false): "); boolean desc = Boolean.parseBoolean(sc.nextLine());
        List<String> res = gRutinas.aplicarSobrecargaProgresiva(id, reps, desc);
        for (String s : res) System.out.println("  " + s);
    }

    private void registrarAlimento() throws ValidacionException {
        System.out.print("ID Alimento (ej. ALI-04): "); String id = sc.nextLine();
        System.out.print("Nombre: "); String nom = sc.nextLine();
        System.out.print("Categoría: "); String cat = sc.nextLine();
        System.out.print("Proteínas (g/100g): "); double p = Double.parseDouble(sc.nextLine());
        System.out.print("Carbohidratos (g/100g): "); double c = Double.parseDouble(sc.nextLine());
        System.out.print("Grasas (g/100g): "); double g = Double.parseDouble(sc.nextLine());
        System.out.print("Calorías declaradas (kcal/100g): "); double kcal = Double.parseDouble(sc.nextLine());
        gNutricion.registrarAlimento(new Alimento(id, nom, cat, p, c, g, kcal, true));
        System.out.println("Alimento registrado y validado correctamente.");
    }

    private void distribuirMacros() throws ValidacionException {
        System.out.print("ID Deportista: "); String id = sc.nextLine();
        Deportista d = gDeportistas.buscarPorId(id);
        if (d == null) throw new ValidacionException("Deportista no existe.");
        System.out.print("Número de comidas al día (3 a 6): "); int comidas = Integer.parseInt(sc.nextLine());
        System.out.println(gNutricion.distribuirMacronutrientes(d, gDeportistas.obtenerPlan(id), comidas));
    }

    private void registrarMetrica() throws ValidacionException {
        System.out.print("ID Deportista: "); String id = sc.nextLine();
        Deportista d = gDeportistas.buscarPorId(id);
        if (d == null) throw new ValidacionException("Deportista no existe.");
        System.out.print("Número de semana: "); int sem = Integer.parseInt(sc.nextLine());
        System.out.print("Peso actual (kg): "); double peso = Double.parseDouble(sc.nextLine());
        System.out.print("Cintura (cm): "); double cint = Double.parseDouble(sc.nextLine());
        System.out.print("Volumen semanal levantado (kg): "); double vol = Double.parseDouble(sc.nextLine());
        gProgreso.registrarMetricaSemanal(d, sem, LocalDate.now(), peso, cint, vol);
        System.out.println("Métrica semanal registrada y peso vigente actualizado.");
    }

    private void evaluarEstancamiento() throws ValidacionException {
        System.out.print("ID Deportista: "); String id = sc.nextLine();
        Deportista d = gDeportistas.buscarPorId(id);
        if (d == null) throw new ValidacionException("Deportista no existe.");
        System.out.println(gProgreso.evaluarEstancamiento(d));
    }

    private void listarTodo() {
        System.out.println("\n--- DEPORTISTAS ---");
        for (Deportista d : gDeportistas.listarDeportistas()) System.out.println(d);
        System.out.println("\n--- ALIMENTOS ECUATORIANOS ---");
        for (Alimento a : gNutricion.listarAlimentos()) System.out.println(a);
    }
}
