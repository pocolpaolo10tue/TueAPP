package com.example.tueapp;

import com.example.tueapp.loginhandling.User;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    EditText InputEmail;
    EditText InputPassword;
    EditText InputFirstName;
    EditText InputLastName;

    Button LoginButton;
    Button RegisterButton;
    VerifyDetails verifier;
    User user;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        verifier = new VerifyDetails();

        InputFirstName = view.findViewById(R.id.first_name);
        InputLastName = view.findViewById(R.id.last_name);
        InputEmail = view.findViewById(R.id.username);
        InputPassword = view.findViewById(R.id.password);

        LoginButton = view.findViewById(R.id.login);
        RegisterButton = view.findViewById(R.id.register);

        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        RegisterButton.setOnClickListener(item ->{
            Register();
        });
        LoginButton.setOnClickListener(item ->{
            Login();
        });

        return view;
    }

    private void Register() {
        String email = InputEmail.getText().toString().trim();
        String password = InputPassword.getText().toString();
        String first_name = InputFirstName.getText().toString();
        String last_name = InputLastName.getText().toString();

        if (TextUtils.isEmpty(email)) {
            InputEmail.setError("Email cannot be empty");
            InputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            InputPassword.setError("Password cannot be empty");
            InputPassword.requestFocus();
        } else if (TextUtils.isEmpty(first_name)) {
            InputFirstName.setError("First name cannot be empty");
            InputFirstName.requestFocus();
        } else if (TextUtils.isEmpty(last_name)) {
            InputLastName.setError("Last name cannot be empty");
            InputLastName.requestFocus();
        } else if (!verifier.isValidEmail(email)) {
            InputEmail.setError(verifier.whyEmailNotValid(email));
            InputEmail.requestFocus();
            System.out.println(verifier.whyEmailNotValid(email));
        } else if (!verifier.isValidPassword(password)) {
            InputPassword.setError(verifier.whyPassNotValid(password));
            InputPassword.requestFocus();
        } else if (!verifier.isValidName(first_name)) {
            InputFirstName.setError(verifier.whyNameNotValid(first_name));
            InputFirstName.requestFocus();
        } else if (!verifier.isValidName(last_name)) {
            InputLastName.setError(verifier.whyNameNotValid(last_name));
            InputLastName.requestFocus();
        } else {
            user = new User(last_name, first_name, email, password);
            mAuth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),
                                        "Account Created", Toast.LENGTH_SHORT).show();
                                DatabaseReference myRef =
                                        database.getReference(mAuth.getCurrentUser().getEmail());
                                myRef.setValue(user);
                                Login();
                            } else {
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