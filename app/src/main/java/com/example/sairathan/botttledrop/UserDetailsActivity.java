package com.example.sairathan.botttledrop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetailsActivity extends AppCompatActivity {
EditText name,email,aadharcardnumber,aadharnumber;

Button btnsave;
private FirebaseAuth firebaseAuth;
DatabaseReference reff;
    private Button button1;
long maxid =0;
User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name = (EditText)findViewById(R.id.editTextCode);
        email= (EditText)findViewById(R.id.editTextCode1);
        aadharcardnumber = (EditText)findViewById(R.id.editTextCode2);


        aadharnumber = (EditText)findViewById(R.id.editTextCode3);
        btnsave = (Button)findViewById(R.id.buttonAdd);
        user = new User();


        firebaseAuth = FirebaseAuth.getInstance();


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
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long phn = Long.parseLong(aadharcardnumber.getText().toString().trim());
                Long aadh = Long.parseLong(aadharnumber.getText().toString().trim());
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

                    user.setAadharcardnumber(phn);

                user.setAadharnumber(aadh);
               // reff.child(String.valueOf(maxid+1)).setValue(user);
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                reff.child(user1.getUid()).setValue(user);
                Toast.makeText(UserDetailsActivity.this,"Data inserted ",Toast.LENGTH_LONG).show();
                login12();

            }
        });
    }
    public void login12(){
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }
}
