package com.example.proyectoavanze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PermisosActivity extends AppCompatActivity {


    int REQUEST_CODE_ASK_PERMISSION = 123;

    RecyclerView recyclerView;
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //verificarPermisos();


        setContentView(R.layout.activity_permisos);
        recyclerView = findViewById(R.id.recview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Toast.makeText(this, "Acepta los permisos para poder continuar", Toast.LENGTH_LONG).show();
        final List<Permiso> permisosList = new ArrayList<>();
        permisosList.add(new Permiso("prueba telefono 1", Manifest.permission.CALL_PHONE));
        permisosList.add(new Permiso("prueba contactos 2", Manifest.permission.READ_CONTACTS));

        AdaptadorPermisos adapter = new AdaptadorPermisos(permisosList, this);

        verificarPermisos();
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }


    public void verificarPermisos() {
        int permisoLlamar = ActivityCompat.checkSelfPermission(PermisosActivity.this, Manifest.permission.CALL_PHONE);
        int permisoContactos = ActivityCompat.checkSelfPermission(PermisosActivity.this, Manifest.permission.READ_CONTACTS);

        if(permisoLlamar == PackageManager.PERMISSION_GRANTED && permisoContactos == PackageManager.PERMISSION_GRANTED){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){


                Intent i = new Intent(PermisosActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }


        }
        Toast.makeText(this, "ACEPTA LOS PERMISOS", Toast.LENGTH_SHORT);


    }

}
