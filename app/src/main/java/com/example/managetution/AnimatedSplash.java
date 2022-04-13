package com.example.managetution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;

public class AnimatedSplash extends AppCompatActivity {

    public static int SPLASH_SCREEN = 5000;
    Animation bottomAnim, topAnim;
    TextView app_name, slogan;
    View lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_splash);

        getSupportActionBar().hide();

        app_name = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);
        bottomAnim = AnimationUtils.loadAnimation( this,R.anim.bottom_animation);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        lottie = findViewById(R.id.animation_study);

        lottie.setAnimation(topAnim);
        app_name.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        Thread td = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                finally {
                    startActivity(new Intent(AnimatedSplash.this, LoginActivity.class));
                    finish();
                }
            }
        };td.start();
    }
}