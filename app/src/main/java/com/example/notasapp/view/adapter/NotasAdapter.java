package com.example.notasapp.view.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notasapp.R;
import com.example.notasapp.model.Nota;

import java.util.ArrayList;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.ViewHolder> {

    private ArrayList<Nota> lista;
    private OnNotaClickListener listener;

    public interface OnNotaClickListener {
        void onNotaClick(Nota n);
    }

    public NotasAdapter(ArrayList<Nota> lista, OnNotaClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nota, parent, false);

        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Nota n = lista.get(position);

        holder.titulo.setText(n.getTitulo());
        holder.descripcion.setText(n.getDescripcion());
        holder.fecha.setText(n.getFecha());

        // ✅ APLICAR COLOR REAL DE LA NOTA
        if (n.getColor() != null && !n.getColor().isEmpty()) {
            holder.contenedorNota.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor(n.getColor()))
            );
        }

        holder.itemView.setOnClickListener(v -> listener.onNotaClick(n));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, descripcion, fecha;
        View contenedorNota;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.txtTituloNota);
            descripcion = itemView.findViewById(R.id.txtDescripcionNota);
            fecha = itemView.findViewById(R.id.txtFechaNota);
            contenedorNota = itemView.findViewById(R.id.contenedorNota);
        }
    }
}
