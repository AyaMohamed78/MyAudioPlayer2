package com.example.myaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    EditText usernameInputSignIn, passwordInputSignIn;
    String usernameTextSignIn, passwordTextSignIn;
    Button signInButton;
    FirebaseAuth mAuth;
    TextView signUpText, welcomeTextView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameInputSignIn = findViewById(R.id.username);
        passwordInputSignIn = findViewById(R.id.password);
        signInButton = findViewById(R.id.button2);
        signUpText = findViewById(R.id.textView177);
        welcomeTextView = findViewById(R.id.welcomeTextView);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // Check if the user's email is available in SharedPreferences
        String userEmail = sharedPreferences.getString("user_email", null);
        if (userEmail != null) {
            // Display a welcome message with the user's email
            welcomeTextView.setText("Welcome, " + userEmail + "!");
        }




        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameTextSignIn = usernameInputSignIn.getText().toString();
                passwordTextSignIn = passwordInputSignIn.getText().toString();

                if (TextUtils.isEmpty(usernameTextSignIn) || TextUtils.isEmpty(passwordTextSignIn)) {
                    Toast.makeText(SignIn.this, "Enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(usernameTextSignIn, passwordTextSignIn)
                        .addOnCompleteListener(SignIn.this, task -> {
                            if (task.isSuccessful()) {
                                // SignIn successful
                                Toast.makeText(SignIn.this, "SignIn successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignIn.this, MainActivity.class));

                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignIn.this, "Authentication failed. Check your username and password.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUp activity
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });

    }
}
