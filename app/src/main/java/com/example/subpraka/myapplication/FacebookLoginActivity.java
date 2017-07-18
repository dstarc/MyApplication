package com.example.subpraka.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by subpraka on 7/8/2017.
 */

public class FacebookLoginActivity extends AppCompatActivity {

    LoginButton fbLogInButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        FacebookSdk.sdkInitialize(getApplicationContext());
        fbLogInButton = (LoginButton) findViewById(R.id.fb_login_btn);
        callbackManager = CallbackManager.Factory.create();
        fbLogInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Toast.makeText(FacebookLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(),MainPageActivity.class);
//                startActivity(intent);
                //getApplicationContext().startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
                Toast.makeText(FacebookLoginActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FacebookLoginActivity.this, MainPageActivity.class);
                FacebookLoginActivity.this.startActivity(intent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(FacebookLoginActivity.this, "Login cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Facebook : ", error.getMessage());
            }
        });
    }

//    private void goMainScreen() {
//        Intent intent = new Intent(getApplicationContext(),MainPageActivity.class);
//      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Data ", Toast.LENGTH_SHORT).show();

    }
}
