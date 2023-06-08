package com.example.scanqr_jokeio.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.scanqr_jokeio.databinding.ActivityForgetPasswordBinding;
import com.example.scanqr_jokeio.helper.AuthHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ActivityForgetPasswordBinding binding;
    private Context context;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = binding.getRoot().getContext();
        firebaseAuth = FirebaseAuth.getInstance();


//        FOR BACK BUTTON
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LoginActivity.class));
            }
        });


//      OnClick on ForgetPassword
        binding.forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resetEmail = binding.resetEmail.getText().toString();
                if (!resetEmail.isEmpty() && AuthHelper.checkEmail(resetEmail)){
                    forgetPass(resetEmail);
                }else {
                    binding.resetEmail.setError("Not a Valid Email Address");
                }
            }
        });
    }

    private void forgetPass(String resetEmail) {
        firebaseAuth.sendPasswordResetEmail(resetEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(context, LoginActivity.class));
                            Toast.makeText(context, "Password reset link Sent to you email successfully!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Some Error Occured :"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}