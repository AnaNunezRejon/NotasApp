package com.example.notasapp.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notasapp.model.Nota;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FuncionesAuxiliares {

    // ==========================
    // VALIDAR NOMBRE Y APELLIDOS
    // ==========================
    public static boolean validarTexto(String texto) {

        if (texto == null) return false;

        texto = texto.trim();

        if (texto.length() < 3) {
            return false;
        }

        // Solo letras y espacios
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }

        return true;
    }

    // ==========================
    // VALIDAR FECHA SIMPLE
    // ==========================
    public static boolean validarFecha(String fecha) {

        if (fecha == null) return false;

        fecha = fecha.trim();

        if (fecha.length() < 3) {
            return false;
        }

        // Solo números, / o -
        for (int i = 0; i < fecha.length(); i++) {
            char c = fecha.charAt(i);

            if (!Character.isDigit(c) && c != '/' && c != '-') {
                return false;
            }
        }

        return true;
    }

    // ==========================
// VALIDACIÓN DE CONTRASEÑA
// ==========================
    public static boolean validarContrasena(String pass) {

        if (pass == null) return false;

        pass = pass.trim();

        if (pass.length() < 8) {
            return false;
        }

        boolean tieneMayus = false;
        boolean tieneMinus = false;
        boolean tieneNumero = false;

        // No usar foreach
        for (int i = 0; i < pass.length(); i++) {
            char c = pass.charAt(i);

            if (Character.isUpperCase(c)) {
                tieneMayus = true;
            } else if (Character.isLowerCase(c)) {
                tieneMinus = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            } else {
                return false; // símbolo raro
            }
        }

        if (!tieneMayus) return false;
        if (!tieneMinus) return false;
        if (!tieneNumero) return false;

        return true;
    }

    public static boolean contrasenasIguales(String p1, String p2) {

        if (p1 == null || p2 == null) return false;

        return p1.equals(p2);
    }

    // ==========================
// VALIDAR CORREO ELECTRÓNICO
// ==========================
    public static boolean validarCorreo(String correo) {

        if (correo == null) return false;

        correo = correo.trim();

        if (correo.length() < 5) {
            return false;
        }

        // Debe contener @
        if (!correo.contains("@")) {
            return false;
        }

        // Debe terminar en .com o .es
        if (!correo.endsWith(".com") && !correo.endsWith(".es")) {
            return false;
        }

        // Validar caracteres válidos (solo letras, números, @, punto)
        for (int i = 0; i < correo.length(); i++) {
            char c = correo.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                // OK
            } else if (c == '@' || c == '.' || c == '_' || c == '-') {
                // OK
            } else {
                return false; // símbolo raro
            }
        }

        return true;
    }

    // ==========================
// GUARDAR USUARIO
// ==========================
    public static void guardarUsuario(Context context, String nombre, String apellidos, String fecha, String correo, String pass) {

        SharedPreferences prefs = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("nombre", nombre);
        editor.putString("apellidos", apellidos);
        editor.putString("fecha", fecha);
        editor.putString("correo", correo);
        editor.putString("pass", pass);

        editor.putBoolean("logeado", true);

        editor.apply();
    }

    // ==========================
// GUARDAR LISTA DE NOTAS EN SHAREDPREFERENCES
// ==========================
    public static void guardarListaNotas(Context context, ArrayList<Nota> notas) {

        SharedPreferences prefs = context.getSharedPreferences("notas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        JSONArray array = new JSONArray();

        try {
            for (Nota n : notas) {
                JSONObject obj = new JSONObject();
                obj.put("id", n.getId());
                obj.put("titulo", n.getTitulo());
                obj.put("descripcion", n.getDescripcion());
                obj.put("color", n.getColor());
                obj.put("fecha", n.getFecha());
                obj.put("esFija", n.isEsFija());
                array.put(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        editor.putString("lista", array.toString());
        editor.apply();
    }

    public static ArrayList<Nota> cargarListaNotas(Context context) {

        SharedPreferences prefs = context.getSharedPreferences("notas", Context.MODE_PRIVATE);
        String datos = prefs.getString("lista", null);

        ArrayList<Nota> lista = new ArrayList<>();

        if (datos == null) return lista;

        try {
            JSONArray array = new JSONArray(datos);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                Nota n = new Nota(
                        obj.getString("id"),
                        obj.getString("titulo"),
                        obj.getString("descripcion"),
                        obj.getString("color"),
                        obj.getString("fecha"),
                        obj.getBoolean("esFija")
                );

                lista.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static String fechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    public static void eliminarNota(Context ctx, String id) {
        ArrayList<Nota> lista = cargarListaNotas(ctx);

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equals(id)) {
                lista.remove(i);
                break;
            }
        }

        guardarListaNotas(ctx, lista);
    }

    public static void borrarTodasLasNotas(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("notas", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    public static void borrarUsuario(Context context) {
        context.getSharedPreferences("usuario", Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }

    public static boolean validarNombreSeguro(String texto) {
        if (texto == null) return false;
        texto = texto.trim();
        if (texto.length() < 3) return false;

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (!Character.isLetter(c) && c != ' ') return false;
        }
        return true;
    }

    public static boolean validarCorreoSeguro(String correo) {
        if (correo == null) return false;
        correo = correo.trim();
        if (correo.contains(" ")) return false;
        if (correo.length() < 6) return false;
        if (!correo.contains("@") || !correo.contains(".")) return false;

        for (int i = 0; i < correo.length(); i++) {
            char c = correo.charAt(i);

            if (Character.isLetterOrDigit(c)) continue;
            if (c == '@' || c == '.' || c == '_' || c == '-') continue;

            return false;
        }
        return true;
    }

    public static String formatearFecha(String fecha) {
        if (fecha == null) return "";
        fecha = fecha.trim();

        // 01022000 -> 01/02/2000
        if (fecha.matches("\\d{8}")) {
            return fecha.substring(0,2) + "/" +
                    fecha.substring(2,4) + "/" +
                    fecha.substring(4);
        }
        return fecha;
    }


}
