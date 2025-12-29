package com.example.notasapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.notasapp.R;
import com.example.notasapp.view.fragments.FragmentInicio;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (usuarioLogueado()) {
            // Ir directamente al Home
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            // Mostrar pantalla de inicio
            setContentView(R.layout.activity_main);

            if (savedInstanceState == null) {
                cargarFragment(new FragmentInicio());
            }
        }
    }

    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorPrincipal, fragment)
                .commit();
    }

    private boolean usuarioLogueado() {
        SharedPreferences prefs =
                getSharedPreferences("usuario", MODE_PRIVATE);

        return prefs.getBoolean("logeado", false);
    }
}
