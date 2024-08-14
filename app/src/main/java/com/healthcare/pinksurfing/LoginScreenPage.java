package com.healthcare.pinksurfing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.healthcare.pinksurfing.Primary;
import com.healthcare.pinksurfing.R;
import com.healthcare.pinksurfing.SignupPage;

public class LoginScreenPage extends AppCompatActivity {

    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;
    TextView donot;

    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen_page);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        donot = findViewById(R.id.donot);

        Auth = FirebaseAuth.getInstance();


        donot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreenPage.this, SignupPage.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = etLoginEmail.getText().toString();
                password = etLoginPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    etLoginEmail.setError("Email cannot be empty");
                    etLoginEmail.requestFocus();
                }else if (TextUtils.isEmpty(password)){
                    etLoginPassword.setError("Password cannot be empty");
                    etLoginPassword.requestFocus();
                }else{
                    Auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LoginScreenPage.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginScreenPage.this, Primary.class));
                            }else{
                                Toast.makeText(LoginScreenPage.this, "Login failed: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });




        }

    }

