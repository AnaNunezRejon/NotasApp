package com.example.notasapp.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.notasapp.R;
import com.example.notasapp.view.activities.RegistroActivity;

public class FragmentInicio extends Fragment {

    public FragmentInicio() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        Button btnEntrar = vista.findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), RegistroActivity.class);
            startActivity(i);
        });

        return vista;
    }
}
