package com.example.myaudioplayer;

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

public class SignUp extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    String usernameText, passwordText;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameInput = findViewById(R.id.editTextText3);
        passwordInput = findViewById(R.id.editTextTextPassword3);
        sp=getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);


        TextView signInText = findViewById(R.id.textView11);
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the SignIn activity
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });

        Button signUpButton = findViewById(R.id.button2);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameText = usernameInput.getText().toString();
                passwordText = passwordInput.getText().toString();
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("username", usernameText);
                editor.putString("password", passwordText);
                editor.commit();
                Toast.makeText(SignUp.this, "welcome " + usernameText, Toast.LENGTH_SHORT).show();


                // Handle sign up logic
                // For example, validate credentials and navigate to MainActivity
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the SignUp activity so that the user cannot go back to it
            }
        });
    }
}
