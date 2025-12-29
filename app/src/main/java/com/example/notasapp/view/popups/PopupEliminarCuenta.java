package com.example.notasapp.view.popups;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.notasapp.R;
import com.example.notasapp.view.activities.MainActivity;
import com.example.notasapp.viewmodel.FuncionesAuxiliares;

public class PopupEliminarCuenta extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(requireContext());
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.popup_eliminar_cuenta, null);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setWindowAnimations(R.style.DialogSlideAnimation);

        Window window = dialog.getWindow();
        window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        window.setGravity(android.view.Gravity.BOTTOM);

        Button btnNo = view.findViewById(R.id.btnNoCuenta);
        Button btnSi = view.findViewById(R.id.btnSiCuenta);

        btnNo.setOnClickListener(v -> dismiss());

        btnSi.setOnClickListener(v -> {

            // 1️⃣ Borrar notas
            FuncionesAuxiliares.borrarTodasLasNotas(requireContext());

            // 2️⃣ Borrar usuario
            FuncionesAuxiliares.borrarUsuario(requireContext());

            // 3️⃣ Volver a inicio
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            dismiss();
        });

        return dialog;
    }
}
