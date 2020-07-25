package com.tech.greapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {


    EditText mUserName, mEmail, mPassword;
    Button signup;
    TextView login;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mUserName = (EditText) findViewById(R.id.user_name);
        mEmail = findViewById(R.id.user_email);
        mPassword = findViewById(R.id.user_password);
        signup = findViewById(R.id.signup_btn);
        login = findViewById(R.id.login_text);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fstore =FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String username = mUserName.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(username)){
                    mUserName.setError("Username Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password Required");
                    return;
                }
                if (password.length()<6){
                    mPassword.setError("Password must be greater than 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    FirebaseUser fuser = fAuth.getCurrentUser();
                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Signup.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure:Email not sent"+e.getMessage());                                        }
                                    });

                                    Toast.makeText(Signup.this, "New user created", Toast.LENGTH_SHORT).show();
                                    userid =fAuth.getCurrentUser().getUid();

                                    DocumentReference documentReference =fstore.collection("users").document(userid);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("username",username);
                                    user.put("email",email);

                                    user.put("password",password);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"onSuccess: user profile is created for"+userid);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"onFailure:"+e.toString());
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                }else{
                                    Toast.makeText(Signup.this, "Error !" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), Loginactivity.class);
                startActivity(s);
            }
        });


    }
    private static final String TAG = "MyActivity";
}
