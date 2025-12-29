package com.example.notasapp.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.notasapp.R;
import com.example.notasapp.view.fragments.HomeFragment;
import com.example.notasapp.view.popups.PopupEliminarCuenta;
import com.example.notasapp.view.popups.PopupEliminarTodasNotas;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView txtMenuNombre;
    private TextView opCerrarSesion;

    // Opciones del menú
    private TextView opDatos, opContrasena, opBorrarNotas, opEliminarCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        txtMenuNombre = findViewById(R.id.txtMenuNombre);

        // ENLAZAR OPCIONES DEL MENÚ
        opDatos = findViewById(R.id.opDatos);
        opContrasena = findViewById(R.id.opContrasena);
        opBorrarNotas = findViewById(R.id.opBorrarNotas);
        opEliminarCuenta = findViewById(R.id.opEliminarCuenta);
        opCerrarSesion = findViewById(R.id.opCerrarSesion);

        opCerrarSesion.setOnClickListener(v -> cerrarSesion());

        SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);
        Log.d("USUARIO_HOME", prefs.getAll().toString());

        cargarNombreUsuario();
        cargarHomeFragment();
        configurarOpcionesMenu();
    }

    private void cargarHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorHome, new HomeFragment())
                .commit();
    }

    private void cargarNombreUsuario() {
        SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);
        String nombre = prefs.getString("nombre", "Usuario");
        txtMenuNombre.setText("Hola " + nombre);
    }

    public void abrirMenu() {
        drawerLayout.openDrawer(findViewById(R.id.menuLateral));
    }

    // -------------------------------------------------------
    // MANEJAR CLICKS DEL MENÚ
    // -------------------------------------------------------
    private void configurarOpcionesMenu() {

        opDatos.setOnClickListener(v -> {
            startActivity(new Intent(this, DatosPersonalesActivity.class));
            drawerLayout.closeDrawers();
        });

        opContrasena.setOnClickListener(v -> {
            startActivity(new Intent(this, CambiarContrasenaActivity.class));
            drawerLayout.closeDrawers();
        });

        opBorrarNotas.setOnClickListener(v -> {
            abrirPopupEliminarTodasNotas();
        });

        opEliminarCuenta.setOnClickListener(v -> {
            PopupEliminarCuenta popup = new PopupEliminarCuenta();
            popup.show(getSupportFragmentManager(), "popupEliminarCuenta");
        });
    }

    // -------------------------------------------------------
    // POPUP ELIMINAR TODAS LAS NOTAS
    // -------------------------------------------------------
    private void abrirPopupEliminarTodasNotas() {

        PopupEliminarTodasNotas popup =
                PopupEliminarTodasNotas.newInstance(() -> {

                    // Después de borrar, recargar HomeFragment
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedorHome, new HomeFragment())
                            .commit();

                    // Cerrar menú
                    drawerLayout.closeDrawers();
                });

        popup.show(getSupportFragmentManager(), "popupTodas");
    }

    private void cerrarSesion() {

        SharedPreferences prefs =
                getApplicationContext().getSharedPreferences("usuario", MODE_PRIVATE);

        prefs.edit()
                .putBoolean("logeado", false)
                .apply();

        Intent intent = new Intent(this, MainActivity.class);

        // 🔒 limpiar backstack
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarNombreUsuario();
    }

}


