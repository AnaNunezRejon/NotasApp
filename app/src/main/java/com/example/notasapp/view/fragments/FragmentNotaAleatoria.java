package com.example.notasapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notasapp.R;

public class FragmentNotaAleatoria extends Fragment {

    private static final String ARG_TITULO = "titulo";
    private static final String ARG_DESC = "descripcion";

    private String titulo;
    private String descripcion;

    public FragmentNotaAleatoria() {}

    public static FragmentNotaAleatoria newInstance(String titulo, String descripcion) {
        FragmentNotaAleatoria fragment = new FragmentNotaAleatoria();
        Bundle args = new Bundle();
        args.putString(ARG_TITULO, titulo);
        args.putString(ARG_DESC, descripcion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nota_aleatoria, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            titulo = getArguments().getString(ARG_TITULO);
            descripcion = getArguments().getString(ARG_DESC);
        }

        TextView txtTitulo = view.findViewById(R.id.txtTituloAleatoria);
        TextView txtDescripcion = view.findViewById(R.id.txtDescAleatoria);
        ImageView btnVolver = view.findViewById(R.id.btnVolver);
        Button btnVolverNotas = view.findViewById(R.id.btnVolverNotas);

        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);

        // Flecha atrás
        btnVolver.setOnClickListener(v -> requireActivity().onBackPressed());

        // Botón volver a la lista
        btnVolverNotas.setOnClickListener(v -> requireActivity().onBackPressed());
    }
}
