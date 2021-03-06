package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;


public class LoginSignupActivity extends Activity {
    // Declare Variables
    private Button loginbutton;
    private Button signup;
    private String usernametxt;
    private String passwordtxt;
    private EditText password;
    private EditText username;
    private int alertLength;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Get the view from main.xml
        setContentView(R.layout.loginsignup);

        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        // Locate Buttons in main.xml
        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        // Check if the device is connected to the internet
        if (!connectionAvailable()){
            alertLength = 5;
            for (int i = 0; i < alertLength; i++) {
                Toast.makeText(
                        getApplicationContext(),
                        "Please connect to the internet",
                        Toast.LENGTH_LONG).show();
            }
        }

        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(
                                            LoginSignupActivity.this,
                                            Welcome.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully logged in",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "No such user exist, please signup",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        // Sign up Button Click Listener
        signup.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Force user to fill up the form
                if (usernametxt.length()==0 || passwordtxt.length() ==0) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();

                }

                else if (!isAlphaNumeric(usernametxt) || usernametxt.length() < 4
                        || usernametxt.length() >16)  {

                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Username Between 4 and 16 Alphanumeric Characters",
                            Toast.LENGTH_LONG).show();

                }

                else if(!isAlphaNumericSpecial(passwordtxt) || passwordtxt.length() < 4
                        || passwordtxt.length() >16){

                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Password Between 4 and 16 Alphanumeric Characters",
                            Toast.LENGTH_LONG).show();
                }

                else {
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    ArrayList<Group> group = new ArrayList<Group>();
                    user.put("groups",group);

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Intent intent = new Intent(
                                        LoginSignupActivity.this,
                                        Welcome.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),
                                        "Successfully signed up.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }

            }
        });
    }

    public static boolean getNetworkState(Context pContext)
    {
        ConnectivityManager connect = null;
        connect =  (ConnectivityManager)pContext.getSystemService(pContext.CONNECTIVITY_SERVICE);

        if(connect != null)
        {
            NetworkInfo result = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (result != null && result.isConnectedOrConnecting())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
            return false;
    }

    public static boolean getWifiState(Context context)
    {
        ConnectivityManager connect =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ( connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean connectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public boolean isAlphaNumeric(String s) {
        String pattern = "^[a-zA-Z0-9]*$";
        return (s.matches(pattern));
    }

    public boolean isAlphaNumericSpecial(String s) {
        String pattern = "^[a-zA-Z0-9 ~ ! @ # $ % ^ & * ( ) _ - + = { } [ ] \\ | : ; \" ' < > , . ?]*$";
        return (s.matches(pattern));
    }

}