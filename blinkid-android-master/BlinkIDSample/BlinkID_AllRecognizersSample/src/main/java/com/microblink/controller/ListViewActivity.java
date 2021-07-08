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
import android.widget.ListView;
import android.widget.Toast;
import com.loopj.android.http.*;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.microblink.libutils.R;
import com.microblink.menu.LoadingActivity;
import com.microblink.result.activity.model.userList;
import com.microblink.result.extract.blinkid.generic.BlinkIDCombinedRecognizerResultExtractor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListViewActivity extends AppCompatActivity implements View.OnClickListener{

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;


    AsyncHttpClient client=new AsyncHttpClient();

    ProgressDialog pDialog;

    EditText ettIdFind;
    ListView lvlUser;
    Button btnFind;

    String URL = "https://lektorgt.com/BlinkID/listDocumentData2.php";
    public static String SHARED_PREFS = "sharedPrefs";
    public static String TEXT = "text";

    LoadingActivity loading = new LoadingActivity(com.microblink.controller.ListViewActivity.this);

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.list_view);

        AsyncHttpClient client=new AsyncHttpClient();
        cliente = new AsyncHttpClient();
        loading.startLoading();
        requestQueue = Volley.newRequestQueue(this);

        initUi();

        btnFind.setOnClickListener(this);

        obtenerUsers();
    }


    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnFind) {
            Intent intent = new Intent(this, EditViewActivity.class);
            intent.putExtra("id", ettIdFind.getText().toString().trim());
            startActivity(intent);
        }
    }

    private void initUi() {
        ettIdFind = findViewById(R.id.ettIdFind);

        btnFind = findViewById(R.id.btnFind);
        lvlUser = findViewById(R.id.lvlUser);
    }

    private void obtenerUsers(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_WORLD_READABLE);

        String idUser = sharedPreferences.getString(TEXT, "root");

        System.out.println("PREFERENCES ID");
        System.out.println(idUser);

        String url = "https://lektorgt.com/BlinkID/listDocumentData2.php?id=" + idUser;
        System.out.println("Holaaa aqui 2");
        System.out.println(url);
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarUsers(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void listarUsers(String respuesta){
        final ArrayList<String> lista = new ArrayList <String> ();
        try{

            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i=0;i<jsonArreglo.length();i++){

                userList u = new userList();
                u.setDocumentNumber(jsonArreglo.getJSONObject(i).getString("documentNumber"));
                u.setFirstName(jsonArreglo.getJSONObject(i).getString("firstName"));
                u.setFirstLasName(jsonArreglo.getJSONObject(i).getString("firstLasName"));

                String nombre = jsonArreglo.getJSONObject(i).getString("firstName");
                String replaceNombre = nombre.replace('\n',' ');

                String lastName = jsonArreglo.getJSONObject(i).getString("firstLasName");
                String replacelastName = lastName.replace('\n',' ');
                String countVisits = jsonArreglo.getJSONObject(i).getString("countVisit");
                lista.add(jsonArreglo.getJSONObject(i).getString("documentNumber")+ "\n" +replaceNombre+ "\n"+replacelastName+"\n"+"Visitas: "+countVisits+"\n");
            }

            ArrayAdapter<String> a = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
            lvlUser.setAdapter(a);
            loading.dismissDialog();
            lvlUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String idBuscado = lista.get(position);
                    String[] split = idBuscado.split("\n");
                    ettIdFind.setText(split[0]);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
