package com.example.scanqr_jokeio.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.scanqr_jokeio.user_wrapper.DashboardActivity;
import com.example.scanqr_jokeio.NavigationActivity;
import com.example.scanqr_jokeio.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        context = binding.getRoot().getContext();

//        Back Button OnClick
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, NavigationActivity.class));
            }
        });

//        Forget Password TextView OnClick
        binding.siForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ForgetPasswordActivity.class));
            }
        });

//      Login Button OnClick
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.liEmailET.getText().toString();
                String password  = binding.liPassET.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                                SharedPreferences settings = getSharedPreferences("JOKE.IO_APP_PREFS", MODE_PRIVATE);
                                SharedPreferences.Editor prefEditor = settings.edit();
                                prefEditor.putBoolean("isLoggedIn",true);
                                prefEditor.putString("currentUser",email);
                                prefEditor.apply();
                                finishAffinity();
                                startActivity(new Intent(context, DashboardActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Bad Credentials!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



//      Create Account TextView OnClick
        binding.siCreateAccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,SignUpActivity.class));
            }
        });


    }
}