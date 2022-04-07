package com.example.managetution;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TutorFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorFragment1 extends Fragment {

    private EditText firstName,lastName, regEmail, regPass, regConfirmPass, regInstitution, regBatch, regAcademicYear, regCurrentSem, contactNo;
    private Button signUpBtn, backToLoginBtn;
    private RadioGroup gender;
    private RadioButton maleBtn, femaleBtn;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TutorFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TutorFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static TutorFragment1 newInstance(String param1, String param2) {
        TutorFragment1 fragment = new TutorFragment1();
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
        view = inflater.inflate(R.layout.fragment_tutor1, container, false);

        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        regEmail = view.findViewById(R.id.regEmail);
        regPass = view.findViewById(R.id.regPassword);
        regConfirmPass = view.findViewById(R.id.regConfirmPassword);
        regInstitution = view.findViewById(R.id.regInstitution);
        regBatch = view.findViewById(R.id.regBatch);
        regAcademicYear = view.findViewById(R.id.regAC_year);
        regCurrentSem = view.findViewById(R.id.regCurrentSem);
        gender = view.findViewById(R.id.male_female);
        signUpBtn = view.findViewById(R.id.btnSignUpTutor);
        backToLoginBtn = view.findViewById(R.id.backToLogin);
        maleBtn = view.findViewById(R.id.male);
        femaleBtn = view.findViewById(R.id.female);
        contactNo = view.findViewById(R.id.contactNo);
        progressBar = view.findViewById(R.id.simpleProgressBar);

        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTutor();
            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }

    public void createTutor(){

        String firstname = firstName.getText().toString();
        String lastname = lastName.getText().toString();
        String email = regEmail.getText().toString();
        String pass = regPass.getText().toString();
        String confirmPass = regConfirmPass.getText().toString();
        String institution = regInstitution.getText().toString();
        String male = maleBtn.getText().toString();
        String female = femaleBtn.getText().toString();
        String batch = regBatch.getText().toString();
        String academicYear = regAcademicYear.getText().toString();
        String currentSem = regCurrentSem.getText().toString();
        String contactInfo = contactNo.getText().toString();

        /*if(firstname.isEmpty()){
            firstName.setError("FullName is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(lastname.isEmpty()){
            lastName.setError("UserName is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if(email.isEmpty()){
            regEmail.setError("Email is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.isEmpty()){
            regPass.setError("Password is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(confirmPass.isEmpty()){
            regConfirmPass.setError("Retype the Password");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pass.equals(confirmPass)){
            regConfirmPass.setError("Password doesn't match");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!maleBtn.isChecked() && !femaleBtn.isChecked()){
            Toast.makeText(getContext(), "Please Select your gender", Toast.LENGTH_SHORT).show();
            return;
        }

        /*if(batch.isEmpty()){
            regBatch.setError("FullName is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(academicYear.isEmpty()){
            regAcademicYear.setError("FullName is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(contactInfo.isEmpty()){
            contactNo.setError("mobile no is mandatory!");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }*/

       /* mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   // Toast.makeText(getContext(), "User created Successfully", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getContext(), Home.class));
                }
                else{
                    Toast.makeText(getContext(), "ERROOOORRRRR signup" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                firebaseUser = mAuth.getCurrentUser();
                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Plz verify email !!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), LoginActivity.class);
                            i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }else{
                            Toast.makeText(getActivity(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                String userId = firebaseUser.getUid();
                TutorUsers tutorUsers = new TutorUsers(firstname,lastname,email,pass,institution,male,female,batch,academicYear,contactInfo);
                FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("TutorUser").child(userId).setValue(tutorUsers).addOnCompleteListener(new OnCompleteListener<Void>() {
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //checking if git is working

}