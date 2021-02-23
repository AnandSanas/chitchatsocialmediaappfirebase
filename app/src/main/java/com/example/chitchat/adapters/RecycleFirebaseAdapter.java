package com.example.chitchat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chitchat.datamodels.Postcommentimage;
import com.example.chitchat.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleFirebaseAdapter extends FirebaseRecyclerAdapter<Postcommentimage,RecycleFirebaseAdapter.RecycleViewHolder> {


    public RecycleFirebaseAdapter(@NonNull FirebaseRecyclerOptions<Postcommentimage> options) {
        super(options);
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;


        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.postedimage);
            textView=itemView.findViewById(R.id.postedcomment);

        }
    }


    @Override
    protected void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int i, @NonNull Postcommentimage postcommentimage) {


        recycleViewHolder.textView.setText(postcommentimage.getUserComment());
        Glide.with(recycleViewHolder.imageView.getContext()).load(postcommentimage.getUserpostimage()).into(recycleViewHolder.imageView);

    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homefrgmentlayout,parent,false);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view);
        return recycleViewHolder;
    }




    }




