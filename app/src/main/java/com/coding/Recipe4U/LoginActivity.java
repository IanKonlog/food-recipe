package com.coding.Recipe4U;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button signUp, logIn;
    private ImageView image;
    private TextView logoText, signInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.signUpBtn);
        logIn = findViewById(R.id.loginBtn);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        signInText = findViewById(R.id.signPhrase);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);

                Pair [] pairs = new Pair[5];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logIn,"btn_sign_register");
                pairs[2] = new Pair<View,String>(signUp,"btn_already");
                pairs[3] = new Pair<View,String>(signInText,"sign_register");
                pairs[4] = new Pair<View,String>(logoText,"logo_text");

                //Activity options for animations from main activity to next activity
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);

                //toBundle() to carry out the transition effect
                startActivity(intent,options.toBundle());
                finish(); // remove activity from activity list
            }
        });
    }
}