package com.example.scanqr_jokeio.user_wrapper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.scanqr_jokeio.R;
import com.example.scanqr_jokeio.databinding.ActivityQrscannerBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;

public class QRScannerActivity extends AppCompatActivity {
    ActivityQrscannerBinding binding;
    private CodeScanner mCodeScanner;
    private FirebaseFirestore firebaseFirestore;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrscannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();
        mCodeScanner = new CodeScanner(this, binding.qrCodeScanner);
        email = getIntent().getStringExtra("currentUser");

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                int startIndex = result.getText().indexOf("\n");
                String title = result.getText().substring(0,startIndex);
                String joke = result.getText().substring(startIndex+1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                        builder.setIcon(R.drawable.round_emoji_emotions_24);
                        builder.setTitle("Joke Found!");
                        builder.setMessage(joke);
                        builder.setPositiveButton("Mark it as Funny", (DialogInterface.OnClickListener) (dialog, which) -> {
                            pushDataToDB(title,joke,email);
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                });
            }
        });

        binding.qrCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });


    }

    private void pushDataToDB(String title, String joke,String email) {
        Map<String, Object> data = new HashMap<>(2);
        data.put("title",title);
        data.put("joke",joke);
        data.put("timestamp",String.valueOf(System.currentTimeMillis()));
        firebaseFirestore.collection("users").document(email).collection("jokes_visited").add(data);
        Toast.makeText(this, "Valued Added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(QRScannerActivity.this,DashboardActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}