package com.example.tapoff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity2 extends AppCompatActivity {

    TextView countxt,gametxt,scoretxt;
    public int counter, highscore;
    ConstraintLayout clayout;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button backbtn1 = findViewById(R.id.backbtn1);
        Button retrybtn = findViewById(R.id.retrybtn);
        start_timer();

        backbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainpage1();

            }
        });

        retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView scoretxt = findViewById(R.id.score_txt);
                scoretxt.setText("");
                start_timer();

            }
        });



    }

    public void start_timer() {
        Button backbtn1 = findViewById(R.id.backbtn1);
        Button retrybtn = findViewById(R.id.retrybtn);
        TextView countxt = findViewById(R.id.countdown_text);
        backbtn1.setVisibility(View.GONE);
        retrybtn.setVisibility(View.GONE);
        new CountDownTimer(4000, 1000){
            public void onTick(long millisUntilFinished){
                countxt.setText(String.valueOf(millisUntilFinished / 1000));
            }
            public  void onFinish(){
                countxt.setText(R.string.go);
                new CountDownTimer(500, 500){
                    public void onTick(long millisUntilFinished){
                    }
                    public  void onFinish(){

                        countxt.setText("");
                        gametimer();
                    }
                }.start();
            }
        }.start();
    }

    public void gametimer(){
        TextView gametxt = findViewById(R.id.game_timer);
        TextView scoretxt = findViewById(R.id.score_txt);
        ConstraintLayout clayout = findViewById(R.id.activity_main2);
        Button backbtn1 = findViewById(R.id.backbtn1);
        Button retrybtn = findViewById(R.id.retrybtn);
        counter = 0;

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                gametxt.setText(getString(R.string.seconds_remaining) + millisUntilFinished / 1000);
                clayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        counter++;
                        scoretxt.setText(String.valueOf(counter));
                    }
                });
            }

            public void onFinish() {
                clayout.setOnClickListener(null);
                gametxt.setText("");
                backbtn1.setVisibility(View.VISIBLE);
                retrybtn.setVisibility(View.VISIBLE);
                scores();
            }
        }.start();
    }

    public  void scores(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        int savedscorecheck = sp.getInt("highscore",0);
        if (counter > savedscorecheck) {
            Toast.makeText(MainActivity2.this, "New Highscore!",Toast.LENGTH_SHORT).show();
            highscore = counter;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("highscore",highscore);
            editor.commit();
        }
        else{
            Toast.makeText(MainActivity2.this, "Try again for a new highscore!",Toast.LENGTH_SHORT).show();

        }

    }

    public void openMainpage1() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}