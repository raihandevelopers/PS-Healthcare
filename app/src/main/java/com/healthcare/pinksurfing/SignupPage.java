package com.healthcare.pinksurfing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.healthcare.pinksurfing.LoginScreenPage;

import java.util.HashMap;
import java.util.Map;

public class SignupPage extends AppCompatActivity {

    public static final String TAG = "TAG";
    FirebaseFirestore fstore;
    FirebaseAuth auth;
    EditText etRegEmail, etRegPassword;
    Button btnRegister;
    TextView have;
    String userID;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");

        have = findViewById(R.id.have);
        etRegEmail =findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        have.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupPage.this, LoginScreenPage.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = etRegEmail.getText().toString();
                password = etRegPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    etRegEmail.setError("Email cannot be empty");
                    etRegEmail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    etRegPassword.setError("Password cannot be empty");
                    etRegPassword.requestFocus();
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupPage.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                userID = auth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Email", email);
                                user.put("Password", password);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: User profile is created for " + userID);
                                    }
                                });
                                startActivity(new Intent(SignupPage.this, Primary.class));
                            } else {
                                Toast.makeText(SignupPage.this, "Registration Failed: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}



