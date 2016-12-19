package cdm.gbalbamon.votaciones;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLite {
    AsistenteBD sqliteHelper;
    SQLiteDatabase db;

    public SQLite(Context context) {
        sqliteHelper = new AsistenteBD(context, 1);
    }

    public void open() {
        Log.i("SQLite", "Se abre conexión a la base de datos " + sqliteHelper.getDatabaseName());
        db = sqliteHelper.getWritableDatabase();
    }

    public void close() {
        Log.i("SQLite", "Se cierra conexión a la base de datos " + sqliteHelper.getDatabaseName());
        sqliteHelper.close();
    }

    public void resetVote() {
        Cursor cursor = db.rawQuery("update candidatos set votos = 0", null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void resetUsers() {
        Cursor cursor = db.rawQuery("update usuarios set hasVoted = 0", null);
        cursor.moveToFirst();
        cursor.close();
    }
}
