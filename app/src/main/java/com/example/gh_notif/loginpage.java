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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginpage extends AppCompatActivity {

   TextInputEditText email, password;
    Button login, signup;
    String emailcheck= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth authenticate;
    FirebaseUser newuser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginpage);

        email=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.loginbutton);
        signup=findViewById(R.id.signupbutton);
        authenticate=FirebaseAuth.getInstance();
        newuser=authenticate.getCurrentUser();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginpage.this, signup.class ));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performlogin();
            }
        });
    }

    private void performlogin()
    {
        String mail=email.getText().toString();
        String passcode=password.getText().toString();

        if(!mail.matches(emailcheck))
        {
            email.setError("enter valid email");
        }
        else if (passcode.isEmpty() || passcode.length()<6)
        {
            password.setError("enter valid password");
        }
        else
        {
            authenticate.signInWithEmailAndPassword(mail,passcode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful())
                 {
                     sendUserToNextActivity();
                     Toast.makeText(loginpage.this,"Login Successfully", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     Toast.makeText(loginpage.this,"Login Failed ", Toast.LENGTH_SHORT).show();
                 }
                }
            });
        }
    }

    private void sendUserToNextActivity()
    {
        Intent intent=new Intent(loginpage.this, dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}