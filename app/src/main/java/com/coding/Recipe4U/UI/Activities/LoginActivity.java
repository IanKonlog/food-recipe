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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private Button signUp, logIn, forgotPasswordBtn;
    private ImageView image;
    private TextView logoText, signInText;

    private TextInputLayout loginPhoneNo, loginPassword, loginEmail; // Login variables

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private ProgressDialog dialog;
    SharedPreferences sp;

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
        //loginPhoneNo = findViewById(R.id.phoneNo);
        loginEmail = findViewById(R.id.email_login);
        loginPassword = findViewById(R.id.password);

        dialog = new ProgressDialog(this);

        sp = getSharedPreferences("login",0);
        boolean isLoggedIn = sp.getBoolean("loggedIn",false);

        if (isLoggedIn){
            Intent intent = new Intent(getApplicationContext(), TestActivity.class);
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
                Intent intent = new Intent(LoginActivity.this, VerifyPhone.class);
                startActivity(intent);
            }
        });
    }

    //Validate Username
    private Boolean validateEmail() {
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
    private Boolean validatePassword() {
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


        //User user = new User("", userName, email, pass, phoneNo, "", "");
        //reference.child(phoneNo).setValue(user);
    }

    private void isUser() {
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
                        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Verify Email Address in your mailbox", Toast.LENGTH_LONG).show();
                        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //Toast.makeText(LoginActivity.this, "Verification email sent", Toast.LENGTH_LONG).show();
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
                    //Snackbar.make(findViewById(R.id.to_show_snack),"Details don't match our records",Snackbar.LENGTH_LONG).show();
                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();


                }
            }
        });







        /*rootNode = FirebaseDatabase.getInstance();
        reference  = rootNode.getReference().child("User");

        Query checkUser = reference.orderByChild("phoneNumber").equalTo(userPhone);
        //Log.d(TAG, "isUser: checking if it returns something"+reference.child("userName"));

        //System.out.println(reference.child("userName"));
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    loginPhoneNo.setError(null);
                    loginPhoneNo.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userPhone).child("password").getValue(String.class);

                    if(passwordFromDB.equals(pass)){

                        loginPassword.setError(null);
                        loginPassword.setErrorEnabled(false);

                        String nameFromDb = snapshot.child(userPhone).child("name").getValue(String.class);
                        String userNameFromDb = snapshot.child(userPhone).child("userName").getValue(String.class);
                        String emailFromDb = snapshot.child(userPhone).child("email").getValue(String.class);
                        //String phoneNoFromDb = snapshot.child(userPhone).child("phoneNumber").getValue(String.class);
                        //String birthdateFromDb = snapshot.child(userName).child("birthDate").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), TestActivity.class);

                        intent.putExtra("name", nameFromDb);
                        intent.putExtra("email", emailFromDb);
                        intent.putExtra("phoneNo", userPhone);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("userName", userNameFromDb);

                        // set MyFragment Arguments
                        ProfileFragment myObj = new ProfileFragment();

                        startActivity(intent);
                        finish();
                    }
                    else{
                       loginPassword.setError("Wrong Password");
                       loginPassword.requestFocus();
                    }
                }

                else{
                    loginPhoneNo.setError("No such User exists");
                    loginPhoneNo.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
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

    //TODO: Login with email and password
    //TODO: implement user authentication
}