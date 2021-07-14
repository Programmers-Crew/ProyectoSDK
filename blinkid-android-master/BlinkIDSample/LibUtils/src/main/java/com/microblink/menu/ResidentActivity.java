package com.microblink.menu;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.microblink.result.activity.model.PoliceList;
import com.microblink.result.extract.blinkid.generic.BlinkIDCombinedRecognizerResultExtractor;
import java.util.HashMap;
import java.util.Map;


public class ResidentActivity extends AppCompatActivity implements View.OnClickListener{

    private AsyncHttpClient cliente;

    RequestQueue requestQueue;


    AsyncHttpClient client=new AsyncHttpClient();

    ProgressDialog pDialog;

    public static EditText ettNoCasa;
    public static EditText ettObservaciones;
    Button btnContinue;

    private Bitmap bitmap;
    private Bitmap bitmap2;

    private static final String URL1 = "https://lektorgt.com/BlinkID/saveDocumentData.php";

    String direccion;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String RESIDENTIAL = "residential";
    public static final String SWITCH1 = "switch1";
    PoliceList u = new PoliceList();

    LoadingActivity loading = new LoadingActivity(ResidentActivity.this);

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.resident_view);

        AsyncHttpClient client=new AsyncHttpClient();
        cliente = new AsyncHttpClient();
        requestQueue = Volley.newRequestQueue(this);

        initUi();

        btnContinue.setOnClickListener(this);

        direccion = "";
    }

    private void initUi() {
        ettNoCasa = findViewById(R.id.ettNoCasa);
        ettObservaciones = findViewById(R.id.ettObservaciones);

        btnContinue = findViewById(R.id.btnContinue);
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnContinue) {
            loading.startLoading();

            String subDateBD = BlinkIDCombinedRecognizerResultExtractor.birth.substring(0, 16);
            String replaceStringBD = subDateBD.replace('.','-');
            String subDate2BD = replaceStringBD.substring(6, 16);


            String subDateExpi = BlinkIDCombinedRecognizerResultExtractor.dateExpire.substring(0, 16);
            String replaceStringExpi = subDateExpi.replace('.','-');
            String subDate2Expi = replaceStringExpi.substring(6, 16);

            String subDateExpe = BlinkIDCombinedRecognizerResultExtractor.dateExpedition.substring(0, 16);
            String replaceStringExpe = subDateExpe.replace('.','-');
            String subDate2Expe = replaceStringExpe.substring(6, 16);

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            String userId = sharedPreferences.getString(TEXT, "");

            createUser(
                    userId,
                    BlinkIDCombinedRecognizerResultExtractor.firstName,
                    BlinkIDCombinedRecognizerResultExtractor.lastName,
                    BlinkIDCombinedRecognizerResultExtractor.sex,
                    BlinkIDCombinedRecognizerResultExtractor.address,
                    subDate2BD,
                    BlinkIDCombinedRecognizerResultExtractor.age.toString(),
                    subDate2Expi,
                    subDate2Expe,
                    BlinkIDCombinedRecognizerResultExtractor.placeBirth,
                    BlinkIDCombinedRecognizerResultExtractor.nacionality1,
                    BlinkIDCombinedRecognizerResultExtractor.maritalStatus,
                    BlinkIDCombinedRecognizerResultExtractor.documentNumber,
                    BlinkIDCombinedRecognizerResultExtractor.mrx.toString(),
                    BlinkIDCombinedRecognizerResultExtractor.documentTipe,
                    BlinkIDCombinedRecognizerResultExtractor.front,
                    BlinkIDCombinedRecognizerResultExtractor.backImage.getImageName(),
                    BlinkIDCombinedRecognizerResultExtractor.personal,
                    ettNoCasa.getText().toString().trim(),
                    ettObservaciones.getText().toString().trim()
            );
        }
    }


    private void createUser(final String userId,final String n, final String ln, final String s, final String add, final String bd, final String age, final String expi, final String expe, final String bp,
                            final String nac, final String cstatus, final String id, final String mrz, final String dType, final String f, final String l, final String p, final String no, final String observation) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ResidentActivity.this, response, Toast.LENGTH_SHORT).show();

                        if(response.equals("Visita se agrego Correctamente")){
                            loading.dismissDialog();
                            finish();
                        }

                        System.out.println("respuesta");
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("$idUsername", userId);
                params.put("firstName", n);
                params.put("firstLasName", ln);
                params.put("sex", s);
                params.put("address", add);
                params.put("birthDate", bd);
                params.put("age", age);
                params.put("expirationDate", expi);
                params.put("expeditionDate", expe);
                params.put("birthPlace", bp);
                params.put("nationality", nac);
                params.put("civilStatus", cstatus);
                params.put("documentNumber", id);
                System.out.println(id);
                params.put("textMRX", mrz);
                params.put("documentTyoe", dType);
                params.put("imgFront", f);
                params.put("imgLater", l);
                params.put("imgPersonal", p);

                params.put("idResident", no);
                params.put("observation", observation);

                System.out.println("popo");
                System.out.println(no);
                System.out.println(observation);

                System.out.println(userId);
                System.out.println(n);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

/*
    private void obtenerUsers(){

        String idUser = ettNoCasa.getText().toString().trim();

        String url = "https://lektorgt.com/BlinkID/Users/findUserByNo.php?id=" + idUser;
        System.out.println("Holaaa aqui 2");
        System.out.println(url);
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listarUsers(new String(responseBody));
                String p = new String(responseBody);

                if(p.equals("No se encontraron resultados")){
                    Toast.makeText(ResidentActivity.this, "Numero de casa inexistente", Toast.LENGTH_SHORT).show();
                }

                System.out.println("devuelta el obtener");
                System.out.println(p);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    */


}
