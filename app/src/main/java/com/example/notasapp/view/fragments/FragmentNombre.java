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

public class FragmentNombre extends Fragment {

    private EditText etNombre, etApellidos, etFecha;
    private Button btnSiguiente;

    private RegistroViewModel vm;

    public FragmentNombre() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_nombre, container, false);

        vm = new ViewModelProvider(requireActivity()).get(RegistroViewModel.class);

        etNombre = vista.findViewById(R.id.etNombre);
        etApellidos = vista.findViewById(R.id.etApellidos);
        etFecha = vista.findViewById(R.id.etFecha);
        btnSiguiente = vista.findViewById(R.id.btnSiguiente);

        btnSiguiente.setEnabled(false);

        etNombre.addTextChangedListener(new SimpleWatcher(this::validar));
        etApellidos.addTextChangedListener(new SimpleWatcher(this::validar));
        etFecha.addTextChangedListener(new SimpleWatcher(this::validar));

        btnSiguiente.setOnClickListener(v -> {

            // ✅ GUARDAR EN VM ANTES DE NAVEGAR
            vm.setNombre(etNombre.getText().toString().trim());
            vm.setApellidos(etApellidos.getText().toString().trim());
            vm.setFecha(etFecha.getText().toString().trim());

            ((RegistroActivity) requireActivity())
                    .cargarFragment(new FragmentContrasena());
        });

        return vista;
    }

    private void validar() {

        String nombre = etNombre.getText().toString();
        String apellidos = etApellidos.getText().toString();
        String fecha = etFecha.getText().toString();

        boolean okNombre = FuncionesAuxiliares.validarNombreSeguro(nombre);
        boolean okApellidos = FuncionesAuxiliares.validarNombreSeguro(apellidos);
        boolean okFecha = FuncionesAuxiliares.validarFecha(fecha);

        if (okNombre && okApellidos && okFecha) {
            btnSiguiente.setEnabled(true);
        } else {
            btnSiguiente.setEnabled(false);
        }
    }

    private static class SimpleWatcher implements android.text.TextWatcher {

        private final Runnable callback;
        SimpleWatcher(Runnable callback) { this.callback = callback; }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { callback.run(); }
        @Override public void afterTextChanged(android.text.Editable s) {}
    }



}
