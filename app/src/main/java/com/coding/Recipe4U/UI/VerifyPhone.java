package com.coding.Recipe4U.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.coding.Recipe4U.R;

public class VerifyPhone extends AppCompatActivity {

    private EditText user_OTP;
    private Button verifyBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        hooks();

        String phoneNo = getIntent().getStringExtra("phoneNo");

        sendVerificationCode(phoneNo);
    }

    private void sendVerificationCode(String phoneNo) {

    }

    private void hooks() {
        user_OTP = findViewById(R.id.user_OTP);
        verifyBtn = findViewById(R.id.verifyBtn);
        progressBar = findViewById(R.id.progress_bar);
    }
}