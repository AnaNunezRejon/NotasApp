package com.example.notasapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.notasapp.R;
import com.example.notasapp.view.fragments.FragmentNombre;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        if (savedInstanceState == null) {
            cargarFragment(new FragmentNombre());
        }
    }

    public void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorRegistro, fragment)
                .addToBackStack(null)
                .commit();
    }
}
