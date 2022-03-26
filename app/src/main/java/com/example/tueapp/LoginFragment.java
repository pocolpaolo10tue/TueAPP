package com.example.tueapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tueapp.loginhandling.VerifyDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    Button LoginButton;
    Button RegisterButton;

    EditText InputEmail;
    EditText InputPassword;

    VerifyDetails verifier;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        LoginButton = view.findViewById(R.id.login);
        RegisterButton = view.findViewById(R.id.register);

        InputEmail = view.findViewById(R.id.username);
        InputPassword = view.findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        verifier = new VerifyDetails();

        LoginButton.setOnClickListener(item ->{
            Login();
        });
        RegisterButton.setOnClickListener(item ->{
            Register();
        });

        return view;
    }

    private void Login() {
        String email = InputEmail.getText().toString().trim();
        String password = InputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            InputEmail.setError("Email cannot be empty");
            InputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            InputPassword.setError("Password cannot be empty");
            InputPassword.requestFocus();
        } else if (!verifier.isValidEmail(email)) {
            InputEmail.setError(verifier.whyEmailNotValid(email));
            InputEmail.requestFocus();
        } else if (!verifier.isValidPassword(password)) {
            InputPassword.setError(verifier.whyPassNotValid(password));
            InputPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),
                                        "User Logged in", Toast.LENGTH_SHORT).show();
                                account_info();
                            } else {
                                Toast.makeText(getActivity(), "Error: " +
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void Register() {
        Fragment newFragment = new RegisterFragment() ;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void account_info() {
        Fragment newFragment = new LoginInfoFragment() ;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}