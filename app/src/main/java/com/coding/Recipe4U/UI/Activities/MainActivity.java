package com.coding.Recipe4U.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.coding.Recipe4U.R;

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

                //Define pairs for transition of image and text
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(logo,"logo_image");
                pairs[1] = new Pair<View,String>(slogan,"logo_text");

                //Activity options for animations from main activity to next activity
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);

                //toBundle() to carry out the transition effect
                startActivity(intent,options.toBundle());
                finish(); // remove activity from activity list
            }
        },SPLASH_SCREEN);

    }
}