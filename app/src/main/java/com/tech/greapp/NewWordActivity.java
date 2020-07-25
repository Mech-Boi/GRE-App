package com.tech.greapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewWordActivity extends AppCompatActivity {

    EditText word, meaning;
    Button add;
    ImageView back_btn;
    FirebaseDatabase database, mdata;
    DatabaseReference mref, allref;
    NewWord newword;
    int id=0;
    String userId;
    FirebaseAuth fauth;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        spinner = findViewById(R.id.spinner);
        fauth = FirebaseAuth.getInstance();
        userId = fauth.getCurrentUser().getUid();


        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(NewWordActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        myadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(myadapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("choose event")) {

                } else {
                    String item = adapterView.getItemAtPosition(i).toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mref = database.getInstance().getReference().child(userId);
        allref = mdata.getInstance().getReference().child("Word");

        back_btn = findViewById(R.id.back_btn);
        word = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        add = findViewById(R.id.addword_btn);

        newword = new NewWord();
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    id = (int) snapshot.getChildrenCount();
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        newword = new NewWord();

        allref.addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                             if (snapshot.exists()) {
                                                 id = (int) snapshot.getChildrenCount();
                                             } else {

                                             }
                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {

                                         }
                                     });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spin = spinner.getSelectedItem().toString();


                newword.setNewword(word.getText().toString());
                newword.setNewmeaning(meaning.getText().toString());
                newword.setSpinner(spin);

                mref.child(spin).child(String.valueOf(id+1)).setValue(newword);
                allref.child(String.valueOf(id+1)).setValue(newword);


                word.getText().clear();
                meaning.getText().clear();

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewWordActivity.this,MainActivity.class));
            }
        });
    }
}