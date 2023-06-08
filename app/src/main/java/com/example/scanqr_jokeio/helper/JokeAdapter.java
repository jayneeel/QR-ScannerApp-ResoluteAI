package com.example.scanqr_jokeio.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scanqr_jokeio.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.MyViewHolder>{
    ArrayList<Joke> jokeArrayList;

    public JokeAdapter(ArrayList<Joke> jokeArrayList) {
        this.jokeArrayList = jokeArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleTxt.setText(jokeArrayList.get(position).getTitle());
        holder.jokeTxt.setText(jokeArrayList.get(position).getJoke());
        String formattedTimestamp = convertTimestamp(jokeArrayList.get(position).getTimestamp());
        holder.timeTxt.setText(formattedTimestamp);
    }

    private String convertTimestamp(String timestamp) {
        Long unixTimestamp = Long.parseLong(timestamp);
        Date date = new Date(unixTimestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d h:mma", Locale.getDefault());
        return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        return jokeArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt,jokeTxt,timeTxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.jokeTitle);
            jokeTxt = itemView.findViewById(R.id.jokeText);
            timeTxt = itemView.findViewById(R.id.timestamp);

        }
    }
}
