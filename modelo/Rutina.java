package modelo;

import java.util.ArrayList;
import java.util.List;

public class Rutina {
    private String idRutina;
    private String idDeportista;
    private int diasPorSemana;
    private boolean vigente;
    private List<ItemRutina> items;

    public Rutina(String idRutina, String idDeportista, int diasPorSemana) {
        this.idRutina = idRutina;
        this.idDeportista = idDeportista;
        this.diasPorSemana = diasPorSemana;
        this.vigente = true;
        this.items = new ArrayList<>();
    }

    public void agregarItem(ItemRutina item) {
        items.add(item);
    }

    public List<String> verificarGruposConsecutivos() {
        List<String> advertencias = new ArrayList<>();
        for (ItemRutina a : items) {
            for (ItemRutina b : items) {
                if (b.getDiaSemana() == a.getDiaSemana() + 1 &&
                        a.getEjercicio().getGrupoMuscular().equals(b.getEjercicio().getGrupoMuscular())) {
                    String msg = "Advertencia: El grupo muscular " + a.getEjercicio().getGrupoMuscular() +
                            " se entrena en días consecutivos (Día " + a.getDiaSemana() + " y Día " + b.getDiaSemana() + ").";
                    if (!advertencias.contains(msg)) advertencias.add(msg);
                }
            }
        }
        return advertencias;
    }

    public String getIdRutina() { return idRutina; }
    public String getIdDeportista() { return idDeportista; }
    public boolean isVigente() { return vigente; }
    public void setVigente(boolean vigente) { this.vigente = vigente; }
    public List<ItemRutina> getItems() { return items; }
}