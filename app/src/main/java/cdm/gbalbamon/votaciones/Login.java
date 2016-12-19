package cdm.gbalbamon.votaciones;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

    private EditText editTextDNI;
    private EditText editTextPass;
    private Button buttonLogin;
    private Button buttonResetVote;
    private TextWatcher textWatcherDNI;
    // test
    public int numLaunches = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextDNI = (EditText) findViewById(R.id.etDNI);
        editTextPass = (EditText) findViewById(R.id.etPassword);
        buttonLogin = (Button) findViewById(R.id.btnLogin);
        buttonResetVote = (Button) findViewById(R.id.btnResetVote);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });
        buttonResetVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetVote();
            }
        });

        // region textWatcher for DNI
        textWatcherDNI = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Util.dniOK(editTextDNI.getText().toString())) {
                    editTextDNI.setBackgroundColor(Color.GREEN);
                } else {
                    editTextDNI.setBackgroundColor(Color.RED);
                }
            }
        };
        editTextDNI.addTextChangedListener(textWatcherDNI);
        // endregion

        // test
        SharedPreferences settings = getPreferences(0);
        numLaunches = settings.getInt("numLaunches", numLaunches);
        Toast.makeText(this, "Num launches: " + numLaunches, Toast.LENGTH_SHORT).show();
        numLaunches++;

        // login autocompletion
        editTextDNI.setText("77466497J");
        editTextPass.setText("pepe");
    }

    private void validateLogin() {
        String dni = editTextDNI.getText().toString();
        String pwd = editTextPass.getText().toString();

        SQLiteDatabase bd = new AsistenteBD(this, 1).getReadableDatabase();
        Cursor cursor = bd.rawQuery("select * from usuarios where dni='" + dni + "' and password='" + pwd + "'", null);

        if (cursor.moveToFirst()) {
            cursor = bd.rawQuery("select hasVoted from usuarios where dni='77466497J'", null);
            cursor.moveToFirst();
            int hasVoted = cursor.getInt(0);
            if (hasVoted == 1) {
                Toast.makeText(this, "Este usuario ya ha votado.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Login.this, Votacion.class);
                intent.putExtra("DNI", dni);
                startActivity(intent);
                //Toast.makeText(this, "Login correcto.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "DNI o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        bd.close();
    }

    private void resetVote() {
        SQLite sqlite = new SQLite(this);
        sqlite.open();
        sqlite.resetVote();
        sqlite.resetUsers();
        sqlite.close();
        Toast.makeText(Login.this, "Votación restablecida. ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getPreferences(0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("numLaunches", numLaunches);
        editor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(Login.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
