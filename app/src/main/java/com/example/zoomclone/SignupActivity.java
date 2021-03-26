package com.example.zoomclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    EditText emailbox,passwordbox,namebox;
    Button loginbutton,signupbutton;
    FirebaseAuth firebaseauth;
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailbox =findViewById(R.id.emailBox);
        passwordbox=findViewById(R.id.PasswordBox);
        loginbutton =findViewById(R.id.Login);
        signupbutton = findViewById(R.id.Create);
        namebox = findViewById(R.id.editTextTextPersonName);
        firebaseauth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        final User user = new User();
        user.setEmail(emailbox.getText().toString());
        user.setUsername(namebox.getText().toString());
        user.setPassword(passwordbox.getText().toString());

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,usename;
                email = emailbox.getText().toString();
                password = passwordbox.getText().toString();
                usename = namebox.getText().toString();
                firebaseauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SignupActivity.this,"Account is created",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                }
                            });

                        }else{
                            Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
    }
}