package com.microblink.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.microblink.blinkid.MenuActivity;
import com.microblink.menu.LoadingActivity;
import com.microblink.result.activity.model.userList;
import com.microblink.result.activity.model.visitList;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class VisitViewActivity extends AppCompatActivity implements View.OnClickListener{

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;


    AsyncHttpClient client=new AsyncHttpClient();


    EditText ettFindV;
    Button btnRegresarV,btnFindV;
    ListView lvlVisit;
    ProgressBar progressBar4;

    String idSex;
    String direccion;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    LoadingActivity loading = new LoadingActivity(com.microblink.controller.VisitViewActivity.this);

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(com.microblink.libutils.R.layout.visit_view);

        AsyncHttpClient client=new AsyncHttpClient();
        cliente = new AsyncHttpClient();
        requestQueue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            System.out.println("VistView");
            System.out.println(extras.getString("id"));
            idSex = extras.getString("id");
        }else{
            Toast.makeText(com.microblink.controller.VisitViewActivity.this, "Estoy aqui login 2", Toast.LENGTH_SHORT).show();
        }

        initUi();
        obtenerVisit();

        btnRegresarV.setOnClickListener(this);
        btnFindV.setOnClickListener(this);

        direccion = "";
    }

    private void initUi() {

        btnRegresarV = findViewById(com.microblink.libutils.R.id.btnRegresarV);
        ettFindV = findViewById(com.microblink.libutils.R.id.ettFindV);
        btnFindV = findViewById(com.microblink.libutils.R.id.btnFindV);
        lvlVisit = findViewById(com.microblink.libutils.R.id.lvlVisit);
        progressBar4 = findViewById(com.microblink.libutils.R.id.progressBar4);
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == com.microblink.libutils.R.id.btnRegresarV) {

            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }else if(id == com.microblink.libutils.R.id.btnFindV){


        }
    }

    private void obtenerVisit(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String idUser = sharedPreferences.getString(TEXT, "");

        String url = "https://lektorgt.com/BlinkID/Visits/listVisit.php?id=" + idSex + "&userId=" + idUser;
        System.out.println("Holaaa aqui 2");
        System.out.println(url);
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String va="";
                    va = new String(responseBody);

                    if(va.equals("No se encontraron resultados")) {
                        Toast.makeText(VisitViewActivity.this, "Visitas no encontradas", Toast.LENGTH_SHORT).show();
                    }else if(!va.equals("No se encontraron resultados")){
                        Toast.makeText(VisitViewActivity.this, "Visitas encontradas", Toast.LENGTH_SHORT).show();
                    }

                    listarVisit(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void listarVisit(String respuesta){
        final ArrayList<String> lista = new ArrayList <String> ();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i=0;i<jsonArreglo.length();i++){

                visitList u = new visitList();
                u.setIdDate(jsonArreglo.getJSONObject(i).getString("idDate"));
                u.setIdTime(jsonArreglo.getJSONObject(i).getString("idTime"));
                u.setObservation(jsonArreglo.getJSONObject(i).getString("observation"));
                u.setUserNoHouse(jsonArreglo.getJSONObject(i).getString("userNoHouse"));

                lista.add(jsonArreglo.getJSONObject(i).getString("idDate")+ "\n" +jsonArreglo.getJSONObject(i).getString("idTime")+ "\n" + jsonArreglo.getJSONObject(i).getString("observation")+ "\n" + jsonArreglo.getJSONObject(i).getString("userNoHouse"));
            }

            ArrayAdapter<String> a = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
            lvlVisit.setAdapter(a);
            //loading.dismissDialog();
            lvlVisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

