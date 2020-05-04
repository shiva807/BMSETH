package com.example.bmseth;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tomer.fadingtextview.FadingTextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    FadingTextView f;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f=(FadingTextView)findViewById(R.id.ftv1);
        f.setTimeout(750,FadingTextView.MILLISECONDS);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.main_anim);
        logo=(ImageView)findViewById(R.id.imageView);
        logo.startAnimation(anim);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, 3500);
    }
}
