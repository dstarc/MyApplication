package com.example.subpraka.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

/**
 * Created by subpraka on 7/10/2017.
 */

public class LinkedInLoginActivity extends AppCompatActivity {

    Button linkedinbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkedinbutton= (Button)findViewById(R.id.li_signin);
        linkedinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LISessionManager.getInstance(getApplicationContext()).init(LinkedInLoginActivity.this, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {
                        Toast.makeText(LinkedInLoginActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(LinkedInLoginActivity.this,MainActivity.class);
                        LinkedInLoginActivity.this.startActivity(intent);
                        //   fetchPersonalInfo();
                        // getApplicationContext().startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        Toast.makeText(LinkedInLoginActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                    }
                }, true);

            }
        });
    }


    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this,requestCode,resultCode,data);
    }
}
