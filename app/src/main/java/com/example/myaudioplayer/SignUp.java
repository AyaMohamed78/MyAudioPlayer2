package com.example.myaudioplayer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    String usernameText, passwordText;
    Button signUpButton;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameInput = findViewById(R.id.editTextText3);
        passwordInput = findViewById(R.id.editTextTextPassword3);
        signUpButton = findViewById(R.id.button2);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameText = usernameInput.getText().toString();
                passwordText = passwordInput.getText().toString();

                if (TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(SignUp.this, "Enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(usernameText, passwordText)
                        .addOnCompleteListener(SignUp.this, task -> {
                            if (task.isSuccessful()) {
                                // Registration successful
                                Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                // Save user data to Firebase Realtime Database
                                saveUserData(usernameText, passwordText);

                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void saveUserData(String username , String password) {
        // Create a unique key for the user in the "users" node
        String userId = mAuth.getCurrentUser().getUid();

        // Create a User object
        user userr = new user(username , password);

        // Save the User object to the "users" node in Firebase Realtime Database
        mDatabase.child(userId).setValue(userr);
    }
}
