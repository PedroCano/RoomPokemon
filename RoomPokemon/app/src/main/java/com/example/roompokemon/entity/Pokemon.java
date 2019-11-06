package com.example.roompokemon.entity;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemon")
public class Pokemon {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "tipo")
    private String tipo;

    @ColumnInfo(name = "foto")
    private String foto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
