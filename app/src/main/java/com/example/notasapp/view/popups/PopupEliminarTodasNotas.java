package com.example.notasapp.view.popups;

import android.app.Dialog;
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
import com.example.notasapp.viewmodel.FuncionesAuxiliares;

public class PopupEliminarTodasNotas extends DialogFragment {

    private Runnable onDeleteAll;

    public static PopupEliminarTodasNotas newInstance(Runnable onDelete) {
        PopupEliminarTodasNotas popup = new PopupEliminarTodasNotas();
        popup.onDeleteAll = onDelete;
        return popup;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(requireContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_eliminar_todas_notas, null);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setWindowAnimations(R.style.DialogSlideAnimation);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(android.view.Gravity.BOTTOM);

        Button btnNo = view.findViewById(R.id.btnNoTodas);
        Button btnSi = view.findViewById(R.id.btnSiTodas);

        btnNo.setOnClickListener(v -> dismiss());

        btnSi.setOnClickListener(v -> {
            FuncionesAuxiliares.borrarTodasLasNotas(requireContext());
            dismiss();
            if (onDeleteAll != null) onDeleteAll.run();
        });

        return dialog;
    }
}
