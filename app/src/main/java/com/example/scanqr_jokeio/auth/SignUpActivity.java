package com.example.scanqr_jokeio.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.scanqr_jokeio.databinding.ActivitySignUpBinding;
import com.example.scanqr_jokeio.user_wrapper.DashboardActivity;
import com.example.scanqr_jokeio.NavigationActivity;
import com.example.scanqr_jokeio.helper.AuthHelper;
import com.example.scanqr_jokeio.helper.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Firebase Object
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

//      Back Button onclick
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(binding.getRoot().getContext(), NavigationActivity.class));
            }
        });


//        Register Button onClick
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.siEmailET.getText().toString();
                String password = binding.siPasswordET.getText().toString();
                String fullname = binding.siFullnameET.getText().toString();

                if (!AuthHelper.checkEmail(email)){
                    binding.siEmailET.setError("Invalid Email");
                }else if (password.length() < 7){
                    Toast.makeText(SignUpActivity.this, "Password must be atleast 8 characters. ", Toast.LENGTH_SHORT).show();
                }else{

                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    String hashPassword = AuthHelper.hashPassword(password);
                                    User user = new User(fullname,email,authResult.getUser().getUid(),hashPassword);
                                    firebaseFirestore.collection("users").document(email).set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(binding.getRoot().getContext(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                                    SharedPreferences settings = getSharedPreferences("JOKE.IO_APP_PREFS", MODE_PRIVATE);
                                                    SharedPreferences.Editor prefEditor = settings.edit();
                                                    prefEditor.putBoolean("isLoggedIn",true);
                                                    prefEditor.putString("currentUser",user.getEmail());
                                                    prefEditor.apply();
                                                    finishAffinity();
                                                    startActivity(new Intent(binding.getRoot().getContext(), DashboardActivity.class));
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(SignUpActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUpActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }
            }
        });

    }
}
