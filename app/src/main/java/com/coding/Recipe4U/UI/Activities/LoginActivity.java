package com.coding.Recipe4U.UI.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coding.Recipe4U.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button signUp, logIn, forgotPasswordBtn;
    private ImageView image;
    private TextView logoText, signInText;
    private TextInputLayout loginPassword, loginEmail; // Login variables
    private ProgressDialog dialog;
    SharedPreferences sp;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //Hooks for animation to Sign up screen
        signUp = findViewById(R.id.signUpBtn);
        logIn = findViewById(R.id.loginBtn);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        signInText = findViewById(R.id.signPhrase);
        forgotPasswordBtn = findViewById(R.id.forget_password_btn);

        //Variables to login
        loginEmail = findViewById(R.id.email_login);
        loginPassword = findViewById(R.id.password);

        dialog = new ProgressDialog(this);

        sp = getSharedPreferences("login", 0);
        isLoggedIn = sp.getBoolean("loggedIn", false);

        if (isLoggedIn){
            Intent intent = new Intent(getApplicationContext(), MainDashboard.class);
            startActivity(intent);
            finish();
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callSignUpScreen(view);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putBoolean("loggedIn",true).commit();
                dialog.show();
                loginUser(view);
            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, VerifyEmail.class);
                startActivity(intent);
            }
        });
    }

    //Validate Username
    public Boolean validateEmail() {
        String val = loginEmail.getEditText().getText().toString();
        String emailPattern = "[a-z-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            loginEmail.setError("Username cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            loginEmail.setError("Email is not valid");
            return false;
        } else {
            loginEmail.setError(null);
            loginEmail.setErrorEnabled(false);
            return true;
        }
    }

    //Validate password
    public Boolean validatePassword() {
        String val = loginPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            loginPassword.setErrorEnabled(false);
            return true;
        }
    }

    //Login User credentials
    public void loginUser(View view) {

        if (!validateEmail() || !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    public void isUser() {
        //Get Values from Text fields
        String userEmail = loginEmail.getEditText().getText().toString().trim();
        String pass = loginPassword.getEditText().getText().toString().trim();


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        firebaseAuth.signInWithEmailAndPassword(userEmail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser.isEmailVerified()) {
                        Intent intent = new Intent(getApplicationContext(), MainDashboard.class);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Verify Email Address in your mailbox", Toast.LENGTH_LONG).show();
                        //Snackbar.make(getCurrentFocus(),"Verify Email Address in your mailbox", Snackbar.LENGTH_SHORT);
                        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: Email not sent" + e.getMessage());
                            }
                        });

                    }

                } else {
                    Log.d(TAG, "onComplete: Failure to sign in" + task.getException());
                    Toast.makeText(LoginActivity.this, "Signin details do not match our records", Toast.LENGTH_LONG).show();
                    //Snackbar.make(getCurrentFocus(),"Signin details do not match our records", Snackbar.LENGTH_SHORT);
                    dialog.dismiss();
                    sp.edit().putBoolean("loggedIn", true).commit();
                }
            }
        });
    }

    //Call transition to Sign up screen
    public void callSignUpScreen(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(image, "logo_image");
        pairs[1] = new Pair<View, String>(logIn, "btn_sign_register");
        pairs[2] = new Pair<View, String>(signUp, "btn_already");
        pairs[3] = new Pair<View, String>(signInText, "sign_register");
        pairs[4] = new Pair<View, String>(logoText, "logo_text");

        //Activity options for animations from main activity to next activity
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);

        //toBundle() to carry out the transition effect
        startActivity(intent, options.toBundle());
        finish(); // remove activity from activity list
    }

}