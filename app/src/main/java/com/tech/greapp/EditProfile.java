package com.tech.greapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    TextView username, useremail, usermobile,usergpay, upiid;
    ImageView userimage, backbtn;
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    String userId;
    Button saveprofile;
    FirebaseUser user;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        useremail = findViewById(R.id.userprofile_email);

        username = findViewById(R.id.userprofile_username);

        userimage = findViewById(R.id.userprofile_image);
        backbtn = findViewById(R.id.back_btn);
        saveprofile = findViewById(R.id.save_profile);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user = fauth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        Intent get =getIntent();
        Bundle bundle =get.getExtras();


        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() ||
                        usergpay.getText().toString().isEmpty() ||
                        useremail.getText().toString().isEmpty() ||
                        usermobile.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this, "One or Many fields are Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String email = useremail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference = fstore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("username", username.getText().toString());

                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),UserProfile.class));
                                finish();
                            }
                        });

                        Toast.makeText(EditProfile.this, "Email Changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(EditProfile.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        if (bundle!=null) {
            String t = (String) bundle.get("name");
            String e = (String) bundle.get("email");


            username.setText(t);
            useremail.setText(e);


            Log.d(TAG,"onCreate: "+ "" +t + "" +e+"");
        }

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this,UserProfile.class));
                finish();

            }
        });
    }
    private static final String TAG = "MyActivity";
}