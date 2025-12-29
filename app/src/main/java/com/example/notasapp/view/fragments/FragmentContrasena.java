package com.example.notasapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notasapp.R;
import com.example.notasapp.view.activities.RegistroActivity;
import com.example.notasapp.viewmodel.FuncionesAuxiliares;
import com.example.notasapp.viewmodel.RegistroViewModel;

public class FragmentContrasena extends Fragment {

    private EditText etPass1, etPass2;
    private Button btnSiguiente;

    private RegistroViewModel vm;

    public FragmentContrasena() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_contrasena, container, false);

        vm = new ViewModelProvider(requireActivity()).get(RegistroViewModel.class);

        etPass1 = vista.findViewById(R.id.etPass1);
        etPass2 = vista.findViewById(R.id.etPass2);
        btnSiguiente = vista.findViewById(R.id.btnSiguiente);

        // Estado inicial
        btnSiguiente.setEnabled(false);
        btnSiguiente.setBackgroundTintList(
                ContextCompat.getColorStateList(requireContext(), R.color.morado_claro)
        );

        etPass1.addTextChangedListener(new SimpleWatcher(this::validar));
        etPass2.addTextChangedListener(new SimpleWatcher(this::validar));

        btnSiguiente.setOnClickListener(v -> {

            // ✅ GUARDAR CONTRASEÑA EN VIEWMODEL
            vm.setContrasena(etPass1.getText().toString().trim());

            ((RegistroActivity) requireActivity())
                    .cargarFragment(new FragmentCorreo());
        });

        return vista;
    }

    // ======================================================
    // VALIDACIÓN COMPLETA
    // ======================================================
    private void validar() {

        String p1 = etPass1.getText().toString().trim();
        String p2 = etPass2.getText().toString().trim();

        boolean okPass = FuncionesAuxiliares.validarContrasena(p1);
        boolean okIguales = FuncionesAuxiliares.contrasenasIguales(p1, p2);

        aplicarColor(etPass1, okPass);
        aplicarColor(etPass2, okIguales);

        if (okPass && okIguales) {
            btnSiguiente.setEnabled(true);
            btnSiguiente.setBackgroundTintList(
                    ContextCompat.getColorStateList(requireContext(), R.color.morado_principal)
            );
        } else {
            btnSiguiente.setEnabled(false);
            btnSiguiente.setBackgroundTintList(
                    ContextCompat.getColorStateList(requireContext(), R.color.morado_claro)
            );
        }
    }

    private void aplicarColor(EditText campo, boolean ok) {
        if (ok) {
            campo.setBackgroundTintList(
                    ContextCompat.getColorStateList(requireContext(), R.color.verde_validado)
            );
        } else {
            campo.setBackgroundTintList(
                    ContextCompat.getColorStateList(requireContext(), R.color.rojo_error)
            );
        }
    }

    private static class SimpleWatcher implements android.text.TextWatcher {

        private final Runnable callback;

        SimpleWatcher(Runnable callback) {
            this.callback = callback;
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            callback.run();
        }
        @Override public void afterTextChanged(android.text.Editable s) {}
    }
}
