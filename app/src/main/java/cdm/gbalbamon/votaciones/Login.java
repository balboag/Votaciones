package cdm.gbalbamon.votaciones;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

    private EditText editTextDNI;
    private EditText editTextPass;
    private Button buttonAcceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextDNI = (EditText) findViewById(R.id.etDNI);
        editTextPass = (EditText) findViewById(R.id.etPassword);
        buttonAcceder = (Button) findViewById(R.id.btnAcceder);

        buttonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });

        // DEBUG
        editTextDNI.setText("77466497J");
        editTextPass.setText("pepe");

    }

    public void validateLogin() {
        String dni = editTextDNI.getText().toString();
        String pwd = editTextPass.getText().toString();

        SQLiteDatabase bd = new AsistenteBD(this, 1).getReadableDatabase();
        Cursor filas = bd.rawQuery("select * from usuarios where dni='" + dni + "' and password='" + pwd + "'", null);

        if (filas.moveToFirst()) {
            Toast.makeText(this, "Login correcto.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Votacion.class);
            intent.putExtra("DNI", dni);
            startActivity(intent);
        } else {
            Toast.makeText(this, "DNI o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
        }
    }

}
