package com.example.chitchat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chitchat.adapters.RecycleFirebaseAdapter;
import com.example.chitchat.datamodels.Postcommentimage;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homefragrent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homefragrent extends Fragment {



    private RecycleFirebaseAdapter recycleFirebaseAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    public static final String Database_Path = "chitchat";
    String Storage_Path = "chit_chat";
    private RecyclerView.LayoutManager layoutManager;
    String userID="";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homefragrent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homefragrent.
     */
    // TODO: Rename and change types and number of parameters
    public static homefragrent newInstance(String param1, String param2) {
        homefragrent fragment = new homefragrent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefragrent, container, false);


        recyclerView= (view).findViewById(R.id.recycler);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        databaseReference = database.getReference(Database_Path).child("UserPost");


        FirebaseRecyclerOptions<Postcommentimage> options =
                new FirebaseRecyclerOptions.Builder<Postcommentimage>()
                        .setQuery(databaseReference, Postcommentimage.class)
                        .build();





        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recycleFirebaseAdapter= new RecycleFirebaseAdapter(options);
        recyclerView.setAdapter(recycleFirebaseAdapter);




        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recycleFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recycleFirebaseAdapter.stopListening();
    }

}

