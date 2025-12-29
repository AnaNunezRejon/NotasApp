package com.example.notasapp.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notasapp.R;
import com.example.notasapp.view.activities.HomeActivity;
import com.example.notasapp.viewmodel.FuncionesAuxiliares;
import com.example.notasapp.viewmodel.RegistroViewModel;

public class FragmentRegistroFinal extends Fragment {

    private Button btnEmpezar;
    private RegistroViewModel vm;

    public FragmentRegistroFinal() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_registro_final, container, false);

        btnEmpezar = vista.findViewById(R.id.btnEmpezar);
        vm = new ViewModelProvider(requireActivity()).get(RegistroViewModel.class);

        btnEmpezar.setOnClickListener(v -> guardarYEntrar());

        return vista;
    }

    private void guardarYEntrar() {

        // 1) coger datos del VM
        String nombre = vm.getNombre();
        String apellidos = vm.getApellidos();
        String fecha = vm.getFecha();
        String correo = vm.getCorreo();
        String pass = vm.getContrasena();

        // 2) formatear fecha ANTES de guardar
        String fechaFormateada = FuncionesAuxiliares.formatearFecha(fecha);

        Log.d("REGISTRO_FINAL",
                "nombre=" + nombre +
                        " apellidos=" + apellidos +
                        " fecha=" + fechaFormateada +
                        " correo=" + correo +
                        " pass=" + pass
        );

        // 3) guardar en SharedPreferences
        FuncionesAuxiliares.guardarUsuario(
                requireContext(),
                nombre,
                apellidos,
                fechaFormateada,
                correo,
                pass
        );

        // 4) debug de lo guardado
        SharedPreferences prefs =
                requireContext().getSharedPreferences("usuario", MODE_PRIVATE);

        Log.d("REGISTRO_FINAL_PREFS", prefs.getAll().toString());

        // 5) ir a Home
        Intent intent = new Intent(requireContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
