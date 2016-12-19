package cdm.gbalbamon.votaciones;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class Votacion extends Activity {

    private ArrayList<Candidato> listaCandidatos;
    private Spinner spinnerCandidatos;
    private TextView textViewVoteResults;
    private String dniUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votacion);

        dniUser = getIntent().getStringExtra("DNI");

        Toast.makeText(this, dniUser, Toast.LENGTH_SHORT).show();

        spinnerCandidatos = (Spinner) findViewById(R.id.spCandidatos);

        listaCandidatos = getDatosCandidatos();
        spinnerCandidatos.setAdapter(new AdaptadorCandidatos(this, listaCandidatos));

        textViewVoteResults = (TextView) findViewById(R.id.tvVoteResults);

        FragBoton frg_btn = (FragBoton) (getFragmentManager().findFragmentById(R.id.fragment_btnVotar));
        frg_btn.setOnFragmentInteraction(new FragBoton.OnFragmentInteractionListener() {
            @Override
            public void onClickBotonVotar(int numClicks) {
                vote(numClicks);
            }

            @Override
            public void lastClick() {
                userHasVoted(dniUser);
                showResults();
            }
        }, "Votar", 3);
    }

    public void vote(int numClicks) {
        Candidato candidato = (Candidato) spinnerCandidatos.getSelectedItem();
        SQLiteDatabase bd = new AsistenteBD(this, 1).getReadableDatabase();
        Cursor cursor = bd.rawQuery("update candidatos set votos = votos + 1 where id=" + candidato.getId(), null);
        cursor.moveToFirst();
        cursor.close();
        bd.close();
        Toast.makeText(this, "Registrado voto número " + numClicks + " al candidato " + candidato.getNombre(), Toast.LENGTH_SHORT).show();
    }

    public void userHasVoted(String dni) {
        SQLiteDatabase bd = new AsistenteBD(this, 1).getReadableDatabase();
        Cursor cursor = bd.rawQuery("update usuarios set hasVoted = 1 where dni = '" + dni + "'", null);
        cursor.moveToFirst();
        cursor.close();
        bd.close();
    }

    public void showResults() {
        textViewVoteResults.setText("Resultados:\n");
        SQLiteDatabase bd = new AsistenteBD(this, 1).getReadableDatabase();
        Cursor cursor = bd.rawQuery("select name as nombre_candidato, votos as cantidad_votos from candidatos", null);
        int indexNombreCandidato = cursor.getColumnIndex("nombre_candidato");
        int indexVotosCandidato = cursor.getColumnIndex("cantidad_votos");
        try {
            while (cursor.moveToNext()) {
                String nombreCandidato = cursor.getString(indexNombreCandidato);
                int cantidadVotos = cursor.getInt(indexVotosCandidato);
                String a = textViewVoteResults.getText().toString();
                textViewVoteResults.setText(a + "\n" + nombreCandidato + " (" + cantidadVotos + ")");
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
    }
}
