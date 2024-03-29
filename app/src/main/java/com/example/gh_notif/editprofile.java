package com.example.gh_notif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editprofile extends AppCompatActivity {


    TextInputEditText prfullname, prusername, premail, prpassword;
    Button saveprofile;
    DatabaseReference databaseReference;


    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(editprofile.this, profilefrag.class));
            finish();
            return;
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        prfullname = findViewById(R.id.fullname);
        prusername = findViewById(R.id.username);
        premail = findViewById(R.id.email);
        prpassword = findViewById(R.id.password);
        saveprofile = findViewById(R.id.save);

        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveuserprofile();
            }
        });

    }

    public void saveuserprofile() {
        String fullName = prfullname.getText().toString().trim();
        String username = prusername.getText().toString().trim();
        String email = premail.getText().toString().trim();
        String password = prpassword.getText().toString().trim();

        // Check if any field is empty
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        // Create a user object
        User user = new User(email, password);

        // Save user object to Firebase Database
        databaseReference.setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(editprofile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(editprofile.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    public class User {
        public User(String email, String password) {
        }
    }

}