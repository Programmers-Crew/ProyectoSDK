package com.microblink.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.microblink.libutils.R;

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

    LoadingActivity loading = new LoadingActivity(EditViewActivity.this);
String URL = "https://lektorgt.com/blinkid/fetchDocumentData.php" + idSex;

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
            Toast.makeText(EditViewActivity.this, "Estoy aqui login 2", Toast.LENGTH_SHORT).show();
        }

        initUi();
        readUsers();

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
    }

    private void readUsers() {
        String URL = "https://lektorgt.com/BlinkID/fetchDocumentData.php?id=" + idSex;
        System.out.println("=================================");
        System.out.println("=================================");
        System.out.println("=================================");
        System.out.println(idSex);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
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
            Intent intent = null;
            intent = new Intent(this, ListViewActivity.class);
            startActivity(intent);
        }
    }

}
