package cdm.gbalbamon.votaciones;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class Votacion extends Activity {

    private Spinner spinnerCandidatos;
    private TextView textViewResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votacion);
        spinnerCandidatos = (Spinner) findViewById(R.id.spCandidatos);
        spinnerCandidatos.setAdapter(new AdaptadorCandidatos(this, getDatosCandidatos()));

        textViewResultados = (TextView) findViewById(R.id.tvResultados);

        frag_boton frg_btn = (frag_boton) (getFragmentManager().findFragmentById(R.id.fragment_btnVotar));
        frg_btn.setOnFragmentInteraction(new frag_boton.OnFragmentInteractionListener() {
            @Override
            public void onClickBotonVotar() {
                votar();
            }

            @Override
            public void heAcabado() {
                mostrarResultados();
            }
        }, "Votar", 3);
    }

    public void votar() {
        int idCandidato = spinnerCandidatos.getFirstVisiblePosition() + 1;
        SQLiteDatabase bd = new AsistenteBD(this, 1).getReadableDatabase();
        Cursor cursor = bd.rawQuery("update candidatos set votos = votos + 1 where id=" + idCandidato, null);
        cursor.moveToFirst();
        cursor.close();
        bd.close();
    }

    public void mostrarResultados() {
        textViewResultados.setText("Resultados:\n");
        SQLiteDatabase bd = new AsistenteBD(this, 1).getReadableDatabase();
        Cursor cursor = bd.rawQuery("select name as nombre_candidato, votos as cantidad_votos from candidatos", null);
        int indexNombreCandidato = cursor.getColumnIndex("nombre_candidato");
        int indexVotosCandidato = cursor.getColumnIndex("cantidad_votos");
        try {
            while (cursor.moveToNext()) {
                String nombreCandidato = cursor.getString(indexNombreCandidato);
                int cantidadVotos = cursor.getInt(indexVotosCandidato);
                String a = textViewResultados.getText().toString();
                textViewResultados.setText(a + "\n" + nombreCandidato + " (" + cantidadVotos + ")");
            }
        } finally {
            cursor.close();
        }
        bd.close();
    }

    public ArrayList<Candidato> getDatosCandidatos() {
        ArrayList<Candidato> listaCandidatos = new ArrayList<>();
        SQLiteDatabase bd = new AsistenteBD(this, 1).getReadableDatabase();
        Cursor cursor = bd.rawQuery("select candidatos.id as id_candidato, candidatos.name as nombre_candidato, partidos.name as nombre_partido, partidos.color as color_partido from candidatos " +
                "inner join partidos where candidatos.id_partido=partidos.id", null);
        int indexIdCandidato = cursor.getColumnIndex("id_candidato");
        int indexNombreCandidato = cursor.getColumnIndex("nombre_candidato");
        int indexNombrePartido = cursor.getColumnIndex("nombre_partido");
        int indexColorPartido = cursor.getColumnIndex("color_partido");
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(indexIdCandidato);
                String nombre = cursor.getString(indexNombreCandidato);
                String partido = cursor.getString(indexNombrePartido);
                int color = cursor.getInt(indexColorPartido);
                listaCandidatos.add(new Candidato(id, nombre, partido, color));
            }
        } finally {
            cursor.close();
        }
        bd.close();
        return listaCandidatos;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // remove animation  http://stackoverflow.com/a/32347183c
        overridePendingTransition(0, 0);
    }
}
