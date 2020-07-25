package com.tech.greapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserProfile extends AppCompatActivity {

    TextView username, useremail;
    ImageView userimage, backbtn;
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    ProgressBar progressBar;
    String userId;
    Button editprofile;
    FirebaseUser user;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = fauth.getCurrentUser().getUid();
        logout = findViewById(R.id.logout_btn);


        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                useremail.setText(documentSnapshot.getString("email"));

                username.setText(documentSnapshot.getString("username"));

            }
        });


        useremail = findViewById(R.id.userprofile_email);

        username = findViewById(R.id.userprofile_username);

        userimage = findViewById(R.id.userprofile_image);
        backbtn = findViewById(R.id.back_btn);


        editprofile = findViewById(R.id.edit_profile);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = username.getText().toString();
                String entryfee = useremail.getText().toString();



                Intent i =new Intent(UserProfile.this, EditProfile.class);
                i.putExtra("name", title);
                i.putExtra("email", entryfee);

                startActivity(i);


            }


        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserProfile.this,MainActivity.class));
                finish();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent p = new Intent(getApplicationContext(),Loginactivity.class);
                startActivity(p);
                finish();
            }
        });

    }
}