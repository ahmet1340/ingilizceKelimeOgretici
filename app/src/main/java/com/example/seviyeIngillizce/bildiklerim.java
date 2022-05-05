package com.example.seviyeIngillizce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class bildiklerim extends AppCompatActivity {
    SwipeMenuListView lst1;
    EditText edittext;
    TextView txt1;
    SharedPreferences ayarlar;
    String[] bilinenler;

    String[] soru=null ;
    String[] cevap=null ;
    String[] idNo=null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bildiklerim);

        edittext=findViewById(R.id.editText2);
        txt1=findViewById(R.id.baslik);


        lst1=findViewById(R.id.list1);
        ayarlar = getSharedPreferences("Veri", Context.MODE_PRIVATE);
        bilinenler=ayarlar.getString("bilinenler","").split("-");

        txt1.setText("Bildiklerim ("+bilinenler.length+")");

        soru=new String[bilinenler.length];
        cevap=new String[bilinenler.length];
        idNo=new String[bilinenler.length];


        soru[0]="Henüz Kelime Eklememişsiniz";
        cevap[0]="";

        if(bilinenler.length>0) {
            FileInputStream fr = null;
            try {

                InputStreamReader isr = new InputStreamReader(getResources().openRawResource(R.raw.ming2), Charset.forName("utf-8"));
                BufferedReader bf = new BufferedReader(isr);
                String line = bf.readLine();

                int j = 0;
                int deger = 0;
                while (line != null) {
                    String[] par = line.split(":");

                    for (int i = 0; i < bilinenler.length; i++) {

                          if (bilinenler[i].equals(String.valueOf(j))) {

                            cevap[deger] = par[0];
                            soru[deger] = par[1];
                            idNo[deger]=String.valueOf(j);

                            deger++;
                            break;
                        }

                    }

                    j++;
                    line = bf.readLine();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for(int i=0;i<bilinenler.length;i++){
            if(soru[i]==null){
               soru[i]="";
               cevap[i]="";
            }

        }

    final ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.ozellistview, R.id.listviewText, soru);
        lst1.setAdapter(ad);


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());

                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth( (90));
                deleteItem.setIcon(R.drawable.ic_delete_forever_black_24dp);
                menu.addMenuItem(deleteItem);
            }
        };

        lst1.setMenuCreator(creator);
        lst1.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        SharedPreferences.Editor editor = ayarlar.edit();
                        String yeniliste= ayarlar.getString("bilinenler","").replace("-"+idNo[position]+"-","-");

                        editor.putString("bilinenler",yeniliste);
                        editor.commit();
                        soru[position]="Bilinmeyenlere Eklendi";
                        cevap[position]="Silindi";
                        ad.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.listviewText);
                textView.setText(soru[position]+" : "+cevap[position]);
            }
        });
      /*  lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

              //  Toast.makeText(getApplicationContext(),iller[position], Toast.LENGTH_LONG).show();

            }
        }); */
    }
}
