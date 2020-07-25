package com.tech.greapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String userId;
    FirebaseAuth fauth;


    private ImageView todo, userprofile, share_btn;

    TextView latest, bookmarks;
    CardView common, basic, advanced, other;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fauth = FirebaseAuth.getInstance();
        userId = fauth.getCurrentUser().getUid();



        share_btn = findViewById(R.id.share_btn);
       userprofile = findViewById(R.id.user_profile1);

        common = findViewById(R.id.common_card);
        basic   = findViewById(R.id.basic_card);
        advanced = findViewById(R.id.adv_card);
        other = findViewById(R.id.other_cards);


        latest = findViewById(R.id.latest);
        bookmarks = findViewById(R.id.bclass);

        latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latest.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                Intent l =new Intent(MainActivity.this,MainActivity.class);
                startActivity(l);
            }
        });
        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarks.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Intent b=new Intent(MainActivity.this,Bookmarks.class);
                startActivity(b);
            }
        });






        final String packagename = getPackageName();
        todo = findViewById(R.id.todo_btn);

        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todo = new Intent(MainActivity.this, NewWordActivity.class);
                startActivity(todo);
            }
        });
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) { startActivity(new Intent(MainActivity.this, UserProfile.class));       }
      });



        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                       "Download at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
              startActivity(sendIntent);

            }
        });

        common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todo = new Intent(MainActivity.this, Common.class);
                startActivity(todo);
            }
        });
        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todo = new Intent(MainActivity.this, Basic.class);
                startActivity(todo);
            }
        });
        advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todo = new Intent(MainActivity.this, Advanced.class);
                startActivity(todo);
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todo = new Intent(MainActivity.this, Other.class);
                startActivity(todo);
            }
        });

    }



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tap again to exist.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);


    }

}