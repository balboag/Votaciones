package cdm.gbalbamon.votaciones;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLite {
    AsistenteBD sqliteHelper;
    SQLiteDatabase db;

    /**
     * Constructor de clase
     */
    public SQLite(Context context) {
        sqliteHelper = new AsistenteBD(context, 1);
    }

    /**
     * Abre conexion a base de datos
     */
    public void abrir() {
        Log.i("SQLite", "Se abre conexión a la base de datos " + sqliteHelper.getDatabaseName());
        db = sqliteHelper.getWritableDatabase();
    }

    /**
     * Cierra conexion a la base de datos
     */
    public void cerrar() {
        Log.i("SQLite", "Se cierra conexión a la base de datos " + sqliteHelper.getDatabaseName());
        sqliteHelper.close();
    }

    public void restablecerVotos() {
        Cursor filas = db.rawQuery("update candidatos set votos = 0", null);
        filas.moveToFirst();
    }
}
