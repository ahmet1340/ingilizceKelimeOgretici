package com.example.seviyeIngillizce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sonuc extends Activity implements View.OnClickListener {
  //  Button basla;
    Button anasayfa;
    Button cikis;
    TextView sonuctxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);


       // basla=(Button)findViewById(R.id.buttonbasla);
        anasayfa=(Button)findViewById(R.id.buttonanasayfa);
        cikis=(Button)findViewById(R.id.buttoncikis);

    //    basla.setOnClickListener(this);
        anasayfa.setOnClickListener(this);
        cikis.setOnClickListener(this);

        String sonuc= getIntent().getExtras().getString("sonuc");
        sonuctxt=(TextView)findViewById(R.id.sonuc);
        sonuctxt.setText(sonuc);


    }
    @Override
    public void onBackPressed() {
        Intent i =new Intent(getBaseContext(),anasayfa.class);
        startActivity(i);
        super.onBackPressed();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
         /*   case R.id.buttonbasla:
                Intent sayfa =new Intent(getBaseContext(),MainActivity.class);
                startActivity(sayfa);
                break;*/
            case R.id.buttonanasayfa:
                Intent anasayfa =new Intent(getBaseContext(),anasayfa.class);
                startActivity(anasayfa);
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
