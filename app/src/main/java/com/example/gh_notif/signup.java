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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {

    EditText fullname,username,email,password;
    Button Signup;

    TextView alreadyacc;
    String emailcheck= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth authenticate;
    FirebaseUser newuser;
    @SuppressLint("MissingInflatedId")
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
        authenticate=FirebaseAuth.getInstance();
        newuser=authenticate.getCurrentUser();

        alreadyacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, loginpage.class));
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performauth();

            }
        });
    }
    private void performauth()
    {
        String fname=fullname.getText().toString();
        String usrname=username.getText().toString();
        String mail=email.getText().toString();
        String pssword=password.getText().toString();

        if (fname.isEmpty())
        {
            fullname.setError("enter your name");
        } else if (!usrname.isEmpty()) {
            username.setError("enter valid username");
        } else if (!mail.matches(emailcheck)) {
            email.setError("enter valid email");
        } else if (pssword.isEmpty() || pssword.length()<6) {
            password.setError("enter minimum 6 aplhabets or numbers");
        }
        else {
            authenticate.createUserWithEmailAndPassword(mail,pssword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful())
                   {
                       sendUserToNextActivity();
                       Toast.makeText(signup.this,"Registered Successfully", Toast.LENGTH_SHORT).show();

                   }
                   else {
                       Toast.makeText(signup.this,"Registration Failed ", Toast.LENGTH_SHORT).show();
                   }
                }
            });
        }
    }
    private void  sendUserToNextActivity(){
        Intent intent=new Intent(signup.this, loginpage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
