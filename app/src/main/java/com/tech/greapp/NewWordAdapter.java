package com.tech.greapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NewWordAdapter extends RecyclerView.Adapter<NewWordAdapter.ViewHolder> {
    private static List<NewWord> newWord;

    int id =0;
    public NewWordAdapter(List<NewWord> newWord) {
        this.newWord = newWord;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wordcardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final NewWord comp = newWord.get(position);
        holder.nword.setText(comp.getNewword());
        holder.nmeaning.setText(comp.getNewmeaning());

        holder.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = holder.nword.getText().toString();

                holder.mTTS.speak(toSpeak, TextToSpeech.QUEUE_ADD, null);
            }
        });
        holder.b_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();

            }

            private void change() {
                holder.b_btn.setImageResource(R.drawable.boo);

                holder.b_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chane1();
                    }

                });

            }

            private void chane1() {
                holder.b_btn.setImageResource(R.drawable.bookmark_border);
                holder.b_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change();
                    }

                });
            }
        });


        holder.meaningcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showword();
            }

            private void showword() {
                holder.word.setVisibility(View.VISIBLE);
                holder.nword.setVisibility(View.VISIBLE);
                holder.tw.setVisibility(View.VISIBLE);

                holder.meaning.setVisibility(View.GONE);
                holder.nmeaning.setVisibility(View.GONE);
                holder.meaningcardview.setVisibility(View.INVISIBLE);
                holder.tm.setVisibility(View.GONE);

            }
        });

        holder.wordcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmeaning();
            }


            private void showmeaning() {
                holder.word.setVisibility(View.GONE);
                holder.nword.setVisibility(View.GONE);
                holder.tw.setVisibility(View.GONE);

                holder.meaning.setVisibility(View.VISIBLE);
                holder.nmeaning.setVisibility(View.VISIBLE);
                holder.meaningcardview.setVisibility(View.VISIBLE);
                holder.tm.setVisibility(View.VISIBLE);


            }

        });

    }


    @Override
    public int getItemCount() {
        return newWord.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nword, nmeaning, word, meaning, tw, tm;
        TextToSpeech mTTS;
        CardView wordcardview, meaningcardview;
        ImageButton show_word;
        FloatingActionButton  speak;
        FloatingActionButton b_btn;


        public ViewHolder(View mview) {
            super(mview);

            tw = (TextView) mview.findViewById(R.id.tapword);
            tm = (TextView) mview.findViewById(R.id.tapmeaning);

            word = (TextView) mview.findViewById(R.id.word);
            meaning = (TextView) mview.findViewById(R.id.meaning);
            nword = (TextView) mview.findViewById(R.id.newword);
            nmeaning = (TextView) mview.findViewById(R.id.newmeaning);
            wordcardview = (CardView) mview.findViewById(R.id.word_cardview);
            meaningcardview = (CardView) mview.findViewById(R.id.meaning_cardview);

            b_btn = mview.findViewById(R.id.bookmark_btn);
            speak = mview.findViewById(R.id.speech);


            mTTS=new TextToSpeech(mview.getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != TextToSpeech.ERROR) {
                        mTTS.setLanguage(Locale.ENGLISH);
                    }
                }
            });

        }
    }
}

