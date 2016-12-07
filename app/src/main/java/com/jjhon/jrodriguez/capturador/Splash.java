package com.jjhon.jrodriguez.capturador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.bar);
        Thread tiempo = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent intent= new Intent(Splash.this,Logueo.class);
                    startActivity(intent);
                }
            }
        };
        progressBar.setVisibility(View.VISIBLE);
        tiempo.start();


    }

    @Override
    protected void onPause() {
        super.onPause();
        progressBar.setVisibility((View.GONE));
        finish();
    }
}



