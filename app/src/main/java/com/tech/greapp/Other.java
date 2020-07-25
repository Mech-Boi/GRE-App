package com.tech.greapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Other extends AppCompatActivity {

    ImageView back_btn;
    private List<NewWord> newWord;
    NewWordAdapter newWordAdapter;
    RecyclerView rwords;
    ProgressBar progree;
    String userId;
    FirebaseAuth fauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        back_btn = findViewById(R.id.back_btn);
        fauth = FirebaseAuth.getInstance();
        userId = fauth.getCurrentUser().getUid();

        rwords = findViewById(R.id.rcommon);
        rwords.setHasFixedSize(true);
        rwords.setLayoutManager(new LinearLayoutManager(this));
        newWord = new ArrayList<>();
        progree = findViewById(R.id.progressBartodo);
        progree.setVisibility(View.VISIBLE);


        final DatabaseReference mdataref = FirebaseDatabase.getInstance().getReference().child(userId).child("Other");
        mdataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot mdatasnap: dataSnapshot.getChildren()){
                        NewWord c = mdatasnap.getValue(NewWord.class);
                        newWord.add(c);


                    }
                    newWordAdapter = new NewWordAdapter(newWord);
                    rwords.setAdapter(newWordAdapter);
                    progree.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Other.this,MainActivity.class));
            }
        });

    }
}