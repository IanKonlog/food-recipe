package com.coding.Recipe4U.UI.Fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.UserClasses.User;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private TextInputLayout fullName, email, phoneNo, password, userName;
    private TextView fullNameLabel, userNameLabel, createdLabel, favoriteLabel;
    private Button goToDashboard, updateProfileBtn, signoutBtn;

    private String user_userName, user_password, user_email, user_name, user_phoneNo;

    private DatabaseReference reference;

    private ImageView profile_picture;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String id, oldpass;
    private boolean uploadedPicture = false;
    private boolean flag = false;

    SharedPreferences sp;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_picture = view.findViewById(R.id.profile_image);
        fullName = view.findViewById(R.id.fullName);
        userName = view.findViewById(R.id.userName);
        email = view.findViewById(R.id.email);
        phoneNo = view.findViewById(R.id.phoneNo);
        password = view.findViewById(R.id.passw);
        fullNameLabel = view.findViewById(R.id.full_name);
        userNameLabel = view.findViewById(R.id.user_name_label);
        goToDashboard = view.findViewById(R.id.go_to_dashboard);
        updateProfileBtn = view.findViewById(R.id.update_profile_btn);
        createdLabel = view.findViewById(R.id.created_label);
        favoriteLabel = view.findViewById(R.id.favorite_label);
        signoutBtn = view.findViewById(R.id.signout_profile_btn);

        MaterialCardView cardView = view.findViewById(R.id.card_favorite_recipes);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NavHostFragment navHostFragment = (NavHostFragment) getFragmentManager()
                  //      .findFragmentById(R.id.profileFragment);
                //NavController navCo = navHostFragment.getNavController();
                //Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_favRecipesShowed);
                //navCo.navigate(R.id.action_profileFragment_to_favRecipesShowed);
                replaceFragment(new FavRecipesShowed());


            }
        });

        MaterialCardView cardView1 = view.findViewById(R.id.card_created_recipes);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new CreatedRecipeShowed());
            }
        });

        //Dtabase reference
        reference = FirebaseDatabase.getInstance().getReference("User");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        id = firebaseUser.getUid().toString();



        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null) {

                    //reference.child(id).child("password").setValue(pass);

                    user_password = user.getPassword();
                    oldpass = user_password;
                    user_userName = user.getUserName();
                    user_email = user.getEmail();
                    user_name = user.getName();
                    user_phoneNo = user.getPhoneNumber();

                    fullNameLabel.setText(user_name);
                    userNameLabel.setText(user_userName);
                    fullName.getEditText().setText(user_name);
                    userName.getEditText().setText(user_userName);
                    email.getEditText().setText(user_email);
                    phoneNo.getEditText().setText(user_phoneNo);
                    //password.getEditText().setText(user_password);
                    password.getEditText().setText(user_password);

                    if (user.getUserCreatedRecipes() != null) {
                        int size = user.getUserCreatedRecipes().size();
                        createdLabel.setText(String.valueOf(size));
                    }

                    if (user.getFavoriteRecipes() != null) {
                        int size = user.getFavoriteRecipes().size();
                        favoriteLabel.setText(String.valueOf(size));
                    }

                    if (!user.getProfilePicture().equals("")) {
                        downloadWithBytes();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_LONG).show();
            }
        });

        //Change profile picture
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(view);
            }
        });

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfile(view);

                if (uploadedPicture) {
                    Snackbar.make(view.findViewById(android.R.id.content), "Image successfully uploaded.", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                sp = getActivity().getSharedPreferences("login",0);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("loggedIn",false);
                //edit.clear();
                edit.commit();

                //sp.edit().putBoolean("logged",false);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment);
        fragmentTransaction.commit();
    }

    private void downloadWithBytes() {
        StorageReference imageRef = storageReference.child("profileImage/" + id + ".jpg");
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_picture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void changeProfile(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profile_picture.setImageURI(imageUri);

            uploadPicture();
        }
    }

    private void uploadPicture() {

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Uploading Image....");

        // Create a reference to "mountains.jpg"
        StorageReference profileRef = storageReference.child("profileImage/").child(id + ".jpg");

        profileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                reference.child(id).child("profilePicture").setValue(id + ".jpg");
                uploadedPicture = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress: " + (int) progressPercent + "%");
            }
        });

    }

    //Update User Data
    public void update(View view) {
        if (isNameChanged() || isPasswordChanged() || isPhoneChanged() || isUserNameChanged()) {
            //Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(getContext(), "Same data cannot be updated", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isPasswordChanged() {
        if (!user_password.equals(password.getEditText().getText().toString().trim())) {
            FirebaseUser user;
            user = FirebaseAuth.getInstance().getCurrentUser();
            String newPassword = password.getEditText().getText().toString();

            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        reference.child(id).child("password").setValue(password.getEditText().getText().toString());
                        password.getEditText().setText(password.getEditText().getText().toString());
                        flag = true;
                        Toast.makeText(getContext(), "Password Successfully Modified", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d(TAG, "onComplete: " + task.getException());
                        flag = false;
                    }
                }
            });
        } else {
            flag = false;
        }

        return flag;

    }

    private boolean isNameChanged() {
        if (!user_name.equals(fullName.getEditText().getText().toString().trim())) {
            reference.child(id).child("name").setValue(fullName.getEditText().getText().toString());
            fullNameLabel.setText(fullName.getEditText().getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isPhoneChanged() {
        if (!user_phoneNo.equals(phoneNo.getEditText().getText().toString().trim())) {
            reference.child(id).child("phoneNumber").setValue(phoneNo.getEditText().getText().toString());
            phoneNo.getEditText().setText(phoneNo.getEditText().getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isUserNameChanged() {
        if (!user_userName.equals(userName.getEditText().getText().toString().trim())) {
            reference.child(id).child("userName").setValue(userName.getEditText().getText().toString());
            userName.getEditText().setText(userName.getEditText().getText().toString());
            return true;
        } else {
            return false;
        }
    }
}