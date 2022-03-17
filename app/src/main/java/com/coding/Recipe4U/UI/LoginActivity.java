package com.coding.Recipe4U.UI;

import androidx.annotation.NonNull;
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

import com.coding.Recipe4U.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button signUp, logIn;
    private ImageView image;
    private TextView logoText, signInText;

    private TextInputLayout loginPhoneNo, loginPassword; // Login variables

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

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

        //Variables to login
        loginPhoneNo = findViewById(R.id.phoneNo);
        loginPassword = findViewById(R.id.password);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callSignUpScreen(view);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
            }
        });
    }

    //Validate Username
    private Boolean validateUserName(){
        String val = loginPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()){
            loginPhoneNo.setError("Username cannot be empty");
            return false;
        }
        else{
            loginPhoneNo.setError(null);
            loginPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    //Validate password
    private Boolean validatePassword(){
        String val = loginPassword.getEditText().getText().toString();

        if (val.isEmpty()){
            loginPassword.setError("Password cannot be empty");
            return false;
        }
        else{
            loginPassword.setError(null);
            loginPassword.setErrorEnabled(false);
            return true;
        }
    }

    //Login User credentials
    public void loginUser(View view){

        if(!validateUserName() || !validatePassword()) {
            return;
        }
        else{
            isUser();
        }


        //User user = new User("", userName, email, pass, phoneNo, "", "");
        //reference.child(phoneNo).setValue(user);
    }

    private void isUser() {
        //Get Values from Text fields
        String userPhone = loginPhoneNo.getEditText().getText().toString().trim();
        String pass = loginPassword.getEditText().getText().toString().trim();

        rootNode = FirebaseDatabase.getInstance();
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

                        Intent intent = new Intent(getApplicationContext(), Dasboard.class);

                        intent.putExtra("name", nameFromDb);
                        intent.putExtra("email", emailFromDb);
                        intent.putExtra("phoneNo", userPhone);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("userName", userNameFromDb);

                        startActivity(intent);
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

    //TODO: Login with email and password
    //TODO: implement user authentication
}