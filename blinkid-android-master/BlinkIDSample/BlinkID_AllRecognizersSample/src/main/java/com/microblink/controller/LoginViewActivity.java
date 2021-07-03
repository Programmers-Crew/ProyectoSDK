package com.microblink.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.microblink.blinkid.MenuActivity;
import com.microblink.libutils.R;

import java.util.HashMap;
import java.util.Map;

public class LoginViewActivity extends AppCompatActivity implements View.OnClickListener{

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;


    AsyncHttpClient client=new AsyncHttpClient();

    ProgressDialog pDialog;

    EditText ettUsernameL, ettPassL;
    Button btnRegister,btnLogin;
    ImageButton imageButton6, imageButton5;
    ProgressBar progressLogin;
    Switch switchLogin;

    public static String idPreferences = "";

    String direccion;
    private static final String LoginURL = "https://lektorgt.com/BlinkID/Users/login.php";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";


    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_view);

        AsyncHttpClient client=new AsyncHttpClient();
        cliente = new AsyncHttpClient();
        requestQueue = Volley.newRequestQueue(this);

        initUi();

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);

        direccion = "";

    }

    private void initUi() {
        ettUsernameL = findViewById(R.id.ettUsernameL);
        ettPassL = findViewById(R.id.ettPassL);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLoginL);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);

        progressLogin = findViewById(R.id.progressLogin);
        switchLogin = findViewById(R.id.switchLogin);
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnLoginL) {
            String username = ettUsernameL.getText().toString().trim();
            String pass = ettPassL.getText().toString().trim();

            login(username, pass);

            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);

            progressLogin.setVisibility(View.VISIBLE);

        }else if(id == R.id.btnRegister){
            Intent intent = new Intent(this, RegisterViewActivity.class);
            startActivity(intent);
        }else if(id == R.id.imageButton5){
            direccion = "https://www.youtube.com/watch?v=LB2AhSKg1OE";
            goToFacebook(direccion);
        }else if(id == R.id.imageButton6){
            direccion = "http://lektorgt.com/";
            goToFacebook(direccion);
        }
    }

    public void goToFacebook(String d){
        Uri uri = Uri.parse(d);
        Intent intentNav = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intentNav);
    }

    String respuesta;

    public void login(final String username,final String userPassword){
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                LoginURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginViewActivity.this, response, Toast.LENGTH_SHORT).show();
                        respuesta = response;

                        Integer index = respuesta.length();
                        String r1 = respuesta.substring(0, index-2);
                        Integer indexR1 = r1.length();
                        String r2 = r1.substring(11, indexR1);

                        if(switchLogin.isChecked()) {
                            saveDate(r2);
                            System.out.println("se guardaron los datos");
                        }
                        progressLogin.setVisibility(View.GONE);
                        limpiarText();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error aqui");
                        System.out.println(error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userName", username);
                params.put("userPassword", userPassword);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void limpiarText(){
        ettUsernameL.setText("");
        ettPassL.setText("");
    };


    public void saveDate(String id){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, id);
        editor.commit();
    }
}

