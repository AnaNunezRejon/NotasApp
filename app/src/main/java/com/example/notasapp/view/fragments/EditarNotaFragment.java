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
import com.example.notasapp.view.popups.PopupEliminarNota;
import com.example.notasapp.viewmodel.FuncionesAuxiliares;

import java.util.ArrayList;

public class EditarNotaFragment extends Fragment {

    private static final String ARG_ID = "idNota";

    private String idNota;
    private EditText etTitulo, etDescripcion, etColor;
    private Button btnGuardar, btnEliminar;
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

    public EditarNotaFragment() {}

    public static EditarNotaFragment newInstance(String id) {
        EditarNotaFragment fragment = new EditarNotaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_nota, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTitulo = view.findViewById(R.id.etTituloEditar);
        etDescripcion = view.findViewById(R.id.etDescripcionEditar);
        etColor = view.findViewById(R.id.etColorEditar);
        btnGuardar = view.findViewById(R.id.btnGuardarCambios);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        btnVolver = view.findViewById(R.id.btnVolverEditar);

        if (getArguments() != null) {
            idNota = getArguments().getString(ARG_ID);
        }

        cargarNota();
        configurarEventos();
    }

    private void cargarNota() {

        ArrayList<Nota> lista = FuncionesAuxiliares.cargarListaNotas(requireContext());

        for (Nota n : lista) {
            if (n.getId().equals(idNota)) {

                etTitulo.setText(n.getTitulo());
                etDescripcion.setText(n.getDescripcion());

                colorSeleccionadoHex = n.getColor();
                etColor.setText(nombreDesdeHex(colorSeleccionadoHex));

                aplicarColorAFormulario(colorSeleccionadoHex);

                return;
            }
        }
    }

    private String nombreDesdeHex(String hex) {
        for (String[] c : coloresNota) {
            if (c[1].equalsIgnoreCase(hex)) {
                return c[0];
            }
        }
        return "Color";
    }


    private void aplicarColorVisual(String hex) {
        try {
            int color = android.graphics.Color.parseColor(hex);

            etTitulo.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(color)
            );
            etDescripcion.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(color)
            );
            etColor.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(color)
            );

        } catch (Exception ignored) {}
    }



    private void configurarEventos() {
        btnVolver.setOnClickListener(v -> requireActivity().onBackPressed());

        etColor.setOnClickListener(v -> seleccionarColor());

        btnGuardar.setOnClickListener(v -> guardarCambios());

        btnEliminar.setOnClickListener(v -> abrirPopupEliminar());
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

        etTitulo.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );

        etColor.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );

        etDescripcion.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );

        btnGuardar.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );
    }



    private void guardarCambios() {

        ArrayList<Nota> lista = FuncionesAuxiliares.cargarListaNotas(requireContext());

        for (Nota n : lista) {
            if (n.getId().equals(idNota)) {

                n.setTitulo(etTitulo.getText().toString());
                n.setDescripcion(etDescripcion.getText().toString());
                n.setColor(colorSeleccionadoHex);

                break;
            }
        }

        FuncionesAuxiliares.guardarListaNotas(requireContext(), lista);

        Toast.makeText(requireContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
        requireActivity().onBackPressed();
    }


    private void abrirPopupEliminar() {
        PopupEliminarNota popup = PopupEliminarNota.newInstance(idNota, () -> {
            requireActivity().onBackPressed(); // volver al home
        });

        popup.show(getParentFragmentManager(), "popupEliminar");
    }

}
