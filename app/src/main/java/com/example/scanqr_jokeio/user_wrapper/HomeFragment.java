package com.example.scanqr_jokeio.user_wrapper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scanqr_jokeio.databinding.FragmentHomeBinding;
import com.example.scanqr_jokeio.helper.Joke;
import com.example.scanqr_jokeio.helper.JokeAdapter;
import com.example.scanqr_jokeio.helper.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private FirebaseFirestore db;
    User user;
    private Context context;
    private ArrayList<Joke> jokeArrayList;
    private JokeAdapter adapter;




    public HomeFragment() {
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
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        context = binding.getRoot().getContext();
        db = FirebaseFirestore.getInstance();
        jokeArrayList = new ArrayList<>();
        adapter = new JokeAdapter(jokeArrayList);
        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));

//        Fetching Jokes from Firestore and Inflating it in RC View

        db.collection("users").document(user.getEmail()).collection("jokes_visited").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> jokesList = queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot d : jokesList){
                                    Joke joke = d.toObject(Joke.class);
                                    jokeArrayList.add(joke);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });





        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QRScannerActivity.class);
                intent.putExtra("currentUser",user.getEmail());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }
}