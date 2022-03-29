package com.example.tueapp;

import com.example.tueapp.loginhandling.VerifyDetails;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    EditText InputEmail;
    EditText InputPassword;
    EditText InputFirstName;
    EditText InputLastName;

    Button LoginButton;
    Button RegisterButton;
    VerifyDetails verifier;
    FirebaseAuth mAuth;
    FirebaseDatabase database;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        //Create new verifier to verify input
        verifier = new VerifyDetails();

        //Variable for textfield first name
        InputFirstName = view.findViewById(R.id.first_name);
        //Variable for textfield last name
        InputLastName = view.findViewById(R.id.last_name);
        //Variable for textfield email
        InputEmail = view.findViewById(R.id.username);
        //Variable for textfield password
        InputPassword = view.findViewById(R.id.password);

        //button sign in
        LoginButton = view.findViewById(R.id.login);
        //button register
        RegisterButton = view.findViewById(R.id.register);

        //get instance of firebase
        database = FirebaseDatabase.getInstance
                ("https://project2-bb61c-default-rtdb.europe-west1.firebasedatabase.app");

        //get instance of authentication
        mAuth = FirebaseAuth.getInstance();

        //OnClick listener for the register method
        RegisterButton.setOnClickListener(item ->{
            Register();
        });
        //on click listener to go to login fragment
        LoginButton.setOnClickListener(item ->{
            Login();
        });

        return view;
    }

    private void Register() {
        //get email from textfield
        String email = InputEmail.getText().toString().trim();
        //get password from textfield
        String password = InputPassword.getText().toString();
        //get first name from textfield
        String first_name = InputFirstName.getText().toString();
        //get last name from textfield
        String last_name = InputLastName.getText().toString();

        //if email is empty set error
        if (TextUtils.isEmpty(email)) {
            InputEmail.setError("Email cannot be empty");
            InputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {   //if password is empty print error and turn on
            // red error sign on textbox
            InputPassword.setError("Password cannot be empty");
            InputPassword.requestFocus();
        } else if (TextUtils.isEmpty(first_name)) { //if first name is empty print error and turn on
            // red error sign on textbox
            InputFirstName.setError("First name cannot be empty");
            InputFirstName.requestFocus();
        } else if (TextUtils.isEmpty(last_name)) { //if last name is empty set error and turn on
            // red error sign on textbox
            InputLastName.setError("Last name cannot be empty");
            InputLastName.requestFocus();
        } else if (!verifier.isValidEmail(email)) { //if email is not valid print error and turn on
            // red error sign on textbox
            InputEmail.setError(verifier.whyEmailNotValid(email));
            InputEmail.requestFocus();
        } else if (!verifier.isValidPassword(password)) { //if password is not valid print error and
            // turn on red error sign on textbox
            InputPassword.setError(verifier.whyPassNotValid(password));
            InputPassword.requestFocus();
        } else if (!verifier.isValidName(first_name)) { //if first name is not valid print error
            // and turn on red error sign on textbox
            InputFirstName.setError(verifier.whyNameNotValid(first_name));
            InputFirstName.requestFocus();
        } else if (!verifier.isValidName(last_name)) { //if last name is not valid print error and
            // turn on red error sign on textbox
            InputLastName.setError(verifier.whyNameNotValid(last_name));
            InputLastName.requestFocus();
        } else {
            //create user with email and password and create new on complete listener
            mAuth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //if task is successful show message account created
                                Toast.makeText(getActivity(),
                                        "Account Created", Toast.LENGTH_SHORT).show();
                                //Update the profile
                                UserProfileChangeRequest profileUpdates = new
                                        UserProfileChangeRequest.Builder()
                                        .setDisplayName(first_name + " " + last_name)
                                        .build();
                                mAuth.getCurrentUser().updateProfile(profileUpdates);
                                //go to login fragment
                                Login();
                            } else {
                                //show error message
                                Toast.makeText(getActivity(), "Error: " + task.getException()
                                        .getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void Login() {
        Fragment newFragment = new LoginFragment() ;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}