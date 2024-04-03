package com.example.gh_notif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    TextInputEditText fullname,username,email,password;
    Button Signup;

    TextView alreadyacc;
    String emailcheck= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//initialize firebase database
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://gh-notif-d5754-default-rtdb.firebaseio.com/");
   // FirebaseAuth authenticate;
   // FirebaseUser newuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        alreadyacc=findViewById(R.id.alreadyacc);

        fullname=findViewById(R.id.fullname);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        Signup=findViewById(R.id.signupbutton);
       // authenticate=FirebaseAuth.getInstance();
       // newuser=authenticate.getCurrentUser();

        alreadyacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToNextActivity();
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String fullnametxt=fullname.getText().toString();
                String usernametxt=username.getText().toString();
                String emailtxt=email.getText().toString();
                String passwordtxt=password.getText().toString();

                if(fullnametxt.isEmpty() || emailtxt.isEmpty() || usernametxt.isEmpty() || passwordtxt.isEmpty())
                {
                    Toast.makeText(signup.this, "please enter your email and pasword", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //checking for email is registered or not
                            if(snapshot.hasChild(emailtxt))
                            {
                                Toast.makeText(signup.this,"email is already registered", Toast.LENGTH_SHORT).show();

                            }else {
                                //sending data to firebase
                                databaseReference.child("users").child(emailtxt).child("fullname").setValue(fullnametxt);
                                databaseReference.child("users").child(emailtxt).child("username").setValue(usernametxt);
                                databaseReference.child("users").child(emailtxt).child("password").setValue(passwordtxt);

                                Toast.makeText(signup.this,"user registered successfully", Toast.LENGTH_SHORT);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
               // performauth();
            }
        });
    }
   /* private void performauth()
    {
        Map<String,Object> map= new HashMap<>();
        map.put("fullname",fullname.getText().toString());
        map.put("username",username.getText().toString());
        map.put("email",email.getText().toString());
        map.put("password",password.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("users").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(signup.this,"Data Inserted Successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signup.this,"Error Inserting Data", Toast.LENGTH_SHORT).show();

                    }
                });

    }*/
    private void  sendUserToNextActivity(){
        Intent intent=new Intent(signup.this, loginpage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
