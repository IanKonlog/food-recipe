package com.coding.Recipe4U.UI.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coding.Recipe4U.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UserProfileActivity extends AppCompatActivity {

    private TextInputLayout fullName, email, phoneNo, password, userName;
    private TextView fullNameLabel, userNameLabel;
    private Button goToDashboard;

    private String user_userName, user_password, user_email, user_name, user_phoneNo;

    private DatabaseReference reference;

    private ImageView profile_picture;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        //Hooks
        hooks();

        goToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Dasboard.class);
                startActivity(intent);
            }
        });

        //Show All data from Database
        showAllUserData();

        //Dtabase reference
        reference = FirebaseDatabase.getInstance().getReference("User");


        //Change profile picture
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        
        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfile(view);
            }
        });
    }

    private void hooks() {
        profile_picture = findViewById(R.id.profile_image);
        fullName = findViewById(R.id.fullName);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phoneNo);
        password = findViewById(R.id.passw);
        fullNameLabel = findViewById(R.id.full_name);
        userNameLabel = findViewById(R.id.user_name_label);
        goToDashboard = findViewById(R.id.go_to_dashboard);
    }

    /*Change profile picture*/
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
                Toast.makeText(UserProfileActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress: "+ (int)progressPercent + "%");
            }
        });

    }


    /*Show User Data*/
    private void showAllUserData() {
        Intent intent = getIntent();
        user_userName = intent.getStringExtra("userName");
        user_password = intent.getStringExtra("password");
        user_email = intent.getStringExtra("email");
        user_name = intent.getStringExtra("name");
        user_phoneNo = intent.getStringExtra("phoneNo");

        System.out.println(user_name + "" + user_password+ ""+ user_userName);

        fullNameLabel.setText(user_name);
        userNameLabel.setText(user_userName);
        fullName.getEditText().setText(user_name);
        userName.getEditText().setText(user_userName);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);

    }


    /*Update user data*/
    public void update(View view){
        if (isNameChanged() || isPasswordChanged()){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Same data cannot be updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPasswordChanged() {
        if(!user_password.equals(password.getEditText().getText().toString().trim())){
            reference.child(user_phoneNo).child("password").setValue(password.getEditText().getText().toString());;
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!user_name.equals(fullName.getEditText().getText().toString().trim())){
            reference.child(user_phoneNo).child("name").setValue(fullName.getEditText().getText().toString());
            fullNameLabel.setText(fullName.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    //TODO: link profile picture URL to user data
}