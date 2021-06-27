package com.microblink.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.microblink.libutils.R;

public class RegisterViewActivity extends AppCompatActivity implements View.OnClickListener{

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;


    AsyncHttpClient client=new AsyncHttpClient();

    ProgressDialog pDialog;

    EditText ettUsername, ettPass,ettPhone,ettResidential, ettEmail, ettHouse,ettAddres;
    Button btnRegister4, btnLogin4;

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
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnRegister4) {
            Intent intent = new Intent(this, LoginViewActivity.class);
            startActivity(intent);
        }else if(id == R.id.btnLogin4){
            Intent intent = new Intent(this, LoginViewActivity.class);
            startActivity(intent);

        }

    }

}
