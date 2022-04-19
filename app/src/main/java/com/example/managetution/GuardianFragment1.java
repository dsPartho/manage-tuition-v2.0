package com.example.managetution;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuardianFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuardianFragment1 extends Fragment {

    private EditText firstName, lastName, email, pass, confirmPass, location, phoneNo;
    private RadioGroup gender;
    private RadioButton maleBtn, femaleBtn;
    private Button btnSignUpG,btnLoginG;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String userId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuardianFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuardianFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static GuardianFragment1 newInstance(String param1, String param2) {
        GuardianFragment1 fragment = new GuardianFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        view = inflater.inflate(R.layout.fragment_guardian1, container, false);

        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        email = view.findViewById(R.id.regEmail);
        pass = view.findViewById(R.id.regPassword);
        confirmPass = view.findViewById(R.id.regConfirmPassword);
        location = view.findViewById(R.id.regLocation);
        gender = view.findViewById(R.id.male_female);
        btnSignUpG = view.findViewById(R.id.btnSignUpGuardian);
        btnLoginG = view.findViewById(R.id.backToLogin);
        maleBtn = view.findViewById(R.id.male);
        femaleBtn = view.findViewById(R.id.female);
        phoneNo = view.findViewById(R.id.contactNo);

        mAuth = FirebaseAuth.getInstance();

        btnSignUpG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGuardian();
            }
        });

        btnLoginG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }

    public void createGuardian(){

        String firstname = firstName.getText().toString();
        String lastname = lastName.getText().toString();
        String s_email = email.getText().toString();
        String s_pass = pass.getText().toString();
        String s_confirmPass = confirmPass.getText().toString();
        String male = maleBtn.getText().toString();
        String female = femaleBtn.getText().toString();
        String s_location = location.getText().toString();
        String contactInfo = phoneNo.getText().toString();


        if(firstname.isEmpty()){
            firstName.setError("FullName is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(lastname.isEmpty()){
            lastName.setError("UserName is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(s_email.isEmpty()){
            email.setError("Email is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(s_pass.isEmpty()){
            pass.setError("Password is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(s_confirmPass.isEmpty()){
            confirmPass.setError("Retype the Password");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!s_pass.equals(s_confirmPass)){
            confirmPass.setError("Password doesn't match");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!maleBtn.isChecked() && !femaleBtn.isChecked()){
            Toast.makeText(getContext(), "Please Select your gender", Toast.LENGTH_SHORT).show();
            return;
        }

        String gender = "";
        if(maleBtn.isChecked()){
            gender = "male";
        }
        else{
            gender = "female";
        }
        String finalGender = gender;
        String role = "Guardian";
        mAuth.createUserWithEmailAndPassword(s_email,s_pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                firebaseUser = mAuth.getCurrentUser();
                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Plz verify email !!", Toast.LENGTH_SHORT).show();

                            //Set User Display Name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName("Guardian").build();
                            firebaseUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                            //

                            Intent i = new Intent(getActivity(), LoginActivity.class);
                            i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getActivity(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                userId = firebaseUser.getUid();

               // GuardianUsers guardianUsers = new GuardianUsers(role, firstname,lastname, s_email,s_pass,s_location, finalGender, contactInfo);
                GuardianUsersByPartho guardianUsers = new GuardianUsersByPartho(userId, "", role, firstname,lastname, s_email,s_pass,s_location, finalGender, contactInfo);
                SignInData signInData = new SignInData(s_email,s_pass,role);
                FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("GuardianUser").child(userId).setValue(guardianUsers).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "database inserted", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(getActivity(), "databse not working", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("SignInData").child(userId).setValue(signInData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(getActivity(), "database inserted in sign in", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(getActivity(), "databse not working", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}