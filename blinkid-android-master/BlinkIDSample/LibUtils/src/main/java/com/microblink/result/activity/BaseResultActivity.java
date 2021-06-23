package com.microblink.result.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.microblink.entities.recognizers.HighResImagesBundle;
import com.microblink.image.Image;
import com.microblink.image.highres.HighResImageWrapper;
import com.microblink.libutils.R;
import com.microblink.util.ImageUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.microblink.result.extract.blinkid.generic.BlinkIDCombinedRecognizerResultExtractor;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseResultActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    AsyncHttpClient client=new AsyncHttpClient();

    private static final String URL1 = "https://lektorgt.com/BlinkID/saveDocumentData.php";



    protected ViewPager mPager;
    private HighResImagesBundle highResImagesBundle;
    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityContentView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        requestQueue = Volley.newRequestQueue(this);

        try {
            highResImagesBundle = new HighResImagesBundle(intent);
        } catch (Exception e) {
            // crash if high res frames aren't enabled
            highResImagesBundle = null;
        }

        if (highResImagesBundle != null && !highResImagesBundle.getImages().isEmpty()) {
            invalidateOptionsMenu();
        } else {
            toolbar.setVisibility(View.GONE);
        }

        mPager = findViewById(R.id.resultPager);
        mPager.setAdapter(createResultFragmentPagerAdapter(intent));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mPager);
        tabLayout.setClipChildren(false);

        findViewById(R.id.btnUseResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subDateBD = BlinkIDCombinedRecognizerResultExtractor.birth.substring(0, 16);
                String replaceStringBD = subDateBD.replace('.','-');
                String subDate2BD = replaceStringBD.substring(6, 16);


                String subDateExpi = BlinkIDCombinedRecognizerResultExtractor.dateExpire.substring(0, 16);
                String replaceStringExpi = subDateExpi.replace('.','-');
                String subDate2Expi = replaceStringExpi.substring(6, 16);

                String subDateExpe = BlinkIDCombinedRecognizerResultExtractor.dateExpedition.substring(0, 16);
                String replaceStringExpe = subDateExpe.replace('.','-');
                String subDate2Expe = replaceStringExpe.substring(6, 16);

                createUser(
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
                        BlinkIDCombinedRecognizerResultExtractor.back,
                        BlinkIDCombinedRecognizerResultExtractor.personal
                );
            finish();
            }
        });
    }

    private void showHighResImagesDialog() {
        if (highResImagesBundle != null && !highResImagesBundle.getImages().isEmpty()) {
            LinearLayout imagesLayout = new LinearLayout(this);
            imagesLayout.setOrientation(LinearLayout.VERTICAL);

            ScrollView scrollView = new ScrollView(this);
            scrollView.addView(imagesLayout);

            for (HighResImageWrapper highResImageWrapper : highResImagesBundle.getImages()) {
                Image highResImage = highResImageWrapper.getImage();
                Bitmap highResBmp = highResImage.convertToBitmap();
                Matrix rotationMatrix = new Matrix();
                switch (highResImage.getImageOrientation()) {
                    case ORIENTATION_PORTRAIT:
                        rotationMatrix.postRotate(90);
                        break;
                    case ORIENTATION_LANDSCAPE_LEFT:
                        rotationMatrix.postRotate(180);
                        break;
                    case ORIENTATION_PORTRAIT_UPSIDE:
                        rotationMatrix.postRotate(270);
                }
                Bitmap rotatedBmp = Bitmap.createBitmap(
                        highResBmp, 0, 0, highResBmp.getWidth(), highResBmp.getHeight(), rotationMatrix, true);
                highResBmp.recycle();

                final float scale = getResources().getDisplayMetrics().density;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) (400 * scale)
                );
                layoutParams.topMargin = (int) (20 * scale);

                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageBitmap(rotatedBmp);
                imagesLayout.addView(imageView);
            }

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setView(scrollView);
            dialogBuilder.setTitle(R.string.dialog_title_high_res_images);
            dialogBuilder.setPositiveButton(R.string.btn_close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialogBuilder.create().show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (highResImagesBundle != null) {
            highResImagesBundle.clearSavedState();
        }
    }

    /**
     * Loads expected result data from given intent and creates appropriate fragment
     * pager adapter. This method is called from {@link BaseResultActivity#onCreate(Bundle)}
     * @param intent contains result data.
     * @return appropriate fragment pager adapter for the result data.
     */
    @NonNull
    protected abstract FragmentPagerAdapter createResultFragmentPagerAdapter(Intent intent);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (highResImagesBundle != null) {
            highResImagesBundle.saveState();
        }
    }

    protected void setActivityContentView() {
        setContentView(R.layout.result_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (highResImagesBundle == null || highResImagesBundle.getImages().isEmpty()) {
            return false;
        } else {
            getMenuInflater().inflate(R.menu.menu_save_high_res, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save_high_res) {
            saveHighResImages();
            return true;
        } else if (item.getItemId() == R.id.action_show_high_res) {
            showHighResImagesDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveHighResImages() {
        for(HighResImageWrapper image : highResImagesBundle.getImages()) {
            String currentTime = String.valueOf(System.currentTimeMillis());
            String imageName = currentTime + ".jpeg";
            ImageUtils.storeHighResImage(this.getApplicationContext(), imageName, image);
        }
    }

    private void createUser(final String n, final String ln, final String s, final String add, final String bd, final String age, final String expi, final String expe, final String bp,
        final String nac, final String cstatus, final String id, final String mrz, final String dType, final String f, final String l, final String p) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BaseResultActivity.this, "Registro guardado"+response, Toast.LENGTH_SHORT).show();
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

                return params;

            }
        };
        System.out.println("FECHA STRING");
        System.out.println(expi);
        requestQueue.add(stringRequest);
    }

}
