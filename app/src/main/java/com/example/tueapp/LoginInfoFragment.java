package com.example.tueapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInfoFragment extends Fragment {

    TextView EmailText;
    TextView NameText;

    TextView user_manual;
    TextView change_password;
    TextView log_out;
    Button delete_account;

    FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_accountinfo, container, false);

        mAuth = FirebaseAuth.getInstance();

        EmailText = view.findViewById(R.id.Email);
        NameText = view.findViewById(R.id.name);

        user_manual = view.findViewById(R.id.user_manual);
        change_password = view.findViewById(R.id.change_password);
        log_out = view.findViewById(R.id.logout);
        delete_account = view.findViewById(R.id.delete_account);

        EmailText.setText("Email: " + mAuth.getCurrentUser().getEmail());
        NameText.setText("Name: " + mAuth.getCurrentUser().getDisplayName());

        change_password.setOnClickListener(item ->{
            mAuth.sendPasswordResetEmail(mAuth.getCurrentUser().getEmail())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(),
                                    "Please check your email.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        log_out.setOnClickListener(item ->{
            mAuth.signOut();
            Register();
        });

        user_manual.setOnClickListener(item ->{
            // TODO: Redirect to user manual
        });

        delete_account.setOnClickListener(item ->{
            delete_popup();
        });

        return view;
    }

    private void Register() {
        Fragment newFragment = new RegisterFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void delete_popup() {
        Intent popup = new Intent(getActivity(), Delete_account_popup.class);
        startActivity(popup);
    }

}
