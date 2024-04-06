package com.example.gh_notif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editprofile extends AppCompatActivity {


    TextInputEditText prfullname, prusername, premail, prpassword;
    Button saveprofile;
    String nameUser, emailUser, usernameUser, passwordUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gh-notif-d5754-default-rtdb.firebaseio.com/");


    // FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        prfullname = findViewById(R.id.editprofilefullname);
        prusername = findViewById(R.id.editprofileusername);
        premail = findViewById(R.id.editprofileemail);
        prpassword = findViewById(R.id.editprofilepassword);
        saveprofile = findViewById(R.id.editsave);

        showData();

        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullnamechanged() || emailchanged() || passwordchanged()) {
                    Toast.makeText(editprofile.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(editprofile.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean emailchanged() {
        if (!emailUser.equals(premail.getText().toString())) {
            databaseReference.child(usernameUser).child("email").setValue(premail.getText().toString());
            emailUser = premail.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean fullnamechanged() {
        if (!nameUser.equals(prfullname.getText().toString())) {
            databaseReference.child(usernameUser).child("fullname").setValue(prfullname.getText().toString());
            nameUser = prfullname.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean passwordchanged() {
        if (!passwordUser.equals(prpassword.getText().toString())) {
            databaseReference.child(usernameUser).child("password").setValue(prpassword.getText().toString());
            passwordUser = prpassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }


    public void showData() {
        Intent intent = getIntent();
        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        usernameUser = intent.getStringExtra("username");
        passwordUser = intent.getStringExtra("password");
        prfullname.setText(nameUser);
        premail.setText(emailUser);
        prusername.setText(usernameUser);
        prpassword.setText(passwordUser);
    }
}


      /*  mAuth = FirebaseAuth.getInstance();
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
        }*/


