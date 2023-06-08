package com.example.scanqr_jokeio.user_wrapper;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.scanqr_jokeio.NavigationActivity;
import com.example.scanqr_jokeio.databinding.FragmentHomeBinding;
import com.example.scanqr_jokeio.databinding.FragmentProfileBinding;
import com.example.scanqr_jokeio.helper.User;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    User user;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
                user = (User) arguments.getSerializable("loggedInUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

//        Setting Up Details of LoggedIn User

        binding.userFullName.setText(user.getFullname());
        binding.userEmail.setText(user.getEmail());

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = view.getContext().getSharedPreferences("JOKE.IO_APP_PREFS", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();
                prefEditor.putBoolean("isLoggedIn",false);
                prefEditor.putString("currentUser","");
                prefEditor.apply();
                Toast.makeText(view.getContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                getActivity().finishAffinity();
                startActivity(new Intent(view.getContext().getApplicationContext(), NavigationActivity.class));
            }
        });

        return view;
    }
}