package com.example.subpraka.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT= 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(splashIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

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
