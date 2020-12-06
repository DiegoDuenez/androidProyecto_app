package com.example.proyectoavanze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private VolleyS vs;
    private RequestQueue requestQueue;
    private String jwtToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vs = VolleyS.getInstance(this.getApplicationContext());
        requestQueue= vs.getRequestQueue();

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

                       // token = response.toString();
                        try {
                            jwtToken = response.getString("token");

                            Toast.makeText(LoginActivity.this, jwtToken, Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "Se logro la conexion se manera satisfactoria", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, UsuarioActivity.class);
                            intent.putExtra("token", response.getString("token"));
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "Error en la conexion de put/url/request", Toast.LENGTH_SHORT).show();
                    }
                });/* {


                   @Override
                   public Map<String, String> getHeaders() throws AuthFailureError{
                       try {
                           Map<String, String> headers = new HashMap<String, String>();
                           headers.put("Authorization", "Bearer" + jwtToken);
                           Toast.makeText(LoginActivity.this, "Se logro la conexion se manera satisfactoria", Toast.LENGTH_SHORT).show();
                           return headers;
                       }catch (Exception e) {
                           Log.e(jwtToken, "Authentication Filure" );
                       }
                       return super.getHeaders();
                    }

               };*/



                request.setRetryPolicy(new DefaultRetryPolicy(500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                break;


        }

    }


}