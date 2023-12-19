package com.example.myaudioplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {
    EditText username, password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        TextView signUpText = findViewById(R.id.textView12);
        username= findViewById(R.id.username);
        password= findViewById(R.id.password);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        // Handle Sign In button click
        Button signInButton = findViewById(R.id.button2);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs" , Context.MODE_PRIVATE) ;
                String getusername = sp.getString("username","");
                String getpassword = sp.getString("password" ,"");


                if(getusername.equals(username.getText().toString()) & getpassword.equals(password.getText().toString())){
                    // Handle sign in logic
                    // For example, validate credentials and navigate to MainActivity
                    Toast.makeText(SignIn.this, "Welcome to LyriX", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignIn.this, MainActivity.class);


                    startActivity(intent);
                    finish(); // Finish the SignIn activity so that the user cannot go back to it

                }
                else {
                    Toast.makeText(SignIn.this, "Check your username or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

