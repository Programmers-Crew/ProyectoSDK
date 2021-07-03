package com.microblink.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.microblink.blinkid.MenuActivity;
import com.microblink.libutils.R;

public class Validation extends AppCompatActivity implements View.OnClickListener{

    TextView textView;
    ProgressBar progress2;
    Button btnValidation;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    public Menu menu;

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.validation_view);

        initUi();

        btnValidation.setOnClickListener(this);
    }

    private void initUi() {
        textView = findViewById(R.id.textView);
        progress2 = findViewById(R.id.progressBar2);
        btnValidation = findViewById(R.id.btnValidation);

    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnValidation) {

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            String idValidation = sharedPreferences.getString(TEXT, "");
            System.out.println("get data");
            System.out.println(idValidation);

            if(idValidation.equals("")){
                Intent intent = new Intent(this, LoginViewActivity.class);
                startActivity(intent);
            }else if(!idValidation.equals("")){
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            }


        }
    }

}
