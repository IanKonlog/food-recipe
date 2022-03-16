package com.coding.Recipe4U.UI;

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

import com.coding.Recipe4U.Classes.Recipe;
import com.coding.Recipe4U.Classes.User;
import com.coding.Recipe4U.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private Button register, alreadyReg;
    private TextView welcomeMess, signUpMess;
    private ImageView logo;

    private TextInputLayout regUserName, regEmail, regPhoneNo, regPassword;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set whole screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        register = findViewById(R.id.registerBtn); // Register Button
        alreadyReg = findViewById(R.id.alreadySignBtn); //Already Signed in Button
        signUpMess = findViewById(R.id.signUpPhrase);
        welcomeMess = findViewById(R.id.logo_name);
        logo = findViewById(R.id.logo_image);

        /*Text Input Hooks*/
        regUserName = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phoneNumber);
        regPassword = findViewById(R.id.password);


        //Return to Sign In page
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




        //Register button event
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(view);
            }
        });

    }

    //Validate Username
    private Boolean validateUserName(){
        String val = regUserName.getEditText().getText().toString().trim();
        //String noWhiteSpace = "(?=\\s+$)";
        String noWhiteSpace = "\\A\\w{4,15}\\z";


        if (val.isEmpty()){
            regUserName.setError("Username cannot be empty");
            return false;
        }else if(val.length() < 4){
            regUserName.setError("Username cannot be less than 4");
            return false;
        }else if(val.length() >= 15){
            regUserName.setError("Username cannot be greater than 15");
            return false;
        }else if(!val.matches(noWhiteSpace)){
            regUserName.setError("Username cannot contain whitespace");
            return false;
        }
        else{
            regUserName.setError(null);
            regUserName.setErrorEnabled(false);
            return true;
        }
    }

    //Validate email
    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString().trim();
        String emailPattern = "[a-z-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            regEmail.setError("Email cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            regEmail.setError("Email is not valid");
            return false;
        }
        else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    //Validate phone Number
    private Boolean validatePhoneNo(){
        String val = regPhoneNo.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            regPhoneNo.setError("Phone Number cannot be empty");
            return false;
        }
        else{
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    //Validate password
    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString().trim();
        String passwordVeri = "^"+
                //"(?=.*[0-9]]"+      //at least 1 digit
                //"(?=.*[a-z])"+      //at least 1 lower case letter
                //"(?=.*[A-Z])"+      //at least 1 upper case letter
                "(?=.*[a-zA-Z])"+  // Any letter
                "(?=.*[@#$%^&+=])"+  // at least one special character
                //"(?=\\s+$)"+   //no white spaces
                ".{6,}"+  //at least 6 characters
                "$";


        if (val.isEmpty()){
            regPassword.setError("Password cannot be empty");
            return false;
        }else if(!val.matches(passwordVeri)){
            regPassword.setError("Invalid Password. Password is too weak");
            return false;
        }
        else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    //Register User
    public void registerUser(View view){
        rootNode = FirebaseDatabase.getInstance();
        reference  = rootNode.getReference().child("User");

        if(!validateEmail() || !validateUserName() || !validatePassword() || !validatePhoneNo()) {
            return;
        }
            //Get Values from Text fields
            String userName = regUserName.getEditText().getText().toString().trim();
            String email = regEmail.getEditText().getText().toString().trim();
            String phoneNo = regPhoneNo.getEditText().getText().toString().trim();
            String pass = regPassword.getEditText().getText().toString().trim();

            ArrayList <Recipe> R = new ArrayList<>();

            Intent intent = new Intent(getApplicationContext(),VerifyPhone.class);
            intent.putExtra("phoneNo", phoneNo);
            startActivity(intent);
            //User user = new User("", userName, email, pass, phoneNo, "", R);
            //reference.child(phoneNo).setValue(user);
    }
}