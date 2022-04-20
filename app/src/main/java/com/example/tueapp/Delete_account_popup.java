package com.example.tueapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Delete_account_popup extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button delete;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account_popup);

        mAuth = FirebaseAuth.getInstance();
        delete = findViewById(R.id.button);
        cancel = findViewById(R.id.button2);

        delete.setOnClickListener(item ->{
            delete();
        });
        cancel.setOnClickListener(item ->{
            finish();
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int)(width*.7), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    private void delete() {
        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Account Deleted", Toast.LENGTH_SHORT).show();
                    Intent main = new Intent(Delete_account_popup.this
                            , MainActivity.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(main);
                }
            }
        });
    }
}