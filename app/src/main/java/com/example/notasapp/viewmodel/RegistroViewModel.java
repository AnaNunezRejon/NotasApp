package com.example.notasapp.viewmodel;

import androidx.lifecycle.ViewModel;

public class RegistroViewModel extends ViewModel {

    private String nombre, apellidos, fecha, correo, contrasena;

    public void setNombre(String n) { this.nombre = n; }
    public void setApellidos(String a) { this.apellidos = a; }
    public void setFecha(String f) { this.fecha = f; }
    public void setCorreo(String c) { this.correo = c; }
    public void setContrasena(String p) { this.contrasena = p; }

    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getFecha() { return fecha; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
}
