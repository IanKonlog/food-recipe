package com.coding.Recipe4U.UI.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.coding.Recipe4U.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyPhone extends AppCompatActivity {

    private TextInputLayout user_OTP;
    private Button verifyBtn;
    private ProgressBar progressBar;
    private ProgressDialog dialog;
    String emailAddress;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_phone);

        hooks();
        dialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailAddress = user_OTP.getEditText().getText().toString().trim();
                Log.d(TAG, "onCreate: "+emailAddress);
                //dialog.show();
                System.out.println("Inside");
                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: Completed");
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(VerifyPhone.this, "Reset Email Sent", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onSuccess: Email sent");
                        Intent intent = new Intent(VerifyPhone.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+ e.getMessage());
                    }
                });
            }
        });
    }

    private void hooks() {
        user_OTP = findViewById(R.id.user_OTP);
        verifyBtn = findViewById(R.id.verifyBtn);
        //progressBar = findViewById(R.id.progress_bar);
    }
}