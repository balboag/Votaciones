package cdm.gbalbamon.votaciones;

import android.graphics.Color;

public class Candidato {

    private int id;
    private String nombre;
    private String nombre_partido;
    private int color_partido;

    public Candidato(int id, String nombre, String nombre_partido, int color_partido) {
        this.id = id;
        this.nombre = nombre;
        this.nombre_partido = nombre_partido;
        this.color_partido = color_partido;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombrePartido() {
        return nombre_partido;
    }

    public int getColorPartido() {
        return color_partido;
    }

    @Override
    public String toString() {
        return  getNombre() + " (" + getNombrePartido() + ")";
    }
}
