package com.microblink.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.microblink.libutils.R;

public class LoginViewActivity extends AppCompatActivity implements View.OnClickListener{

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;


    AsyncHttpClient client=new AsyncHttpClient();

    ProgressDialog pDialog;

    EditText ettUsername, ettPass;
    Button btnRegister,btnLogin;
    ImageButton imageButton6, imageButton5;

    String direccion;
    //LoadingActivity loading = new LoadingActivity(LoginViewActivity.this);


    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_view);

        AsyncHttpClient client=new AsyncHttpClient();
        cliente = new AsyncHttpClient();
        //loading.startLoading();
        requestQueue = Volley.newRequestQueue(this);

        initUi();

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);

        direccion = "";
    }

    private void initUi() {
        ettUsername = findViewById(R.id.ettUsername);
        ettPass = findViewById(R.id.ettPass);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnLogin) {
            System.out.println("Login Login");
        }else if(id == R.id.btnRegister){
            Intent intent = new Intent(this, RegisterViewActivity.class);
            startActivity(intent);
        }else if(id == R.id.imageButton5){
            direccion = "https://www.youtube.com/watch?v=LB2AhSKg1OE";
            goToFacebook(direccion);
        }else if(id == R.id.imageButton6){
            direccion = "https://www.google.com/";
            goToFacebook(direccion);
        }
    }

    public void goToFacebook(String d){
        Uri uri = Uri.parse(d);
        Intent intentNav = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intentNav);
    }
}
