package com.example.seviyeIngillizce;

import androidx.annotation.RequiresApi;
import android.content.SharedPreferences;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Random;

public class MainActivity extends Activity  {

//          ,{"SORU ","Cevap1","Cevap2","Cevap3","Cevap4","3",""}

MediaPlayer mediaPlayer =new MediaPlayer();

    String[] soru = null;
    String[] cevap = null;
    String[] idNo = null;

    String[] bilinenler;

    int kacincisoru=0;

    int toplamsorusayisi=15;

    String oyunzorlugu="kolay";
    Boolean oyunkilidi=false;
    boolean oyunBittimi=false;

    int[] gosterilenler;

    LinearLayout layout;

    TextView soru_txt;
    TextView soru_sayisi_txt;
    TextView bildiklerim;

    ImageView joker1;
    ImageView joker2;
    ImageView joker3;

    boolean joker1Hakki=true;
    boolean joker2Hakki=true;
    boolean joker3Hakki=true;

    int random=0;

    SharedPreferences ayarlar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          ayarlar = getSharedPreferences("Veri", Context.MODE_PRIVATE);


        soru=new String[toplamsorusayisi];
        cevap=new String[toplamsorusayisi];
        idNo=new String[toplamsorusayisi];

        layout=  findViewById(R.id.layout);

        if(getIntent().getExtras().getInt("zorluk")==0){
            oyunzorlugu="kolay";
        }else   if(getIntent().getExtras().getInt("zorluk")==1){
            oyunzorlugu="orta";
        }else  if(getIntent().getExtras().getInt("zorluk")==2){
            oyunzorlugu="zor";
        }


            gosterilenler=new int[toplamsorusayisi];

        for (int i=0;i<toplamsorusayisi;i++){
            gosterilenler[i]=-1;
        }

        soru_txt=(TextView)findViewById(R.id.textView);
        soru_sayisi_txt=(TextView)findViewById(R.id.textView4);
        bildiklerim=(TextView)findViewById(R.id.bildiklerim);

        joker1=(ImageView)findViewById(R.id.joker1);
        joker2=(ImageView)findViewById(R.id.joker2);
        joker3=(ImageView)findViewById(R.id.joker3);



        //-------------BilinenCek
              SharedPreferences.Editor editor = ayarlar.edit();

        editor.putString("bilinenler",ayarlar.getString("bilinenler","").replace("--","-"));
        editor.commit() ;


        bilinenler=ayarlar.getString("bilinenler","").split("-");




        //-------------BilinenCek!



        FileInputStream fr= null;
        try {

            InputStreamReader isr=new InputStreamReader(getResources().openRawResource(R.raw.ming2), Charset.forName("utf-8"));//ISO-8859-9
            BufferedReader bf=new BufferedReader(isr);
            String line=bf.readLine();
           int i=0;
           int j=0;
            while(line!=null){
                String[] par=line.split(":");

               /* editor.putString("bilinenler","");
                editor.commit() ;
                if (j < 600) {
                    editor.putString("bilinenler",ayarlar.getString("bilinenler","")+j+"-");
                    editor.commit() ;
                }*/
               if(bilinenKontrol(j)) {

                    cevap[i] = par[0];
                    soru[i] = par[1];
                    idNo[i]=String.valueOf(j);

                    if (i >= toplamsorusayisi - 1) {
                        break;
                    }
                 i++;
                }
                j++;
                line=bf.readLine();

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        //--------------------------------------------------------

        yenisoru(true,false);

        bildiklerim.setText("Bildiklerim\n"+bilinenler.length);
        //---------------------------------------------------------

       /*for (int i=0;i<bilinenler.length;i++){
            soru_sayisi_txt.setText( soru_sayisi_txt.getText()+"*"+bilinenler[i]);
        }*/

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

       /* Intent i =new Intent(getBaseContext(),anasayfa.class);
        startActivity(i);*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void swapView(final View v){
        if(!oyunkilidi){


        TransitionManager.beginDelayedTransition(layout);

                    if( v ==  joker1){

                        soru_txt.setText(soru[random]+" : "+cevap[random]);
                        joker2.setVisibility(View.VISIBLE);
                        joker3.setVisibility(View.VISIBLE);
                        joker1.setVisibility(View.INVISIBLE);

                    }else if( v ==  joker2){

                       yenisoru(true,false);


                    }else if( v ==  joker3){

                        yenisoru(true,true);

                    }



        if(!joker1Hakki){
            joker1.setVisibility(View.INVISIBLE);
        }
        if(!joker2Hakki){
            joker2.setVisibility(View.INVISIBLE);
        }
        if(!joker3Hakki){
            joker3.setVisibility(View.INVISIBLE);
        }
        }
    }

    public  void bilineneEkle(){

        SharedPreferences.Editor editor = ayarlar.edit();
        String s="";
        for(int i=0;i<bilinenler.length;i++){
          s +=bilinenler[i]+"-";
        }
        s+=idNo[random]+"-";
        editor.putString("bilinenler", s );
        editor.commit();
        bilinenler=ayarlar.getString("bilinenler","boş").split("-");

        bildiklerim.setText("Bildiklerim\n"+bilinenler.length);
        Animation bounce=AnimationUtils.loadAnimation(this,R.anim.bounce);

        bounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        bildiklerim.startAnimation(bounce);


    }

    /*   @Override
     public void onClick(View v) {
           oyunkilitle();
           final Animation btnanim= AnimationUtils.loadAnimation(this, R.anim.btn);

         final  Button gelen=(Button) v;
           v.startAnimation(btnanim);
           btnanim.setAnimationListener(new Animation.AnimationListener() {
               @Override
               public void onAnimationStart(Animation animation) {

               }

               @Override
               public void onAnimationEnd(Animation animation) {
                   if( cevapKontrol((Button) gelen,random)){
                       gelen.setBackground(getResources().getDrawable(R.drawable.butonstil_green));
                     //  sure_txt.setText("");


                   }else{
                       gelen.setBackground(getResources().getDrawable(R.drawable.butonstil_red));

                       String s="";


                       oyunbitir("YANLIS CEVAP\n"+toplamsorusayisi+" Sorudan "+(kacincisoru-1)+" Tanesi Doğru",2500);
                   }
                   oyunkilitle();

                   if(!oyunBittimi){
                       yenisoru(true,false);
                   }
               }
               @Override
               public void onAnimationRepeat(Animation animation) {

               }
           });

       }*/
    public void oyunkilitle(){
        oyunkilidi=true;
    }
    public void oyunkilitac(){
        oyunkilidi=false;
    }
    public boolean cevapKontrol(Button b,int soru){
            return false;
    }
    public void oyunbitir(final String sonucCevap,final int zaman){
        oyunBittimi=true;


        oyunkilitle();
        Thread timer2 = new Thread() {
            public void run(){
                try {

                        sleep(zaman);

                    Intent sonuc=new Intent(getBaseContext(),sonuc.class);
                    sonuc.putExtra("sonuc",sonucCevap);
                    finish();
                    startActivity(sonuc);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer2.start();
    }

    public void yenisoru(boolean AnimasyonDurumu, final boolean AnimasyonTuru) {

        joker2.setVisibility(View.INVISIBLE);
        joker3.setVisibility(View.INVISIBLE);
        joker1.setVisibility(View.VISIBLE);
        if (kacincisoru >= toplamsorusayisi) {
            if(AnimasyonTuru){
                bilineneEkle();
            }
            oyunbitir("Tebrikler\nTüm Kelimeleri\nGözden Geçirdiniz",1500);
        } else {
            if(AnimasyonDurumu) {
                final Animation animasyon ;
                final Animation animasyon2;
                if(AnimasyonTuru){
                    animasyon = AnimationUtils.loadAnimation(this, R.anim.asagi_kay);
                }else{
                    animasyon = AnimationUtils.loadAnimation(this, R.anim.asagidan_yukari_kay);
                }
                animasyon2 = AnimationUtils.loadAnimation(this, R.anim.yukaridan_asagi_kay);

                //final Animation sola = AnimationUtils.loadAnimation(this, R.anim.sola_kay);
                //final Animation saga = AnimationUtils.loadAnimation(this, R.anim.saga_kay);

                animasyon.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        soru_txt.setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                      //  soru_txt.setTextColor(Color.BLACK);
                        oyunkilitac();
                        soru_txt.setVisibility(View.INVISIBLE);


                        if(AnimasyonTuru){
                            bilineneEkle();
                        }
                        Random rnd = new Random();
                        random = rnd.nextInt(toplamsorusayisi);
                        while (soruRandomKontrol(random)){
                            random = rnd.nextInt(toplamsorusayisi);
                        }
                        //-----------Kelime Getir

                        soru_txt.setText(soru[random]);

                        try {
                            sesOku(soru[random]);
                        } catch (IOException e) {
                            Log.d("hata",e.toString());
                            e.printStackTrace();
                        }

                        //-----------Kelime Getir!

                        gosterilenler[kacincisoru]=random;
                        kacincisoru++;
                        soru_sayisi_txt.setText(kacincisoru + "/" + toplamsorusayisi);

                            animasyon2.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    soru_txt.setTextColor(Color.WHITE);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                    soru_txt.setTextColor(Color.BLACK);
                                    oyunkilitac();
                                    soru_txt.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            soru_txt.startAnimation(animasyon2);



                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                soru_txt.startAnimation(animasyon);

            }else{

            }

        }
    }

    private void sesOku(String s) throws IOException {
/*

        String url=("http://tts.voicetech.yandex.net/tts?format=mp3&quality=hi&platform=web&application=translate&lang=tr_TR&speed=1&text=merhaba");

        try {
            MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(url);
            player.prepare();
            player.start();
        } catch (Exception e) {
            // TODO: handle exception
        }*/
    }

    public boolean bilinenKontrol(int rnd){


        for (int i=0;i<bilinenler.length;i++){

            if(bilinenler[i].equals(String.valueOf(rnd))){
                soru_txt.setText(soru_txt.getText()+" :"+bilinenler[i]);
                return false;

            }

        }
        return true;
    }

    public boolean soruRandomKontrol(int rnd){


        for (int i=0;i<toplamsorusayisi;i++){
            if(gosterilenler[i]==rnd){
               // soru_sayisi_txt.setText(soru_sayisi_txt.getText()+" :"+gosterilenler[i]);
                return true;
            }
        }
        return false;
    }


}
