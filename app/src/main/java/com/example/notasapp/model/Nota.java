package com.example.notasapp.model;

public class Nota {

    private String id;
    private String titulo;
    private String descripcion;
    private String color;
    private String fecha;
    private boolean esFija;

    public Nota(String id, String titulo, String descripcion, String color, String fecha, boolean esFija) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.color = color;
        this.fecha = fecha;
        this.esFija = esFija;
    }

    // Getters y setters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getColor() { return color; }
    public String getFecha() { return fecha; }
    public boolean isEsFija() { return esFija; }

    public void setTitulo(String t) { this.titulo = t; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public void setColor(String c) { this.color = c; }
}
