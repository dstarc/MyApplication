package com.example.subpraka.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT= 1800;
    private ProgressBar splashProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashProgressBar=(ProgressBar)findViewById(R.id.progressBar);
       // splashProgressBar.setProgressTintList(ColorStateList.valueOf(Color.CYAN));
        splashProgressBar.setScaleY(3f);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
               // startApp();
                finish();
            }
        }).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(splashIntent);
                finish();
            }
        },SPLASH_TIME_OUT);


    }

    private void doWork(){

        for (int progress=0; progress<38000; progress+=10) {
            try {
                Thread.sleep(100);
                splashProgressBar.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
               // Timber.e(e.getMessage());
            }

            if (progress==30000){
                Intent splashIntent= new Intent(SplashActivity.this,MainActivity.class);
                startActivity(splashIntent);
                finish();
            }
        }
    }

//    private void startApp(){
//
//
//    }

                  /** Shared Preferences Content */

//    public class Session{
//
//        SharedPreferences prefs;
//        SharedPreferences.Editor editor;
//        Context ctx;
//
//        public Session(Context ctx){
//            this.ctx= ctx;
//            prefs=ctx.getSharedPreferences("myapp",Context.MODE_PRIVATE);
//            editor = prefs.edit();
//        }
//        public void setLoggedIn(boolean loggedin){
//            editor.putBoolean("loggedInMode" ,loggedin);
//            editor.commit();
//        }
//
//        public boolean loggedin(){
//            return prefs.getBoolean("loggedInMode" , false);
//        }
//    }


}
