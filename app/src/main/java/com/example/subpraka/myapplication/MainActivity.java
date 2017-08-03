package com.example.subpraka.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.subpraka.myapplication.activity.RegisterActivity;
import com.example.subpraka.myapplication.helper.SessionManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

public class MainActivity extends AppCompatActivity implements FingerPrintAuthCallback, View.OnClickListener {
    FingerPrintAuthHelper mFingerPrintAuthHelper;
    Button linkedinbutton;

    LoginButton fbLogInButton;
    CallbackManager callbackManager;
    Button btnFingerpeintSettings;

    //    private SQLiteHandler db;
    private SessionManager session;
    AlertDialog alertDialog;
    DatabaseHandler handler = new DatabaseHandler(this);
   // private SplashActivity.Session newSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0 & getIntent()
//                .getExtras() == null) {
//            finish();
//            return;
//        }

        /** Handling session here ... Shared preferences data */

//        session=new SessionManager(this);
//        if (session.isLoggedIn()){
//            Intent sessionIntent= new Intent(MainActivity.this,MainPageActivity.class);
//            startActivity(sessionIntent);
//            finish();
//        }

        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        linkedinbutton = (Button) findViewById(R.id.li_signin);
        linkedinbutton.setOnClickListener(this);

        btnFingerpeintSettings = (Button) findViewById(R.id.btn_fingerprint_settings);
        btnFingerpeintSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callFingerprintSettingIntent = new Intent(Settings.ACTION_SETTINGS);
//                Intent intentSettings = new Intent();
//                intentSettings.setAction(Settings.EXTRA_INPUT_METHOD_ID);
                MainActivity.this.startActivity(callFingerprintSettingIntent);
            }
        });


        final EditText userEmail = (EditText) findViewById(R.id.email);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final Button btnRegisterFront = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        final String email = userEmail.getText().toString();
        final String userPassword = etPassword.getText().toString();

        btnRegisterFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });


        // Handling Login Button Click//

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = userEmail.getText().toString();
                final String userPassword = etPassword.getText().toString();

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    userEmail.setError("Please enter email id");
                }else if (userPassword.isEmpty()){
                    etPassword.setError("First enter password");
                } else {
                    boolean result = handler.searchpass(userEmail.getText().toString(),
                            etPassword.getText().toString());

                    if (result) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                        startActivity(intent);
                        userEmail.setText("");
                        etPassword.setText("");
                        //Below line session code
                         //session.setLogin(true);

                    } else {
                        Toast.makeText(MainActivity.this, "Please enter valid credentials.", Toast.LENGTH_SHORT).show();
                    }
                }


//                    if (userName.length() == 0 && userPassword.length() == 0) {
//                  Toast.makeText(MainActivity.this, "First Enter Your Credentials", Toast.LENGTH_LONG).show();
//               }
//                     if (email.equals(userEmail)&& userPassword.equals(password)){
//                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
//                        startActivity(intent);
//                    }
//                    else {Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
//               }

//                if (userName.equals("dexter") && userPassword.equals("1234")) {
//                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
//                    startActivity(intent);
//                } else if (userName.equals("subhash") && userPassword.equals("1234")) {
//                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
//                    startActivity(intent);
//                } else if (userName.length() == 0 && userPassword.length() == 0) {
//                    Toast.makeText(MainActivity.this, "First Enter Your Credentials", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
//                }
            }
        });
                   /** Facebook login */

        fbLogInButton = (LoginButton) findViewById(R.id.fb_login_btn);
        callbackManager = CallbackManager.Factory.create();
        fbLogInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //getApplicationContext().startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
                Toast.makeText(MainActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                MainActivity.this.startActivity(intent);

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Login cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Facebook : ", error.getMessage());
            }
        });

        btnRegisterFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });
//        if (AccessToken.getCurrentAccessToken()==null){
//            goLoginScreen();
//
//  }
//
//    private void goLoginScreen() {
//        Intent intent = new Intent(this,MainPageActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }

    }


                       /** Fingerprint Authentication code */
    @Override
    protected void onResume() {
        super.onResume();
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(this, "No fingerprint sensor found!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNoFingerPrintRegistered() {
        Toast.makeText(this, "No Fingerprint registered! Register at least one fingerprint in Settings", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(this, "Your device doesn't support fingerprint authnetication", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Toast.makeText(this, "Authentication Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
        MainActivity.this.startActivity(intent);

    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                Toast.makeText(this, "Cannot recognise", Toast.LENGTH_SHORT).show();
                showDialogFingerPrint();
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //This is not recoverable error. Try other options for user authentication. like pin, password.
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                //Any recoverable error. Display message to the user.
                break;
        }

    }

    private void showDialogFingerPrint() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Fingerprint");
        builder.setMessage("Fingerprint not recognised! Please register fingerprint in settings");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(
                        new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        LISessionManager.getInstance(getApplicationContext()).init(MainActivity.this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                Toast.makeText(MainActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                MainActivity.this.startActivity(intent);
                //   fetchPersonalInfo();
                // getApplicationContext().startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
            }

            @Override
            public void onAuthError(LIAuthError error) {
                Toast.makeText(MainActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
            }
        }, true);

    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "successful login ", Toast.LENGTH_SHORT).show();
    }

}
