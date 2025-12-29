package com.example.notasapp.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notasapp.R;

public class CambiarContrasenaActivity extends AppCompatActivity {

    private EditText etActual, etNueva, etRepetir;
    private Button btnGuardar;
    private ImageView btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        etActual = findViewById(R.id.etContrasenaActual);
        etNueva = findViewById(R.id.etContrasenaNueva);
        etRepetir = findViewById(R.id.etContrasenaRepetir);
        btnGuardar = findViewById(R.id.btnGuardarContrasena);
        btnVolver = findViewById(R.id.btnVolverContrasena);

        btnVolver.setOnClickListener(v -> finish());
        btnGuardar.setOnClickListener(v -> cambiarContrasena());
    }

    private void cambiarContrasena() {

        SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);
        String passwordGuardada = prefs.getString("password", "");

        String actual = etActual.getText().toString().trim();
        String nueva = etNueva.getText().toString().trim();
        String repetir = etRepetir.getText().toString().trim();

        if (actual.isEmpty() || nueva.isEmpty() || repetir.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!actual.equals(passwordGuardada)) {
            Toast.makeText(this, "La contraseña actual no es correcta", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nueva.equals(repetir)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        prefs.edit().putString("password", nueva).apply();

        Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
        finish();
    }
}
