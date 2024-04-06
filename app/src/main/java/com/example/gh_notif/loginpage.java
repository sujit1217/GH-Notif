package com.example.gh_notif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginpage extends AppCompatActivity {

    TextInputEditText username, password;
    Button login, signup;
    FirebaseAuth authenticate;

    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://gh-notif-d5754-default-rtdb.firebaseio.com");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginpage);

        login=findViewById(R.id.loginbutton);
        signup=findViewById(R.id.signupbutton);
        username=findViewById(R.id.lusername);
        password=findViewById(R.id.lpassword);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernametxt= username.getText().toString();
                final String passwordtxt= password.getText().toString();

                if(usernametxt.isEmpty() || passwordtxt.isEmpty())
                {
                    Toast.makeText(loginpage.this, "please enter your email and password", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usernametxt)){
                                final String getpassword = snapshot.child(usernametxt).child("password").getValue(String.class);

                                if(getpassword.equals(passwordtxt))
                                {
                                    Toast.makeText(loginpage.this, "successfully logged in ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(loginpage.this, editprofile.class));
                                    finish();

                                }else{
                                    Toast.makeText(loginpage.this, "wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(loginpage.this, "wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginpage.this, forgotpassword.class));
            }
        });*/

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginpage.this, signup.class));
            }
        });
    }
}