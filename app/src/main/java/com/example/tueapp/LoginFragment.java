package com.example.tueapp;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tueapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    Button RegisterButton;
    Button LoginButton;
    EditText InputEmail;
    EditText InputPassword;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        RegisterButton = view.findViewById(R.id.register);
        LoginButton = view.findViewById(R.id.login);
        InputEmail = view.findViewById(R.id.username);
        InputPassword = view.findViewById(R.id.password);
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
        System.out.println("hoi");
        String email = InputEmail.getText().toString().trim();
        String password = InputPassword.getText().toString().trim();
        Boolean tuemail = email.substring(email.length()-6).equals("tue.nl");
        if (TextUtils.isEmpty(email)) {
            InputEmail.setError("Email cannot be empty");
            InputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            InputPassword.setError("Password cannot be empty");
            InputPassword.requestFocus();
        } else if (password.length() <= 5) {
            // TODO: Pass 6 length and one number
            InputPassword.setError("Password has to be atleast 5 characters");
            InputPassword.requestFocus();
        } else if (!tuemail) {
            InputEmail.setError("Please use a tu/e mail.");
            InputEmail.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),
                                        "Account Created", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Error: " +
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void Login() {
        String email = InputEmail.getText().toString().trim();
        String password = InputPassword.getText().toString().trim();
//        Boolean tuemail = email.substring(email.length()-6) == "tue.nl";
        if (TextUtils.isEmpty(email)) {
            InputEmail.setError("Please enter a valid tu/e email");
            InputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            InputPassword.setError("Password cannot be empty");
            InputPassword.requestFocus();
        } else if (password.length() <= 5) {
            InputPassword.setError("Password has to be atleast 5 characters");
            InputPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),
                                        "User Logged in", Toast.LENGTH_SHORT).show();
                                // TODO: Go back to mainactivity
                            } else {
                                Toast.makeText(getActivity(), "Error: " +
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}