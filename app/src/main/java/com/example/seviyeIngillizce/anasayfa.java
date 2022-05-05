package com.example.seviyeIngillizce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;


public class anasayfa extends Activity implements View.OnClickListener{
    Button basla;
    Button cikis;
    Button butonBildiklerim;

    private TextSwitcher kayanYazi;
    private Button nextButton;
    private int zorluk = 0;
    private String[] row = {"Bil. Mes. İng. 2"};
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        kayanYazi = findViewById(R.id.kayanYazi);

        kayanYazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zorluk == row.length - 1) {
                    zorluk = 0;
                    kayanYazi.setText(row[zorluk]);
                } else {
                    kayanYazi.setText(row[++zorluk]);
                }
            }
        });

        kayanYazi.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView = new TextView(anasayfa.this);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(45);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);

                return textView;
            }
        });

       kayanYazi.setText(row[zorluk]);

        basla=(Button)findViewById(R.id.buttonbasla);
        butonBildiklerim=(Button)findViewById(R.id.buttonBildiklerim);
        cikis=(Button)findViewById(R.id.buttoncikis);

         basla.setOnClickListener(this);
        butonBildiklerim.setOnClickListener(this);
        cikis.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startMain);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonbasla:
                Intent sayfa =new Intent(getBaseContext(),MainActivity.class);
                sayfa.putExtra("zorluk",zorluk);
                startActivity(sayfa);
                break;
            case R.id.buttonBildiklerim:
                Intent sayfa2 =new Intent(getBaseContext(),bildiklerim.class);
                //sayfa2.putExtra("zorluk",zorluk);
                startActivity(sayfa2);
                break;
            case R.id.buttoncikis:
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(startMain);
                break;
        }
    }
}
