package cdm.gbalbamon.votaciones;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

public class AsistenteBD extends SQLiteOpenHelper {
    static final String NOMBRE_BD = "db";

    public AsistenteBD(Context context, int version) {
        super(context, NOMBRE_BD, null, version);
    }

    String sql_partidos = "CREATE TABLE if not exists partidos(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, color INTEGER)";
    String sql_candidatos = "CREATE TABLE if not exists candidatos(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, votos INTEGER, id_partido INTEGER FOREING KEY)";
    String sql_usuarios = "CREATE TABLE if not exists usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, dni TEXT, password TEXT, hasVoted INTEGER)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_partidos);
        db.execSQL(sql_candidatos);
        db.execSQL(sql_usuarios);

        // Partidos
        db.execSQL("INSERT INTO partidos VALUES (null, 'PP', " + Color.BLUE + ")");
        db.execSQL("INSERT INTO partidos VALUES (null, 'PSOE', " + Color.RED + ")");
        db.execSQL("INSERT INTO partidos VALUES (null, 'Ciudadanos', " + Color.MAGENTA + ")");
        db.execSQL("INSERT INTO partidos VALUES (null, 'Podemos', " + Color.CYAN + ")");
        // Candidatos
        db.execSQL("INSERT INTO candidatos VALUES (null, 'Mariano Rajoy', 0, 1)");
        db.execSQL("INSERT INTO candidatos VALUES (null, 'Cristina Cifuentes', 0, 1)");
        db.execSQL("INSERT INTO candidatos VALUES (null, 'Dolores Cospedal', 0, 1)");
        db.execSQL("INSERT INTO candidatos VALUES (null, 'Pedro Sánchez', 0, 2)");
        db.execSQL("INSERT INTO candidatos VALUES (null, 'Antonio Hernando', 0, 2)");
        db.execSQL("INSERT INTO candidatos VALUES (null, 'Albert Rivera', 0, 3)");
        db.execSQL("INSERT INTO candidatos VALUES (null, 'Fernando de Páramo', 0, 3)");
        db.execSQL("INSERT INTO candidatos VALUES (null, 'Pablo Iglesias', 0, 4)");
        db.execSQL("INSERT INTO candidatos VALUES (null, 'Pablo Echenique', 0, 4)");
        // Usuarios
        db.execSQL("INSERT INTO usuarios VALUES (null, '77466497J', 'pepe', 0)");
        db.execSQL("INSERT INTO usuarios VALUES (null, '77466497K', 'marco', 0)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
