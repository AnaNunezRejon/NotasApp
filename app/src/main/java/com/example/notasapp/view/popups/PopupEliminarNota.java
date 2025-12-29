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

public class PopupEliminarNota extends DialogFragment {

    private String idNota;
    private Runnable onDelete;

    public static PopupEliminarNota newInstance(String id, Runnable onDeleteAction) {
        PopupEliminarNota popup = new PopupEliminarNota();
        popup.idNota = id;
        popup.onDelete = onDeleteAction;
        return popup;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(requireContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_eliminar_nota, null);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        // Fondo transparente del popup
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Animación estilo iOS/Figma: desde abajo
        dialog.getWindow().setWindowAnimations(R.style.DialogSlideAnimation);

        // Que aparezca pegado abajo
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(android.view.Gravity.BOTTOM);

        Button btnNo = view.findViewById(R.id.btnNo);
        Button btnSi = view.findViewById(R.id.btnSi);

        btnNo.setOnClickListener(v -> dismiss());

        btnSi.setOnClickListener(v -> {
            FuncionesAuxiliares.eliminarNota(requireContext(), idNota);
            dismiss();
            if (onDelete != null) onDelete.run();
        });

        return dialog;
    }
}
