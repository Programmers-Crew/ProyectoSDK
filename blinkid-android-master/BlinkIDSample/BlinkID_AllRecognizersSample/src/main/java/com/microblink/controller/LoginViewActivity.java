package com.microblink.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.microblink.blinkid.MenuActivity;
import com.microblink.libutils.R;
import com.microblink.result.activity.model.PoliceList;
import org.json.JSONArray;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class LoginViewActivity extends AppCompatActivity implements View.OnClickListener {

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;


    AsyncHttpClient client = new AsyncHttpClient();

    ProgressDialog pDialog;

    EditText ettUsernameL, ettPassL;
    Button btnRegister, btnLogin;
    ImageButton imageButton6, imageButton5;
    ProgressBar progressLogin;
    Switch switchLogin;

    public static String idPreferences = "";

    String direccion;
    private static final String LoginURL = "https://lektorgt.com/BlinkID/Users/login.php";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String RESIDENTIAL = "residential";
    public static final String SWITCH1 = "switch1";
    String respuesta;

    Integer index = 0;
    String r1 = "";
    Integer indexR1 = 0;
    String r2 = "";
    String stringMac = "";
    String macAddress = "";
    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_view);

        AsyncHttpClient client = new AsyncHttpClient();
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
            getMac();
            String username = ettUsernameL.getText().toString().trim();
            String pass = ettPassL.getText().toString().trim();
            login(username, pass, macAddress);
            progressLogin.setVisibility(View.VISIBLE);

        } else if (id == R.id.btnRegister) {
            Intent intent = new Intent(this, RegisterViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.imageButton5) {
            direccion = "https://www.youtube.com/watch?v=LB2AhSKg1OE";
            goToFacebook(direccion);
        } else if (id == R.id.imageButton6) {
            direccion = "http://lektorgt.com/";
            goToFacebook(direccion);
        }
    }

    public void goToFacebook(String d) {
        Uri uri = Uri.parse(d);
        Intent intentNav = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intentNav);
    }


    public void intentToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void login(final String username, final String userPassword, final String mac) {
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                LoginURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginViewActivity.this, response, Toast.LENGTH_SHORT).show();
                        System.out.println("respuesta 1");
                        System.out.println(response);

                        if (response.equals("not found rows")) {
                            Toast.makeText(LoginViewActivity.this, "Contraseña o usuario incorrectas", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("Hay campos vacios")) {
                            Toast.makeText(LoginViewActivity.this, response, Toast.LENGTH_SHORT).show();
                        } else if (!response.equals("not found rows") || !response.equals("Hay campos vacios")) {
                            Toast.makeText(LoginViewActivity.this, "Accediste a tu cuenta", Toast.LENGTH_SHORT).show();
                            System.out.println("credenciales");
                            System.out.println(index);
                            System.out.println(r1);
                            System.out.println(indexR1);
                            System.out.println(r2);

                            index = response.length();
                            r1 = response.substring(0, index - 2);
                            indexR1 = r1.length();
                            r2 = r1.substring(11, indexR1);
                            intentToMenu();
                        }


                        if (switchLogin.isChecked()) {
                            System.out.println("ID POLICIA");
                            System.out.println(r2);
                            saveDate(r2);
                            obtenerUsers();
                            System.out.println("se guardaron los datos");
                        } else if (!switchLogin.isChecked()) {

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
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userName", username);
                params.put("userPassword", userPassword);
                params.put("macAddress", mac);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void limpiarText() {
        ettUsernameL.setText("");
        ettPassL.setText("");
    }

    ;


    private void obtenerUsers() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_WORLD_READABLE);


        String idUser = sharedPreferences.getString(TEXT, "");

        String url = "https://lektorgt.com/BlinkID/Users/listPolice.php?id=" + idUser;

        System.out.println("Holaaa aqui 2");
        System.out.println(url);
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    listarUsers(new String(responseBody));
                    String p = new String(responseBody);
                    System.out.println(p);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void listarUsers(String respuesta) {
        final ArrayList<String> lista = new ArrayList<String>();
        String residential = "";
        PoliceList u = new PoliceList();

        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);

            u.setUserResidential(jsonArreglo.getJSONObject(0).getString("userResidential"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ListUser ID");

        saveResidential(u.getUserResidential());
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

    public void saveResidential(String resdential) {

        System.out.println(resdential);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(RESIDENTIAL, resdential);
        editor.commit();

        System.out.println("save data 3");
        String idResidential = sharedPreferences.getString(RESIDENTIAL, "root");
    }

    public void getMac(){
        try {
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface networkInterface:networkInterfaceList){
                if(networkInterface.getName().equalsIgnoreCase("wlan0")){
                    for(int i = 0; i<networkInterface.getHardwareAddress().length; i++){
                        String stringMacByte = Integer.toHexString(networkInterface.getHardwareAddress()[i] & 0xFF);

                        if(stringMacByte.length() == 1){
                            stringMacByte = "0" + stringMacByte;
                        }
                        stringMac = stringMac+ stringMacByte.toUpperCase() + ":";
                    }
                }
            }
            macAddress = stringMac;
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}