package com.example.foodorderapp.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.foodorderapp.R;
import com.example.foodorderapp.utility.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailEditText;
    Button forgotPasswordBtnTextView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.email_edit_text);
        forgotPasswordBtnTextView = findViewById(R.id.reset_password_btn);
        progressBar = findViewById(R.id.progress_bar);


        forgotPasswordBtnTextView.setOnClickListener(v -> forgotPassword());

    }

    private void forgotPassword() {
        if (!validateData(emailEditText.getText().toString())) {
            return;
        }
        onClickForgotPassword();
    }

    private void onClickForgotPassword() {
        changeInProgress(true);
        String email = emailEditText.getText().toString();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                changeInProgress(false);
                if (task.isSuccessful()) {
                    Utility.ShowToast(ForgotPasswordActivity.this, "Vui lòng kiểm tra email");
                    finish();
                } else {
                    Utility.ShowToast(ForgotPasswordActivity.this, task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void changeInProgress(boolean inProgresss) {
        if (inProgresss) {
            progressBar.setVisibility(View.VISIBLE);
            forgotPasswordBtnTextView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            forgotPasswordBtnTextView.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email) {
        // validate the data that are input by user
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email is invalid");
            return false;
        }
        return true;
    }
}