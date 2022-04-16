package com.coding.Recipe4U.UI.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.coding.Recipe4U.UI.Fragments.ByIngredientFragment;
import com.coding.Recipe4U.UI.Fragments.HomeFragment;
import com.coding.Recipe4U.UI.Fragments.ProfileFragment;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Fragments.SearchFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class TestActivity extends AppCompatActivity{
    private String user_userName, user_password, user_email, user_name, user_phoneNo;
    private ImageView profile_picture;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        user_userName = intent.getStringExtra("userName");
        user_password = intent.getStringExtra("password");
        user_email = intent.getStringExtra("email");
        user_name = intent.getStringExtra("name");
        user_phoneNo = intent.getStringExtra("phoneNo");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.homeFragment:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.searchFragment:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.byIngredientFragment:
                    replaceFragment(new ByIngredientFragment());
                    break;
                case R.id.profileFragment:
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", user_name );
                    bundle.putString("email", user_email);
                    bundle.putString("phoneNo", user_phoneNo );
                    bundle.putString("password", user_password);
                    bundle.putString("userName", user_userName);

                    profileFragment.setArguments(bundle);
                    replaceFragment(profileFragment);
                    break;
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment);
        fragmentTransaction.commit();
    }

    private void changeProfile(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode ==RESULT_OK && data != null){
            imageUri = data.getData();
            profile_picture.setImageURI(imageUri);

            uploadPicture();
        }
    }

    private void uploadPicture() {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image....");

        String uid = UUID.randomUUID().toString();
        // Create a reference to "mountains.jpg"
        StorageReference profileRef = storageReference.child("profileImage/").child(uid+".jpg");

        profileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                reference.child(user_phoneNo).child("profilePicture").setValue(uid+".jpg");
                Snackbar.make(findViewById(android.R.id.content), "Image successfully uploaded.", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress: "+ (int)progressPercent + "%");
            }
        });

    }
}