package com.example.myaudioplayer;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is signed in or not
        // For example, you can use SharedPreferences or any authentication logic
        boolean isUserSignedIn = false; // Set to true if the user is signed in

        if (isUserSignedIn) {
            // User is signed in, navigate to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            // User is not signed in, navigate to SignIn or SignUp
            // For example, navigate to SignIn
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        }

        finish(); // Finish the HomeActivity so that the user cannot go back to it
    }
}

