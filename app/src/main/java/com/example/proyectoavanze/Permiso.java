package com.example.proyectoavanze;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Permiso {


    private String nombre;
    private String permiso;


    public Permiso(String nombre, String permiso) {
        this.nombre = nombre;
        this.permiso = permiso;
    }




    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
