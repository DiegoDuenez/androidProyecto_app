package com.example.proyectoavanze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_PERMISSION = 124;
    private VolleyS cartero;
    private RequestQueue carta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cartero = VolleyS.getInstance(this.getApplicationContext());
        carta = cartero.getRequestQueue();


        //ANIMACIÓN DEL SPLASH
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int permisoLlamar = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
                int permisoContactos = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS);

                if(permisoContactos != PackageManager.PERMISSION_GRANTED ||
                        permisoLlamar != PackageManager.PERMISSION_GRANTED){
                    Intent i = new Intent(MainActivity.this, PermisosActivity.class);
                    startActivity(i);
                    finish();
                }if(permisoContactos == PackageManager.PERMISSION_GRANTED && permisoLlamar == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }




}