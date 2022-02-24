package com.coding.Recipe4U;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;


    //Animation variables
    private Animation topAnimation, bottomAnimation;

    private ImageView logo;
    private TextView slogan, appeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make the application fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        //Call Animations
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        //Add Hooks
        logo = findViewById(R.id.imageView);
        slogan = findViewById(R.id.textView);
        appeal = findViewById(R.id.textView2);

        //Assign animation to image and text
        logo.setAnimation(topAnimation);
        slogan.setAnimation(bottomAnimation);
        appeal.setAnimation(bottomAnimation);


        //Call the intent after 5s to move from splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // remove activity from activity list
            }
        },SPLASH_SCREEN);


    }
}