package com.example.proyectoavanze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity {

    private VolleyS carta;
    private RequestQueue cartero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        carta = VolleyS.getInstance(this.getApplicationContext());
        cartero= carta.getRequestQueue();
    }


    public void botonRegistrar(View view) {
        String urlRegistro = "http://192.168.1.68:8000/api/registro";
        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        String usernametxt = username.getText().toString();
        String emailtxt = email.getText().toString();
        String passtxt = password.getText().toString();

        JSONObject requestEnv = new JSONObject();
        try {
            requestEnv.put("name", usernametxt);
            requestEnv.put("email", emailtxt);
            requestEnv.put("password", passtxt);

        } catch (JSONException e) {
            Toast.makeText(RegistroActivity.this, "Error en el put", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlRegistro, requestEnv, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Gson convertidor = new Gson(); //Convertidor

                //JSONObject responseJSON = response.getJSONObject("Usuario");
                Toast.makeText(RegistroActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(RegistroActivity.this, "Se logro la conexion se manera satisfactoria", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistroActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(RegistroActivity.this, "Error en la conexion de put/url/request", Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        cartero.add(request);
    }











}


