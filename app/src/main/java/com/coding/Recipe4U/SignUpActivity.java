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

public class SignUpActivity extends AppCompatActivity {

    private Button register, alreadyReg;
    private TextView welcomeMess, signUpMess;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set whole screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        register = findViewById(R.id.registerBtn);
        alreadyReg = findViewById(R.id.alreadySignBtn);
        signUpMess = findViewById(R.id.signUpPhrase);
        welcomeMess = findViewById(R.id.logo_name);
        logo = findViewById(R.id.logo_image);

        alreadyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);

                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View,String>(logo,"logo_image");
                pairs[1] = new Pair<View,String>(register,"btn_sign_register");
                pairs[2] = new Pair<View,String>(alreadyReg,"btn_already");
                pairs[3] = new Pair<View,String>(signUpMess,"sign_register");
                pairs[4] = new Pair<View,String>(welcomeMess,"logo_text");

                //Activity options for animations from main activity to next activity
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this,pairs);

                //toBundle() to carry out the transition effect
                startActivity(intent,options.toBundle());
                finish(); // remove activity from activity list
            }
        });
    }
}