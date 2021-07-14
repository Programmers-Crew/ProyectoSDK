package com.microblink.controller;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.microblink.libutils.R;
import com.microblink.menu.LoadingActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class EditViewActivity extends AppCompatActivity implements View.OnClickListener{

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;

    AsyncHttpClient client=new AsyncHttpClient();

    ProgressDialog pDialog;

    EditText firtsName,lastName,dateBirth ,age,birthPlace,ExpeditionDate,expirationDate,address, Nacionality, shipper, sex, civilStatus,documentType, documentNumber,visits;
    Button button3;
    String idSex;
    ImageView imageFront, imageBack;

    LoadingActivity loading = new LoadingActivity(com.microblink.controller.EditViewActivity.this);

    String URL = "https://lektorgt.com/blinkid/fetchDocumentData.php" + idSex;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.edit_view);
        requestQueue = Volley.newRequestQueue(this);
        loading.startLoading();
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            idSex = extras.getString("id");
        }else{
            Toast.makeText(com.microblink.controller.EditViewActivity.this, "Estoy aqui login 2", Toast.LENGTH_SHORT).show();
        }

        initUi();
        readUsers();
        showPhoto();
        showPhotoBack();
        button3.setOnClickListener(this);
    }

    private void initUi() {
        firtsName = findViewById(R.id.firtsName);
        lastName = findViewById(R.id.lastName);
        dateBirth = findViewById(R.id.dateBirth);
        age = findViewById(R.id.age);
        birthPlace = findViewById(R.id.birthPlace);
        ExpeditionDate = findViewById(R.id.ExpeditionDate);
        expirationDate = findViewById(R.id.expirationDate);
        address = findViewById(R.id.address);
        Nacionality = findViewById(R.id.Nacionality);
        sex = findViewById(R.id.sex);
        civilStatus = findViewById(R.id.civilStatus);
        documentType = findViewById(R.id.documentType);
        documentNumber = findViewById(R.id.documentNumber);
        visits = findViewById(R.id.visitas);
        button3 = findViewById(R.id.button3);
        imageFront = findViewById(R.id.imageFront);
        imageBack = findViewById(R.id.imageBack);

    }

    private void readUsers() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String idUser = sharedPreferences.getString(TEXT, "");

        String URL = "https://lektorgt.com/BlinkID/fetchDocumentData.php?id=" + idSex + "&idUsername=" + idUser;
        System.out.println(URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String LfirtsName = response.getString("firstName");
                            String LlastName = response.getString("firstLasName");
                            String LdateBirth = response.getString("birthDate");
                            String Lage = response.getString("age");
                            String LbirthPlace = response.getString("birthPlace");
                            String LExpeditionDate = response.getString("expeditionDate");
                            String LexpirationDate = response.getString("expirationDate");
                            String Laddress = response.getString("address");
                            String LNacionality = response.getString("nationality");
                            String Lsex = response.getString("sexDesc");
                            String LcivilStatus = response.getString("statusDesc");
                            String LdocumentType = response.getString("firstName");
                            String LdocumentNumber = response.getString("typeDesc");
                            String visit = response.getString("countVisit");
                            documentNumber.setText(LdocumentNumber);
                            firtsName.setText(LfirtsName);
                            lastName.setText(LlastName);
                            dateBirth.setText(LdateBirth);
                            age.setText(Lage);
                            birthPlace.setText(LbirthPlace);
                            ExpeditionDate.setText(LExpeditionDate);
                            expirationDate.setText(LexpirationDate);
                            address.setText(Laddress);
                            Nacionality.setText(LNacionality);
                            sex.setText(Lsex);
                            civilStatus.setText(LcivilStatus);
                            documentType.setText(LdocumentType);
                            visits.setText(visit);
                            loading.dismissDialog();
                        } catch (JSONException e) {
                            System.out.println("====================");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("====================");
                        System.out.println(error);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button3) {
            Intent intent = new Intent(this, VisitViewActivity.class);
            intent.putExtra("id", idSex);
            startActivity(intent);
        }
    }

    private void showPhoto(){
        final String path = "https://lektorgt.com/BlinkID/uploads/";
        final String[] ruta = {""};
        String URL_IMAGE = "https://lektorgt.com/BlinkID/fetchImage.php?id=" + idSex;
        System.out.println("holaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaa");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_IMAGE,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("entre entre");
                            System.out.println("entre entre");
                            System.out.println("entre entre");
                            System.out.println("entre entre");

                            ruta[0] = path + response.getString("imgFront");
                            System.out.println(ruta[0]);
                            ImageRequest imageRequest = new ImageRequest(
                                    ruta[0],
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            imageFront.setImageBitmap(response);
                                        }
                                    },
                                    0,
                                    0,
                                    null,
                                    null,
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println(error);
                                        }
                                    }
                            );
                            requestQueue.add(imageRequest);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println(error);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    private void showPhotoBack(){
        final String path = "https://lektorgt.com/BlinkID/uploads/";
        final String[] ruta = {""};
        String URL_IMAGE = "https://lektorgt.com/BlinkID/fetchImageBack.php?id=" + idSex;
        System.out.println("holaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaa");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_IMAGE,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("entre entre");
                            System.out.println("entre entre");
                            System.out.println("entre entre");
                            System.out.println("entre entre");

                            ruta[0] = path + response.getString("imgFront");
                            System.out.println(ruta[0]);
                            ImageRequest imageRequest = new ImageRequest(
                                    ruta[0],
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            imageBack.setImageBitmap(response);
                                        }
                                    },
                                    0,
                                    0,
                                    null,
                                    null,
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println("error");
                                            System.out.println(error);
                                        }
                                    }
                            );
                            requestQueue.add(imageRequest);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println("error");
                        System.out.println(error);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

}
