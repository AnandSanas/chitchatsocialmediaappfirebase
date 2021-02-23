package com.example.chitchat.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chitchat.R;
import com.example.chitchat.datamodels.UserProfile;
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

import static android.app.PendingIntent.getActivity;

public class updateprofile extends AppCompatActivity {

    Button updatebtn;

    private FirebaseAuth firebaseAuth;
    ImageView coverphoto;
    TextView username, useremail, userphone, userage;

    String imageuploadID,currentuser;
    private StorageReference mstorageRef;
    private int browse_gallery = 4;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference(Database_Path);
    public static final String Database_Path = "chitchat";
    String Storage_Path = "profilepic/";


    String imagedownloadURL;
    ProgressBar progressBar3;
    String str_userage, str_username, str_useremail, str_userphone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.commit();     // work like intent

        updatebtn = findViewById(R.id.updatebtn);

        firebaseAuth = FirebaseAuth.getInstance();
        mstorageRef = FirebaseStorage.getInstance().getReference();


        coverphoto = findViewById(R.id.coverphoto);
        userage = findViewById(R.id.userage);
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);
        userphone = findViewById(R.id.userphone);
        progressBar3 = findViewById(R.id.progressBar3);







        coverphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browseimage = new Intent(Intent.ACTION_PICK);
                browseimage.setType("image/*");
                startActivityForResult(browseimage, browse_gallery);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        final Uri uri = data.getData();

        if (requestCode == browse_gallery) {
            coverphoto.setImageURI(uri);
        }


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_userage = userage.getText().toString();
                str_username = username.getText().toString();
                str_useremail = useremail.getText().toString();
                str_userphone = userphone.getText().toString();

                progressBar3.setVisibility(View.VISIBLE);

                final StorageReference filname = mstorageRef.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtention(uri));

                final UploadTask uploadTask = filname.putFile(uri);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                                imagedownloadURL = filname.getDownloadUrl().toString();

                                return filname.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                if (task.isSuccessful()) {

                                    imagedownloadURL = task.getResult().toString();
                                    imageuploadID = databaseReference.push().getKey();

                                    upload();
                                    progressBar3.setVisibility(View.GONE);
                                    Intent updateprofileintent  = new Intent(updateprofile.this,MainActivity.class);
                                    startActivity(updateprofileintent);
                                    finish();

                                }

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(updateprofile.this, " Profile Update Failed ",Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


    }

    private void upload() {

        UserProfile userProfile = new UserProfile(str_username, str_useremail,str_userphone, str_userage, imagedownloadURL);
        currentuser=firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("UserProfile").child(currentuser).setValue(userProfile);

        Toast.makeText(updateprofile.this, " Profile Updated successfully",Toast.LENGTH_SHORT).show();

    }

    private String GetFileExtention(Uri uri1) {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri1));
    }


}