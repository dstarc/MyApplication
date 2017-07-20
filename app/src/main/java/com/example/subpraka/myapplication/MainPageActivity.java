package com.example.subpraka.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.subpraka.myapplication.helper.SessionManager;

public class MainPageActivity extends AppCompatActivity {

    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        final SessionManager session = new SessionManager(this);

        if (!session.isLoggedIn()) {
            session.setLogin(false);
            finish();
            startActivity(new Intent(MainPageActivity.this, MainActivity.class));
        }
        logoutBtn = (Button) findViewById(R.id.btnLogout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent logoutIntent = new Intent(MainPageActivity.this,MainActivity.class);
//                startActivity(logoutIntent);
                session.setLogin(false);
                finish();
                startActivity(new Intent(MainPageActivity.this, MainActivity.class));
                Toast.makeText(MainPageActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
