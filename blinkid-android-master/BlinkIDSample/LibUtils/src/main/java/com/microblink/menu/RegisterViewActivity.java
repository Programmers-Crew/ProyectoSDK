package com.microblink.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.microblink.libutils.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterViewActivity extends AppCompatActivity implements View.OnClickListener{

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;


    AsyncHttpClient client=new AsyncHttpClient();

    ProgressDialog pDialog;

    EditText ettUsername, ettPass,ettPhone,ettResidential, ettEmail, ettHouse,ettAddres;
    Button btnRegister4, btnLogin4;
    ProgressBar progress;

    String respuesta = "";


    private static final String RegisterURL = "https://lektorgt.com/BlinkID/Users/addUser.php";
    //LoadingActivity loading = new LoadingActivity(LoginViewActivity.this);

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.register_view);

        AsyncHttpClient client=new AsyncHttpClient();
        cliente = new AsyncHttpClient();
        //loading.startLoading();
        requestQueue = Volley.newRequestQueue(this);

        initUi();

        btnRegister4.setOnClickListener(this);
        btnLogin4.setOnClickListener(this);

    }

    private void initUi() {
        ettUsername = findViewById(R.id.ettUsername);
        ettPass = findViewById(R.id.ettPass);
        ettPhone = findViewById(R.id.ettPhone);
        ettResidential = findViewById(R.id.ettResidential);
        ettEmail = findViewById(R.id.ettEmail);
        ettHouse = findViewById(R.id.ettHouse);
        ettAddres = findViewById(R.id.ettAddres);

        btnRegister4 = findViewById(R.id.btnRegister4);
        btnLogin4 = findViewById(R.id.btnLogin4);

        progress = findViewById(R.id.progress);
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnRegister4) {
           String name = ettUsername.getText().toString().trim();
           String password = ettPass.getText().toString().trim();
           String phone = ettPhone.getText().toString().trim();
           String residential = ettResidential.getText().toString().trim();
           String email = ettEmail.getText().toString().trim();
           String house = ettHouse.getText().toString().trim();
           String address = ettAddres.getText().toString().trim();



           if(name.equals("") && password.equals("") && phone.equals("") && residential.equals("") && email.equals("") && house.equals("") && address.equals("")){
                Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();
           }else {
               if(password.length()>7){
                   Toast.makeText(getApplicationContext(), "Telefono no puede ser mayor a 7 digitos", Toast.LENGTH_SHORT).show();
               }else if(ettPhone.length()>7){
                   Toast.makeText(getApplicationContext(), "Telefono no puede ser menor a 7 digitos", Toast.LENGTH_SHORT).show();
               }else{
                   registerUser(name,
                           password,
                           phone,
                           email,
                           residential,
                           house,
                           address
                   );

                   progress.setVisibility(View.VISIBLE);
               }
           }
        }else if(id == R.id.btnLogin4){
            Intent intent = new Intent(this, LoginViewActivity.class);
            startActivity(intent);
        }
    }


    public void registerUser(final String username,final String userPassword,final String userPhone,final String userEmail, final String userResidential, final String userNoHouse, final String userAddress){
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                RegisterURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterViewActivity.this, response, Toast.LENGTH_SHORT).show();
                        respuesta = response;
                        if(respuesta.equals("Usuario registrado exitosamente")){
                            limpiarText();
                        }
                        progress.setVisibility(View.GONE);
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
                params.put("userPhone", userPhone);
                params.put("userEmail", userEmail);
                params.put("userResidential", userResidential);
                params.put("userNoHouse", userNoHouse);
                params.put("userAddres", userAddress);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void limpiarText(){
        ettUsername.setText("");
        ettPass.setText("");
        ettPhone.setText("");
        ettResidential.setText("");
        ettEmail.setText("");
        ettHouse.setText("");
        ettAddres.setText("");
    };



}
