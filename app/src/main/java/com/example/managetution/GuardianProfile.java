package com.example.managetution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuardianProfile extends AppCompatActivity {

    // declaring variables
    private Button saveBtn, changePassBtn;
    private ImageView chooseImg;
    private CircleImageView showImg;
    private Uri filePath;
    private EditText firstNameEdit, lastNameEdit, emailEdit, userEdit, insEdit, genderEdit, passEdit, roleEdit, locationEdit, phoneEdit;
    private final int PICK_IMAGE_REQUEST = 22;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    DatabaseReference new_ref;
    FirebaseAuth mAuth;
    FirebaseUser user;


    String _NAME, _EMAIL, _PASSWORD, _FIRSTNAME,_LASTNAME,  _INSTITUTION, _GENDER, _ROLE, _USERID, _LOCATION, _PHONE, _PICTURE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_profile);

        saveBtn = findViewById(R.id.btnSave);
        showImg = findViewById(R.id.imgDP);
        chooseImg = findViewById(R.id.imgChooseDP);
        firstNameEdit = findViewById(R.id.editFirstName);
        lastNameEdit = findViewById(R.id.editLastName);
        locationEdit = findViewById(R.id.editLocation);
        phoneEdit = findViewById(R.id.editPhone);
        emailEdit = findViewById(R.id.editEmail);
        emailEdit.setEnabled(false);
        //userEdit = findViewById(R.id.editUser);
        //userEdit.setEnabled(false);
        genderEdit = findViewById(R.id.editGender);
        genderEdit.setEnabled(false);
        //passEdit = findViewById(R.id.editPass);
        roleEdit = findViewById(R.id.editRole);
        roleEdit.setEnabled(false);
        changePassBtn = findViewById(R.id.btnChangePass);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        _USERID = user.getUid();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("GuardianUser").child(_USERID);


        showAllUserData();

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Imageview clickable working");
                selectImage();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                update();
            }
        });


        //password reset function
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetPass = new EditText(v.getContext());

                final AlertDialog.Builder passResetDialog = new AlertDialog.Builder(v.getContext());
                passResetDialog.setTitle("Update Password?");
                passResetDialog.setMessage("Enter New Password here");
                passResetDialog.setView(resetPass);

                passResetDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPass.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(GuardianProfile.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                                databaseReference.child("pass").setValue(newPassword);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(GuardianProfile.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passResetDialog.create().show();
            }
        });

    }

    private void showAllUserData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                _FIRSTNAME = snapshot.child("firstname").getValue(String.class);
                _LASTNAME = snapshot.child("lastname").getValue(String.class);
                _EMAIL = snapshot.child("email").getValue(String.class);
                _GENDER = snapshot.child("gender").getValue(String.class);
                _LOCATION = snapshot.child("location").getValue(String.class);
                _ROLE = snapshot.child("role").getValue(String.class);
                _PHONE = snapshot.child("phoneNo").getValue(String.class);
                _PICTURE = snapshot.child("Picture URL").getValue(String.class);

                firstNameEdit.setText(_FIRSTNAME);
                lastNameEdit.setText(_LASTNAME);
                emailEdit.setText(_EMAIL);
                phoneEdit.setText(_PHONE);
                genderEdit.setText(_GENDER);
                locationEdit.setText(_LOCATION);
                roleEdit.setText(_ROLE);
                if(_PICTURE.length() > 1)
                    Picasso.get().load(_PICTURE).into(showImg);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        //System.out.println(_NAME);
    }

    //select Image Method
    private void selectImage(){

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);

    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                showImg.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            // Progress Listener for loading
            // percentage on the dialog box
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> {

                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast
                                        .makeText(GuardianProfile.this,
                                                "Image Uploaded!!",
                                                Toast.LENGTH_SHORT)
                                        .show();


                                //realtime database URL
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while(!uriTask.isComplete()) ;
                                Uri uri = uriTask.getResult() ;

                                String imgName = Objects.requireNonNull(uri).toString() ;

                                databaseReference.child("Picture URL").setValue(imgName);

                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(GuardianProfile.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            taskSnapshot -> {
                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(
                                        "Uploaded "
                                                + (int)progress + "%");
                            });
        }
    }


    private void update(){
        //String fullName = fullNameEdit.getText().toString();
        //databaseReference.child("Enter_name").setValue(fullName); //change korsi
        isFirstNameChanged();
        isLastNameChanged();
        isPhoneChanged();
        isLocationChanged();

        if (isFirstNameChanged() || isLocationChanged() || isLastNameChanged() || isPhoneChanged() ){
            Toast.makeText(GuardianProfile.this, "Data has been updated", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(GuardianProfile.this, "Data is same", Toast.LENGTH_LONG).show();
    }


    private boolean isFirstNameChanged() {
        if(!_FIRSTNAME.equals(firstNameEdit.getText().toString())){
            databaseReference.child("firstname").setValue(firstNameEdit.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isLastNameChanged() {
        if(!_LASTNAME.equals(lastNameEdit.getText().toString())){
            databaseReference.child("lastname").setValue(lastNameEdit.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isPhoneChanged() {
        if(!_PHONE.equals(phoneEdit.getText().toString())){
            databaseReference.child("phoneNo").setValue(phoneEdit.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isLocationChanged() {
        if(!_LOCATION.equals(locationEdit.getText().toString())){
            databaseReference.child("location").setValue(locationEdit.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    /*
    private boolean isNameChanged() {
        if(!_FIRSTNAME.equals(firstNameEdit.getText().toString())){
            databaseReference.child("firstname").setValue(firstNameEdit.getText().toString());
            new_ref = FirebaseDatabase.getInstance().getReference("courses");
            new_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String username = dataSnapshot.child("currentUser").getValue(String.class);
                        if(_NAME.equals(username)) {
                            dataSnapshot.child("teacherEnter_name").getRef().setValue(fullNameEdit.getText().toString());
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });
            return true;
        }
        else{
            return false;
        }
    }
    */
    @Override
    public void onBackPressed() {

        Intent newIntent = new Intent(this, Home_Guardian.class);
        startActivity(newIntent);
        finish();
    }

}