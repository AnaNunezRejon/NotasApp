package com.example.notasapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notasapp.R;
import com.example.notasapp.view.activities.RegistroActivity;
import com.example.notasapp.viewmodel.FuncionesAuxiliares;
import com.example.notasapp.viewmodel.RegistroViewModel;

public class FragmentCorreo extends Fragment {

    private EditText etCorreo;
    private Button btnSiguiente;

    private RegistroViewModel vm;

    public FragmentCorreo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_correo, container, false);

        vm = new ViewModelProvider(requireActivity()).get(RegistroViewModel.class);

        etCorreo = vista.findViewById(R.id.etCorreo);
        btnSiguiente = vista.findViewById(R.id.btnSiguiente);

        btnSiguiente.setEnabled(false);

        etCorreo.addTextChangedListener(new SimpleWatcher(this::validar));

        btnSiguiente.setOnClickListener(v -> {

            // ✅ GUARDAR EN VM ANTES DE NAVEGAR
            vm.setCorreo(etCorreo.getText().toString().trim());

            ((RegistroActivity) requireActivity())
                    .cargarFragment(new FragmentRegistroFinal());
        });

        return vista;
    }

    private void validar() {
        String correo = etCorreo.getText().toString();
        boolean okCorreo = FuncionesAuxiliares.validarCorreoSeguro(correo);
        btnSiguiente.setEnabled(okCorreo);
    }


    private static class SimpleWatcher implements android.text.TextWatcher {

        private final Runnable callback;
        SimpleWatcher(Runnable callback) { this.callback = callback; }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { callback.run(); }
        @Override public void afterTextChanged(android.text.Editable s) {}
    }
}
