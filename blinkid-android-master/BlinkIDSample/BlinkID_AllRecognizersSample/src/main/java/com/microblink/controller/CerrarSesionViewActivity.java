package com.microblink.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.microblink.libutils.R;

public class CerrarSesionViewActivity extends AppCompatActivity implements View.OnClickListener {

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;


    AsyncHttpClient client = new AsyncHttpClient();

    ProgressDialog pDialog;
    ImageButton btnFacebook, btnWeb;
    Button btnCerrarSesion;
    String direccion;
    ProgressBar progressCerrar;

    public static String idPreferences = "";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.cerrarsesion_view);

        AsyncHttpClient client = new AsyncHttpClient();
        cliente = new AsyncHttpClient();
        requestQueue = Volley.newRequestQueue(this);

        initUi();

        btnCerrarSesion.setOnClickListener(this);
        btnWeb.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);

        direccion = "";

    }

    private void initUi() {

        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnWeb = findViewById(R.id.btnWeb);
        btnFacebook = findViewById(R.id.btnFacebook);

        progressCerrar = findViewById(R.id.progressCerrar);
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnCerrarSesion) {
            progressCerrar.setVisibility(View.VISIBLE);

            String nulo = "";
            saveDate(nulo);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = null;
            intent = new Intent(CerrarSesionViewActivity.this, LoginViewActivity.class);
            startActivity(intent);

            progressCerrar.setVisibility(View.GONE);

        } else if (id == R.id.btnFacebook) {
            direccion = "https://www.youtube.com/watch?v=LB2AhSKg1OE";
            goToFacebook(direccion);
        } else if (id == R.id.btnWeb) {
            direccion = "http://lektorgt.com/";
            goToFacebook(direccion);
        }
    }

    public void goToFacebook(String d) {
        Uri uri = Uri.parse(d);
        Intent intentNav = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intentNav);
    }

    public void saveDate(String id) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        System.out.println("SAVE DATA");
        System.out.println(id);

        editor.putString(TEXT, id);
        editor.commit();

        System.out.println("SAVE DATA 2");
        String idUser = sharedPreferences.getString(TEXT, "root");
        System.out.println(idUser);

    }
}