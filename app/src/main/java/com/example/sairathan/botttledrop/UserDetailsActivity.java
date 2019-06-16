package com.example.sairathan.botttledrop;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserDetailsActivity extends AppCompatActivity {
    EditText name,email,aadharcardnumber,address;
    String position,area,postalcode,country,city;
    Button btnsave,uploadBtn;
    Button btnLoc;
    private FirebaseAuth firebaseAuth;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    DatabaseReference reff;
    private Button button1;
    long maxid =0;
    User user;
    FirebaseStorage storage;
    StorageReference storageReference;
    Geocoder geocoder;
    List<Address> positions;
    double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name = (EditText)findViewById(R.id.editTextCode);
        email= (EditText)findViewById(R.id.editTextCode1);
        aadharcardnumber = (EditText)findViewById(R.id.editTextCode2);
        address = (EditText)findViewById(R.id.editTextCode3) ;

        btnLoc = (Button) findViewById(R.id.latlong);
        ActivityCompat.requestPermissions(UserDetailsActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        //aadharnumber = (EditText)findViewById(R.id.uploadBtn);
        btnsave = (Button)findViewById(R.id.buttonAdd);
        uploadBtn = (Button)findViewById(R.id.uploadBtn);
        user = new User();
    geocoder = new Geocoder(this, Locale.getDefault());



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

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GpsTracker gt = new GpsTracker(getApplicationContext());
                Location l = gt.getLocation();
                if( l == null){
                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                }else {
                    lat = l.getLatitude();
                    lon = l.getLongitude();

                    try {
                        positions = geocoder.getFromLocation(lat,lon,1);
                         position = positions.get(0).getAddressLine(0);
                         area = positions.get(0).getLocality();
                         city = positions.get(0).getAdminArea();
                         country = positions.get(0).getCountryName();
                         postalcode = positions.get(0).getPostalCode();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),"Lat Long added",Toast.LENGTH_SHORT).show();
                }
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String vamshi = " ";
                //Long aadh = Long.parseLong(aadharnumber.getText().toString().trim());
                final String name1 = name.getText().toString().trim();
                final String email1 = email.getText().toString().trim();
                final String phoneaadhar = aadharcardnumber.getText().toString().trim();
                final String address1 = address.getText().toString().trim();

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
                    aadharcardnumber.setError(getString(R.string.input_error_phone_invalid));
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

                if(address1.isEmpty())
                {
                    address.setError(getString(R.string.input_error_address));
                    address.requestFocus();
                    return;
                }
                user.setAddress(address.getText().toString().trim());

                if(lat == 0 || lon ==0 )
                {
                    btnLoc.setError(getString(R.string.input_error_location_invalid));
                   // btnLoc.requestFocus();
                    return;
                }
                else {
                    user.setLati(lat);
                    user.setLongi(lon);
                    user.setPosition(position);
                    user.setArea(area);
                    user.setCity(city);
                    user.setCountry(country);
                    user.setPostalcode(postalcode);
                   // Toast.makeText(UserDetailsActivity.this, "Location inserted ", Toast.LENGTH_LONG).show();
               // btnLoc.clearFocus();
                }
                user.setHome(vamshi);
                user.setOffice(vamshi);

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
        Intent intent = new Intent(this, AddressActivity.class);
        startActivity(intent);
    }
}
