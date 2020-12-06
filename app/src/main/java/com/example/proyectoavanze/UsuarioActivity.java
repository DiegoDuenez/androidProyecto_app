package com.example.proyectoavanze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UsuarioActivity extends AppCompatActivity {

    String nombre, fecha_c, tkn;
    TextView nombre_usuario, fecha_creacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);


        nombre_usuario= findViewById(R.id.usuario_nombre);
        fecha_creacion = findViewById(R.id.fecha_creacion);

        obtenerUsuario();

    }

    private void obtenerUsuario() {
        String urlUsuario = "http://192.168.1.68:8000/api/usuario";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlUsuario, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    nombre = response.getString("name");
                    fecha_c = response.getString("created_at");
                    Toast.makeText(getApplicationContext(), "datos: " + response, Toast.LENGTH_SHORT).show();
                    nombre_usuario.setText(nombre);
                    fecha_creacion.setText(fecha_c);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);





    }


}