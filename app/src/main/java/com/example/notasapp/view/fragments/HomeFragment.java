package com.example.notasapp.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notasapp.R;
import com.example.notasapp.model.Nota;
import com.example.notasapp.view.activities.HomeActivity;
import com.example.notasapp.view.adapter.NotasAdapter;
import com.example.notasapp.viewmodel.FuncionesAuxiliares;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    private ImageView btnMenu;
    private TextView txtHola;
    private RecyclerView recyclerNotas;

    // LISTAS
    private ArrayList<Nota> listaNotasCompleta = new ArrayList<>();
    private ArrayList<Nota> listaNotasFiltradaColor = new ArrayList<>();
    private ArrayList<Nota> listaNotasFiltrada = new ArrayList<>();

    private NotasAdapter adapter;
    private String textoBusqueda = "";

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_home, container, false);

        // ---------------- BOTÓN NUEVA NOTA ----------------
        ImageView btnNuevaNota = vista.findViewById(R.id.btnNuevaNota);
        btnNuevaNota.setOnClickListener(v -> abrirCrearNota());

        // ---------------- CABECERA ----------------
        btnMenu = vista.findViewById(R.id.btnMenu);
        txtHola = vista.findViewById(R.id.txtHola);
        recyclerNotas = vista.findViewById(R.id.recyclerNotas);

        // ---------------- FILTROS DE COLOR ----------------
        View cTodos = vista.findViewById(R.id.chipTodos);
        View cRosa = vista.findViewById(R.id.chipRosa);
        View cVerde = vista.findViewById(R.id.chipVerde);
        View cAzul = vista.findViewById(R.id.chipAzul);
        View cAmarillo = vista.findViewById(R.id.chipAmarillo);
        View cLila = vista.findViewById(R.id.chipLila);
        View cTurquesa = vista.findViewById(R.id.chipTurquesa);
        View cNaranja = vista.findViewById(R.id.chipNaranja);

        cTodos.setOnClickListener(v -> mostrarTodasLasNotas());
        cRosa.setOnClickListener(v -> filtrarPorColor("#F4CCCC"));
        cVerde.setOnClickListener(v -> filtrarPorColor("#B6D7A8"));
        cAzul.setOnClickListener(v -> filtrarPorColor("#C9DAF8"));
        cAmarillo.setOnClickListener(v -> filtrarPorColor("#FFE599"));
        cLila.setOnClickListener(v -> filtrarPorColor("#D7BCE8"));
        cTurquesa.setOnClickListener(v -> filtrarPorColor("#BEE7E8"));
        cNaranja.setOnClickListener(v -> filtrarPorColor("#F9CB9C"));

        // ---------------- BUSCADOR ----------------
        EditText etBuscar = vista.findViewById(R.id.etBuscar);
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textoBusqueda = s.toString().toLowerCase();
                aplicarBusqueda();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // ---------------- INICIALIZACIÓN ----------------
        cargarNombreUsuario();
        configurarMenu();
        crearNotaEjemploSiNoExiste();
        mostrarNotas();

        return vista;
    }

    // ============================================================
    //  CARGAR NOMBRE DE USUARIO
    // ============================================================
    private void cargarNombreUsuario() {
        SharedPreferences prefs =
                requireContext().getSharedPreferences("usuario", Context.MODE_PRIVATE);

        String nombre = prefs.getString("nombre", "Usuario");
        txtHola.setText("Hola " + nombre);

        Log.d("USUARIO_HOME", prefs.getAll().toString());
    }


    // ============================================================
    //  ABRIR MENÚ LATERAL
    // ============================================================
    private void configurarMenu() {
        btnMenu.setOnClickListener(v -> {
            HomeActivity act = (HomeActivity) getActivity();
            if (act != null) {
                act.abrirMenu();
            }
        });
    }

    // ============================================================
    //  CREAR NOTA DE EJEMPLO SI NO EXISTE
    // ============================================================
    private void crearNotaEjemploSiNoExiste() {

        ArrayList<Nota> lista = FuncionesAuxiliares.cargarListaNotas(requireContext());

        boolean existe = false;
        for (Nota n : lista) {
            if (n.isEsFija()) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            Nota ejemplo = new Nota(
                    "ejemplo",
                    "Nota de ejemplo",
                    "Aquí podrás ver tus notas cuando las vayas creando.",
                    "#D7BCE8",
                    FuncionesAuxiliares.fechaActual(),
                    true
            );

            lista.add(ejemplo);
            FuncionesAuxiliares.guardarListaNotas(requireContext(), lista);
        }
    }

    // ============================================================
    //  MOSTRAR NOTAS
    // ============================================================
    private void mostrarNotas() {

        listaNotasCompleta = FuncionesAuxiliares.cargarListaNotas(requireContext());
        Collections.reverse(listaNotasCompleta);

        listaNotasFiltradaColor = new ArrayList<>(listaNotasCompleta);
        listaNotasFiltrada = new ArrayList<>(listaNotasCompleta);

        adapter = new NotasAdapter(listaNotasFiltrada, nota -> abrirNota(nota));

        recyclerNotas.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerNotas.setAdapter(adapter);
    }

    // ============================================================
    //  FILTROS
    // ============================================================
    private void mostrarTodasLasNotas() {
        listaNotasFiltradaColor.clear();
        listaNotasFiltradaColor.addAll(listaNotasCompleta);
        aplicarBusqueda();
    }

    private void filtrarPorColor(String hexColor) {

        listaNotasFiltradaColor.clear();

        for (Nota n : listaNotasCompleta) {
            if (n.getColor().equalsIgnoreCase(hexColor)) {
                listaNotasFiltradaColor.add(n);
            }
        }

        aplicarBusqueda();
    }

    private void aplicarBusqueda() {

        listaNotasFiltrada.clear();

        for (Nota n : listaNotasFiltradaColor) {

            boolean coincideTitulo =
                    n.getTitulo().toLowerCase().contains(textoBusqueda);

            boolean coincideDescripcion =
                    n.getDescripcion().toLowerCase().contains(textoBusqueda);

            if (coincideTitulo || coincideDescripcion) {
                listaNotasFiltrada.add(n);
            }
        }

        adapter.notifyDataSetChanged();
    }

    // ============================================================
    //  NAVEGACIÓN
    // ============================================================
    private void abrirNota(Nota n) {

        if (n.isEsFija()) {
            FragmentNotaAleatoria fragment =
                    FragmentNotaAleatoria.newInstance(n.getTitulo(), n.getDescripcion());

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorHome, fragment)
                    .addToBackStack(null)
                    .commit();

        } else {
            EditarNotaFragment fragment = EditarNotaFragment.newInstance(n.getId());

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorHome, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void abrirCrearNota() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorHome, new CrearNotaFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarNombreUsuario();
    }
}


