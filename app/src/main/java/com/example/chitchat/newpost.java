package com.example.chitchat;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chitchat.activities.MainActivity;
import com.example.chitchat.datamodels.Postcommentimage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newpost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newpost extends Fragment {

// rules for chitchat
//    rules_version = '2';
//    service firebase.storage {
//        match /b/{bucket}/o {
//            match /{allPaths=**} {
//                allow read, write: if request.auth != null;
//            }
//        }
//    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    ImageView uploadpostimg;
    EditText uploadpostcomment;
    Button postuploadbtn;
    ProgressBar progressBar2;
    FragmentTransaction transaction;
    public static final String Database_Path = "chitchat";
    String Storage_Path = "chit_chat";
    String imageuploadID;



    private FirebaseAuth auth;
    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference databaseReference;
    String imagedownloadURL;

    private int gallery = 5;
    String uploadcommentstring,currentuser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public newpost() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment newpost.
     */
    // TODO: Rename and change types and number of parameters
    public static newpost newInstance(String param1, String param2) {
        newpost fragment = new newpost();
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
        View view = inflater.inflate(R.layout.fragment_newpost, container, false);


        uploadpostimg = (view).findViewById(R.id.uploadpostimg);
        uploadpostcomment =(view).findViewById(R.id.uploadpostcomment);
        postuploadbtn = (view).findViewById(R.id.postuploadbtn);
        transaction =getActivity().getSupportFragmentManager().beginTransaction();
        progressBar2 = (view).findViewById(R.id.progressBar2);

        databaseReference = database.getReference(Database_Path);



        auth=FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();





        uploadpostimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browseintent = new Intent(Intent.ACTION_PICK);
                browseintent.setType("image/*");
                startActivityForResult(browseintent, gallery);

            }
        });

                return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




            final Uri uri = data.getData();

            if (requestCode == gallery) {
                uploadpostimg.setImageURI(uri);// set image on imageviewertag
            }


            postuploadbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progressBar2.setVisibility(View.VISIBLE);

                    uploadcommentstring = uploadpostcomment.getText().toString();
//
//                Postcommentimage postcommentimage =new Postcommentimage(uploadcommentstring,imagedownloadURL);
//
//
//                currentuser = auth.getCurrentUser().getUid();
//                Chitchat.child(currentuser).setValue(postcommentimage);

//                    String currentuserID = auth.getCurrentUser().getUid();

                    final StorageReference filename = mStorageRef.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtention(uri));

                    final UploadTask uploadTask = filename.putFile(uri);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getActivity(), "Posted Successfully", Toast.LENGTH_LONG).show();

                            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                                    imagedownloadURL = filename.getDownloadUrl().toString();



                                    return filename.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    if (task.isSuccessful()) {
                                        imagedownloadURL = task.getResult().toString();
                                        imageuploadID = databaseReference.push().getKey();
                                        uploadpost();


//                                    transaction.replace(R.id.newpost, new homefragrent());
//                                    transaction.commit();

                                        progressBar2.setVisibility(View.GONE);
                                        Intent homeafterpost = new Intent(getContext(), MainActivity.class);
                                        startActivity(homeafterpost);

                                    }

                                }
                            });
                        }
                    })

//
//                  if we dont want url of uploaded image then there is no need od uploadtask and  its listner wecan directly aplly below listner
//
//                filename.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(getActivity(),"Posted Successfully",Toast.LENGTH_LONG).show();
//
//                    }
//                })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getActivity(), "Post not uploaded", Toast.LENGTH_LONG).show();

                                }
                            });
                }


                // Creating Method to get the selected image file Extension from File Path URI.
                private String GetFileExtention(Uri uri1) {

                    ContentResolver contentResolver = getActivity().getContentResolver();

                    MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

                    // Returning the file Extension.
                    return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri1)) ;

                }

//                private void uploadpost() {
//
//
//                    Postcommentimage postcommentimage = new Postcommentimage(uploadcommentstring, imagedownloadURL);
//
//
//                    currentuser = auth.getCurrentUser().getUid();
//                    databaseReference.child("UserPost").child(imageuploadID).setValue(postcommentimage);
//                }


            });


        }

    private void uploadpost() {


         Postcommentimage postcommentimage = new Postcommentimage(uploadcommentstring, imagedownloadURL);
                    currentuser = auth.getCurrentUser().getUid();
                    databaseReference.child("UserPost").child(imageuploadID).setValue(postcommentimage);

    }



}