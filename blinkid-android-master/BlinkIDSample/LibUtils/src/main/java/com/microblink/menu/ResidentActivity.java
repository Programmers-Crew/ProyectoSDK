package com.microblink.menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
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
import com.microblink.result.activity.BaseResultActivity;
import com.microblink.result.activity.model.PoliceList;
import com.microblink.result.extract.blinkid.generic.BlinkIDCombinedRecognizerResultExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;


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
    private static final String URL = "https://lektorgt.com/BlinkID/saveDataOne.php";

    String direccion;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String CONTADORI = "contadori";
    public static final String CONTADORF = "contadorf";
    public static final String FILE_NAME = "";
    Integer contador = 0;


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

            createUserOne(
                    BlinkIDCombinedRecognizerResultExtractor.documentNumber
            );
        }
    }


    private void createUser(final String userId,final String n, final String ln, final String s, final String add, final String bd, final String age, final String expi, final String expe, final String bp,
                            final String nac, final String cstatus, final String id, final String mrz, final String dType, final String f, final String l, final String p, final String no, final String observation){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ResidentActivity.this, response, Toast.LENGTH_SHORT).show();

                        if(response.equals("Visita se agrego Correctamente")){
                            loading.dismissDialog();
                            System.out.println(response);
                            finish();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
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


    private void createUserOne(final String userId){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

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

                        System.out.println("respuesta");
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR DE RED");
                        error.printStackTrace();

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


                        saveFile(
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
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("$idUsername", userId);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


/* */
    private void saveFile(final String userId,final String n, final String ln, final String s, final String add, final String bd, final String age, final String expi, final String expe, final String bp,
                      final String nac, final String cstatus, final String id, final String mrz, final String dType, final String f, final String l, final String p, final String no, final String observation){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_WORLD_READABLE);
        Integer contador = sharedPreferences.getInt(CONTADORI, 001);

        String FILE_NAME = "object" +contador+".txt";

        try
        {
            File ruta = Environment.getExternalStorageDirectory();

            File file = new File(ruta.getAbsolutePath(), FILE_NAME);

            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(
                            new FileOutputStream(file));

            String documentData = userId+","+n+","+s+","+add+","+bd+","+age+","+expi+","+expe+","+bp+","+nac+","+cstatus+","+id+","+mrz+","+dType+","+f+","+l+","+p+","+no+","+observation;
            outputStreamWriter.write(documentData);
            outputStreamWriter.close();

            System.out.println("Fichero salvado: " + getFilesDir() + "/" + FILE_NAME);
            Toast.makeText(getApplicationContext(), "Datos guardados internamente", Toast.LENGTH_SHORT).show();
            PreferenceUpdateInitial(contador+1);

        }
        catch (Exception e)
        {
            System.out.println("Fichero salvado: " + getFilesDir() + "/" + FILE_NAME);
            e.printStackTrace();
        }
    }

    /**/
    public void PreferenceUpdateInitial(Integer i){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(CONTADORI, i);
        editor.commit();
    }



    public void  readFile(){

        try
        {
            String FILE_NAME = "object" +10+".txt";

            File ruta = Environment.getExternalStorageDirectory();

            File file = new File(ruta.getAbsolutePath(), FILE_NAME);


            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(file)));

            String data1 = bufferedReader.readLine();
            String data2 = bufferedReader.readLine();
            String data3 = bufferedReader.readLine();
            String data4 = bufferedReader.readLine();

            String documentData = data1+data2+data3+data4;
            System.out.println(documentData);

            bufferedReader.close();
            String dataDocument[] = documentData.split(",");

            createUser(
                    dataDocument[0],
                    dataDocument[1],
                    dataDocument[2],
                    dataDocument[3],
                    dataDocument[4],
                    dataDocument[5],
                    dataDocument[6],
                    dataDocument[7],
                    dataDocument[8],
                    dataDocument[9],
                    dataDocument[10],
                    dataDocument[11],
                    dataDocument[12],
                    dataDocument[13],
                    dataDocument[14],
                    dataDocument[15],
                    dataDocument[16],
                    dataDocument[17],
                    dataDocument[18],
                    dataDocument[19]
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteFile(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_WORLD_READABLE);
        Integer contador = sharedPreferences.getInt(CONTADORI, 001);

        try{

            for(Integer i = 0; i<= contador ; i++){
                String FILE_NAME = "object" +10+".txt";
                File ruta = Environment.getExternalStorageDirectory();
                File file = new File(ruta.getAbsolutePath(), FILE_NAME);

                file.delete();
            }

            PreferenceUpdateInitial(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
