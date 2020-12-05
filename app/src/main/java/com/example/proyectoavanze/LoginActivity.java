package com.example.proyectoavanze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.sql.CommonDataSource;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private VolleyS carta;
    private RequestQueue cartero;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        carta = VolleyS.getInstance(this.getApplicationContext());
        cartero= carta.getRequestQueue();

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_reg_1).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_reg_1:
                Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_login:


                String urlRegistro = "http://192.168.1.68:8000/api/login";
                EditText email = findViewById(R.id.email);
                EditText password = findViewById(R.id.password);
                String emailtxt = email.getText().toString();
                String passtxt = password.getText().toString();

                JSONObject requestEnv = new JSONObject();
                try {
                    requestEnv.put("email", emailtxt);
                    requestEnv.put("password", passtxt);

                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "Error en el put", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlRegistro, requestEnv, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        token = response.toString();


                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "Se logro la conexion se manera satisfactoria", Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "Error en la conexion de put/url/request", Toast.LENGTH_SHORT).show();
                    }
                });
               /* {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError{
                        HashMap<String, String> headers = new HashMap<String, String>();

                        headers.put("Content-Type", "application/json");
                        headers.put("Bearer", token);
                       Intent intent1 = new Intent(getApplicationContext(), UsuarioActivity.class);
                        startActivity(intent1);
                        Toast.makeText(LoginActivity.this, "Token enviado" + headers, Toast.LENGTH_SHORT).show();
                        return headers;
                    }

                };*/



                request.setRetryPolicy(new DefaultRetryPolicy(500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                cartero.add(request);
                break;


        }

    }

}