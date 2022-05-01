package com.coding.Recipe4U.UI.Activities;

import static org.junit.Assert.*;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
//import androidx.test.runner.AndroidJUnit4;
import android.widget.Button;

import com.coding.Recipe4U.R;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    InstrumentationRegistry instrumentationRegistry;
    public LoginActivity loginActivity;
    public Context context;

    @Before
    public void setUp() throws Exception {
        loginActivity = mActivityTestRule.getActivity();
        instrumentationRegistry.getInstrumentation().getContext();
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }

    @Test
    public void validateEmail() {
        instrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                TextInputLayout email = loginActivity.findViewById(R.id.email_login);
                email.getEditText().setText("email");

                Button loginBtn = loginActivity.findViewById(R.id.loginBtn);
                loginBtn.performClick();

                assertEquals(loginActivity.validateEmail(), true);
            }
        });
    }

    @Test
    public void validatePassword() {
    }

    @Test
    public void loginUser() {
    }

    @Test
    public void isUser() {
    }

    @Test
    public void callSignUpScreen() {
    }
}