import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.microblink.blinkid.R;

import java.util.Observable;
import java.util.Observer;

public class result_menu extends AppCompatActivity implements Observer, View.OnClickListener {

    private Button Button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_menu);

        Button1 = findViewById(R.id.btnUseResult);
        Button1.setOnClickListener(this);

        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");



    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnUseResult){
            System.out.println("hola");

        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
