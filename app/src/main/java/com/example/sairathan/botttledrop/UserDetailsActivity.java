package com.example.sairathan.botttledrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserDetailsActivity extends AppCompatActivity {
    EditText name,email,aadharcardnumber;

    Button btnsave,uploadBtn;
    private FirebaseAuth firebaseAuth;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    DatabaseReference reff;
    private Button button1;
    long maxid =0;
    User user;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name = (EditText)findViewById(R.id.editTextCode);
        email= (EditText)findViewById(R.id.editTextCode1);
        aadharcardnumber = (EditText)findViewById(R.id.editTextCode2);


        //aadharnumber = (EditText)findViewById(R.id.uploadBtn);
        btnsave = (Button)findViewById(R.id.buttonAdd);
        uploadBtn = (Button)findViewById(R.id.uploadBtn);
        user = new User();


        firebaseAuth = FirebaseAuth.getInstance();

        storage =FirebaseStorage.getInstance();
        storageReference =storage.getReference();


        reff = FirebaseDatabase.getInstance().getReference().child("User");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseUser user1 = firebaseAuth.getCurrentUser();

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Long aadh = Long.parseLong(aadharnumber.getText().toString().trim());
                final String name1 = name.getText().toString().trim();
                final String email1 = email.getText().toString().trim();
                final String phoneaadhar = aadharcardnumber.getText().toString().trim();
                if(name1.isEmpty())
                {
                    name.setError(getString(R.string.input_error_name));
                    name.requestFocus();
                    return;
                }

                    user.setName(name.getText().toString().trim());

                if (email1.isEmpty()) {
                    email.setError(getString(R.string.input_error_email));
                    email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    email.setError(getString(R.string.input_error_email_invalid));
                    email.requestFocus();
                    return;
                }

                    user.setEmail(email.getText().toString().trim());


                if (phoneaadhar.isEmpty()) {
                    aadharcardnumber.setError(getString(R.string.input_error_phone));
                    aadharcardnumber.requestFocus();
                    return;
                }

                if (phoneaadhar.length() != 12) {
                    aadharcardnumber.setError(getString(R.string.input_error_phone_invalid));
                    aadharcardnumber.requestFocus();
                    return;
                }
                Long phn = Long.parseLong(aadharcardnumber.getText().toString().trim());
                user.setAadharcardnumber(phn);

                //user.setAadharnumber(aadh);
               // reff.child(String.valueOf(maxid+1)).setValue(user);
                if(uploadImage()){
                    FirebaseUser user1 = firebaseAuth.getCurrentUser();
                    reff.child(user1.getUid()).setValue(user);
                    Toast.makeText(UserDetailsActivity.this, "Data inserted ", Toast.LENGTH_LONG).show();
                    login12();
                }
                else{
                    uploadBtn.setError("Image required");
                }
            }
        });
    }

    private boolean uploadImage() {
        if(filePath!=null) {
            final ProgressDialog progressDialog =  new ProgressDialog(this);
            progressDialog.setTitle("Uploading..!!");
            progressDialog.show();
            StorageReference reff = storageReference.child(firebaseAuth.getCurrentUser().getUid().toString());
            reff.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(UserDetailsActivity.this,"Uploaded",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UserDetailsActivity.this,"Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress= (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
            return true;
        }
        return false;
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    /**
     *
     */
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null){
            filePath=data.getData();
        }
    }

    public void login12(){
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }
}
