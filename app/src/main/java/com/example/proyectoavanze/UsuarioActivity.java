package com.example.proyectoavanze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UsuarioActivity extends AppCompatActivity  implements View.OnClickListener  {

    String nombre, fecha_c, fecha_u, mCorreo, mToken;
    TextView nombre_usuario, fecha_creacion, fecha_actualizacion;
    int value = 0;
    private VolleyS carta;
    private RequestQueue cartero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        carta = VolleyS.getInstance(this.getApplicationContext());
        cartero= carta.getRequestQueue();

        //BOTONES INFERIORES
        findViewById(R.id.btn_encender).setOnClickListener(this);


        nombre_usuario= findViewById(R.id.usuario_nombre);
        fecha_creacion = findViewById(R.id.fecha_creacion);
        fecha_actualizacion = findViewById(R.id.fecha_actu);
        Bundle bundle = getIntent().getExtras();
        mCorreo = bundle.getString("correo");
        String urlUsuario = "http://192.168.1.68:8000/api/usuario/" + mCorreo;
        /*String urlUsuario = "http://192.168.1.68:8000/api/usuario";*/
        Toast.makeText(this, urlUsuario, Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlUsuario, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray arreglo = response.getJSONArray("usuario");
                    JSONObject usuario = arreglo.getJSONObject (0);
//                  Toast.makeText(getApplicationContext(), usuario.toString(), Toast.LENGTH_LONG).show();
                    Gson gson = new Gson();
                    nombre_usuario.setText(usuario.getString("name"));
                    fecha_creacion.setText(usuario.getString("created_at"));
                    fecha_actualizacion.setText(usuario.getString("updated_at"));
//                    txtContraApp.setText( arreglo.getString("password"));

                    //Toast.makeText(getApplicationContext(), "datos: " + response, Toast.LENGTH_SHORT).show();


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

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_encender:
                String urlEncender = "http://192.168.1.68:8000/api/prender";
                value = 1;
                JSONObject requestEnv = new JSONObject();
                try {
                    requestEnv.put("value", value);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error en el post del value", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlEncender, requestEnv, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Gson convertidor = new Gson(); //Convertidor

                        //JSONObject responseJSON = response.getJSONObject("Usuario");
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Se envio el value " + value, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Error en la conexion de put/url/request", Toast.LENGTH_SHORT).show();
                    }
                });
                request.setRetryPolicy(new DefaultRetryPolicy(500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                cartero.add(request);

                break;

            case R.id.btn_apagar:

                String urlApagar = "http://192.168.1.68:8000/api/apagar";
                value = 0;
                JSONObject requestEnv2 = new JSONObject();
                try {
                    requestEnv2.put("value", value);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error en el post del value", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.POST, urlApagar, requestEnv2, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Gson convertidor = new Gson(); //Convertidor

                        //JSONObject responseJSON = response.getJSONObject("Usuario");
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Se envio el value " + value, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Error en la conexion de put/url/request", Toast.LENGTH_SHORT).show();
                    }
                });
                request2.setRetryPolicy(new DefaultRetryPolicy(500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                cartero.add(request2);

                break;

            case R.id.btn_datos:

                break;

            case R.id.btn_logout:
                Intent iLogout = new Intent(UsuarioActivity.this, LoginActivity.class);
                startActivity(iLogout);
                finish();
                break;

    }
}

}