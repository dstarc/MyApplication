package com.example.subpraka.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.subpraka.myapplication.DatabaseHandler;
import com.example.subpraka.myapplication.MainActivity;
import com.example.subpraka.myapplication.R;
import com.example.subpraka.myapplication.UserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by subpraka on 7/11/2017.
 */

public class RegisterActivity extends AppCompatActivity {

//    private static final String TAG = RegisterActivity.class.getSimpleName();
//    private EditText inputFullName;
//    private EditText inputEmail;
//    private EditText inputPassword;
//    private ProgressDialog pDialog;
//    private SQLiteHandler db;

    private Button linkToLoginScreen ;
    EditText rgName;
    EditText rgEmail;
    EditText rgPassword;
    Button rgRegisterBtn;
    EditText rgCnfPassword;
    DatabaseHandler db = new DatabaseHandler(RegisterActivity.this);

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db= new DatabaseHandler(this);

        linkToLoginScreen = (Button)findViewById(R.id.btnLinkToLoginScreen);
        linkToLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkToLoginIntent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(linkToLoginIntent);
            }
        });
                rgName = (EditText) findViewById(R.id.name_rgpage);
                rgEmail = (EditText) findViewById(R.id.email_rgpage);
                rgPassword = (EditText) findViewById(R.id.password_rgpage);
                 rgCnfPassword= (EditText)findViewById(R.id.cnf_Password_rgpage);
                rgRegisterBtn = (Button) findViewById(R.id.btnRegister_rgpage);

                rgRegisterBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHandler db = new DatabaseHandler(RegisterActivity.this);
                        Log.d("Insert: ", "Inserting..");
                        String finalName = rgName.getText().toString();
                        String finalEmail = rgEmail.getText().toString();
                        String finalPassword = rgPassword.getText().toString();
                        String cnfPassword = rgCnfPassword.getText().toString();

                        boolean resultUser = db.searchUser(rgEmail.getText().toString());

                        if (finalName.isEmpty() || finalName.length() > 32) {
                            rgName.setError("Please enter name first");
                            return;
                        }
                        if (finalEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(finalEmail).matches()) {
                            rgEmail.setError("Please enter valid email");
                            return;
                        }
                        if (resultUser) {
                            Toast.makeText(RegisterActivity.this, "User already exists! Try with different email id", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (finalPassword.isEmpty() || !isValidPassword(finalPassword)) {
                            rgPassword.setError("Password must contain a special character ,number and an upper case letter");
                            return;
                        }
                        if (!finalPassword.equals(cnfPassword)) {
                            Toast.makeText(RegisterActivity.this, "Password doesn't match! Re-enter " +
                                    "password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                            long rowID = db.addUser(new UserInfo(finalName, finalEmail, finalPassword));
                            if (rowID != -1) {
                                Toast.makeText(RegisterActivity.this, "Registration Successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                Intent toLoginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(toLoginIntent);
                                rgName.setText("");
                                rgEmail.setText("");
                                rgPassword.setText("");
                                rgCnfPassword.setText("");

                            }else {
                                Toast.makeText(RegisterActivity.this, "Not Success", Toast.LENGTH_SHORT).show();
                            }
                        }

//                        if (!finalPassword.equals(cnfPassword)) {
//                            Toast.makeText(RegisterActivity.this, "Password doesn't match! Re-enter password", Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            if(finalEmail.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(finalEmail).matches()){
//                                rgEmail.setError("Please enter valid email");
//                            }
//                            else if (finalName.length() == 0 && finalEmail.isEmpty() && finalPassword.length() == 0) {
//                                Toast.makeText(RegisterActivity.this, "ERROR! First fill all details ", Toast.LENGTH_SHORT).show();
//                            } else if (rowID != -1) {
//                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                                Intent toLoginIntent = new Intent(RegisterActivity.this, MainActivity.class);
//                                startActivity(toLoginIntent);
//                            } else {
//                                Toast.makeText(RegisterActivity.this, "Not Success", Toast.LENGTH_SHORT).show();
//                            }
//                        }

                });

            }

    private void register(){

    }

    }

