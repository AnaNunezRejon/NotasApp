package com.example.notasapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notasapp.R;
import com.example.notasapp.model.Nota;
import com.example.notasapp.viewmodel.FuncionesAuxiliares;

import java.util.ArrayList;
import java.util.UUID;

public class CrearNotaFragment extends Fragment {

    private EditText etTitulo, etDescripcion, etColor;
    private Button btnGuardar;
    private ImageView btnVolver;

    private String colorSeleccionadoHex = "";

    private String[][] coloresNota = {
            {"Rosa", "#F4CCCC"},
            {"Verde", "#B6D7A8"},
            {"Azul", "#C9DAF8"},
            {"Amarillo", "#FFE599"},
            {"Lila", "#D7BCE8"},
            {"Turquesa", "#BEE7E8"},
            {"Naranja", "#F9CB9C"}
    };

    public CrearNotaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crear_nota, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTitulo = view.findViewById(R.id.etTituloNota);
        etDescripcion = view.findViewById(R.id.etDescripcionNota);
        etColor = view.findViewById(R.id.etColorNota);
        btnGuardar = view.findViewById(R.id.btnGuardarNota);
        btnVolver = view.findViewById(R.id.btnVolverCrear);

        btnVolver.setOnClickListener(v -> requireActivity().onBackPressed());
        etColor.setOnClickListener(v -> seleccionarColor());
        btnGuardar.setOnClickListener(v -> guardarNota());
    }

    private void seleccionarColor() {

        String[] nombres = new String[coloresNota.length];

        for (int i = 0; i < coloresNota.length; i++) {
            nombres[i] = coloresNota[i][0];
        }

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Selecciona un color")
                .setItems(nombres, (dialog, which) -> {

                    String nombre = coloresNota[which][0];
                    String hex = coloresNota[which][1];

                    etColor.setText(nombre);
                    colorSeleccionadoHex = hex;

                    aplicarColorAFormulario(hex);
                })
                .show();
    }

    private void aplicarColorAFormulario(String hex) {

        int color = android.graphics.Color.parseColor(hex);

        // Fondo inputs
        etTitulo.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );

        etColor.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );

        etDescripcion.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );

        // Botón guardar
        btnGuardar.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );
    }



    private void guardarNota() {

        String titulo = etTitulo.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String color = colorSeleccionadoHex;

        if (titulo.isEmpty()) {
            Toast.makeText(requireContext(), "Debes escribir un título", Toast.LENGTH_SHORT).show();
            return;
        }

        if (color.isEmpty()) {
            Toast.makeText(requireContext(), "Selecciona un color", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cargar lista actual
        ArrayList<Nota> lista = FuncionesAuxiliares.cargarListaNotas(requireContext());

        // Crear nota nueva
        Nota nueva = new Nota(
                UUID.randomUUID().toString(), // ID único
                titulo,
                descripcion,
                color,
                FuncionesAuxiliares.fechaActual(),
                false // no es nota fija
        );

        lista.add(nueva);

        // Guardar lista actualizada
        FuncionesAuxiliares.guardarListaNotas(requireContext(), lista);

        Toast.makeText(requireContext(), "Nota guardada", Toast.LENGTH_SHORT).show();

        // Volver al Home
        requireActivity().onBackPressed();
    }
}
