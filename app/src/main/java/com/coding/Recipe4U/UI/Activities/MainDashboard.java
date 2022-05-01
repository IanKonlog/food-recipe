package com.coding.Recipe4U.UI.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Fragments.ByIngredientFragment;
import com.coding.Recipe4U.UI.Fragments.HomeFragment;
import com.coding.Recipe4U.UI.Fragments.ProfileFragment;
import com.coding.Recipe4U.UI.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainDashboard extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        //setupActionBarWithNavController(this, navController);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeFragment:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.searchFragment:
                    replaceFragment(new SearchFragment());
                    //navController.navigate(R.id.action_homeFragment_to_searchFragment, savedInstanceState);
                    break;
                case R.id.byIngredientFragment:
                    replaceFragment(new ByIngredientFragment());
                    break;
                case R.id.profileFragment:
                    ProfileFragment profileFragment = new ProfileFragment();
                    replaceFragment(profileFragment);
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                );
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}