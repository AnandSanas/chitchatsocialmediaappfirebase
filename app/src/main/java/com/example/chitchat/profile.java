package com.example.chitchat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chitchat.datamodels.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

    private FirebaseAuth firebaseAuth;
    ImageView coverphoto;
    TextView username,useremail,userphone,userage;
    RecyclerView recyclerView;

    String imageuploadID,currentuser;
    private StorageReference mstorageRef;


    public static final String Database_Path = "chitchat";
    String Storage_Path = "profilepic/";

//    dont work for some weird reasons
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        mstorageRef= FirebaseStorage.getInstance().getReference();


        coverphoto = (view).findViewById(R.id.coverphoto);
        userage = (view).findViewById(R.id.userage);
        username= (view).findViewById(R.id.username);
        useremail= (view).findViewById(R.id.useremail);
        userphone=(view).findViewById(R.id.userphone);

        recyclerView = (view).findViewById(R.id.recycler);

        currentuser = firebaseAuth.getCurrentUser().getUid();





        downloadprofile();



        return view;
    }


    private void downloadprofile() {


//        DatabaseReference databaseReference;
//        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path).child("UserProfile").child(currentuser);

        databaseReference =database.getReference(Database_Path).child("UserProfile").child(currentuser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                if (snapshot.exists())
//                {
//                    if (isAdded())
//                    {
//                        for (DataSnapshot mysnap : snapshot.getChildren())
//                        {

                            UserProfile userProfile =snapshot.getValue(UserProfile.class);

                            username.setText("Name: "+userProfile.getUsernameprofile());
                            useremail.setText("Email: "+userProfile.getUseremailprofile());
                            userage.setText("Age: "+userProfile.getUserageprofile());
                            userphone.setText("Phone No: "+userProfile.getUserphoneprfile());

                            Glide.with(getActivity()).load(userProfile.getUserImagepath()).into(coverphoto);


//                        }
//                    }
//                }
//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}