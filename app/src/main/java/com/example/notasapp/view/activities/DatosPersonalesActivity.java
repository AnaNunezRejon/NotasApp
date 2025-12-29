package com.example.notasapp.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notasapp.R;

public class DatosPersonalesActivity extends AppCompatActivity {

    private EditText etNombre, etApellidos, etFecha, etEmail;
    private Button btnGuardar;
    private ImageView btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etFecha = findViewById(R.id.etFecha);
        etEmail = findViewById(R.id.etEmail);
        btnGuardar = findViewById(R.id.btnGuardarDatos);
        btnVolver = findViewById(R.id.btnVolverDatos);

        cargarDatosUsuario();

        btnGuardar.setOnClickListener(v -> guardarDatosUsuario());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void cargarDatosUsuario() {

        SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);

        etNombre.setText(prefs.getString("nombre", ""));
        etApellidos.setText(prefs.getString("apellidos", ""));
        etFecha.setText(prefs.getString("fecha", ""));
        etEmail.setText(prefs.getString("correo", ""));
    }

    private void guardarDatosUsuario() {

        SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);

        prefs.edit()
                .putString("nombre", etNombre.getText().toString().trim())
                .putString("apellidos", etApellidos.getText().toString().trim())
                .putString("fecha", etFecha.getText().toString().trim())
                .putString("correo", etEmail.getText().toString().trim())
                .apply();

        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();
        finish();

        Log.d("USUARIO", getSharedPreferences("usuario", MODE_PRIVATE).getAll().toString());
    }

}
