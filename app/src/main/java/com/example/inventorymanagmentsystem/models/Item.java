package com.example.inventorymanagmentsystem.models;

public class Item {
    private int id;
    private String nombre;
    private Type type;

    public Item(int id,Type type, String nombre) {
        this.id=id;
        this.type = type;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Type getType() {
        return type;
    }
}
