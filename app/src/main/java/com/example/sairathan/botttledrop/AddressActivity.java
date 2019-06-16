package com.example.sairathan.botttledrop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddressActivity extends AppCompatActivity {
    private TextView t,addressArea;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,reff;
    private Button button,sendDetails;
     EditText editText,home;

    Address adress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        t = findViewById(R.id.addresspane);
        editText = (EditText) findViewById(R.id.addresstype);

        button = findViewById(R.id.UpdateAddress);
        sendDetails=findViewById(R.id.sendDetailsButton);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        final DatabaseReference address= databaseReference.child("User").child(user.getUid()).child("position");
        final DatabaseReference address1 = databaseReference.child("User").child(user.getUid()).child("home");
        final DatabaseReference address2 = databaseReference.child("User").child(user.getUid()).child("office");
      //  DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
       // mDatabase.child(editText.getText().toString().trim()).setValue(address);
      //  databaseReference.child("User").child(user.getUid()).child(editText.toString().trim()).setValue(address);
      //  final DatabaseReference before = databaseReference.child("User").child(user.getUid()).child("");

        reff = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
        address.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                t.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = "home";
                String check1 = "office";
                final String message = editText.getText().toString().trim();
                //databaseReference.child(address.toString()).setValue(editText.toString());
                if(message.isEmpty())
                {
                    editText.setError(getString(R.string.input_error_name));
                    editText.requestFocus();
                    return;
                }
                else {
                    if (message.toLowerCase().equals(check)) {
                        address1.setValue(address.getKey());
                        //login();
                        Toast.makeText(getApplicationContext(), " added Home Details", Toast.LENGTH_SHORT).show();
                    }
                    if (message.toLowerCase().equals(check1)) {
                        address2.setValue(address.getKey());
                        //  login();
                        Toast.makeText(getApplicationContext(), " added office details", Toast.LENGTH_SHORT).show();
                    }

                    else {

                            editText.setError(getString(R.string.input_error_name));
                            editText.requestFocus();
                            return;
                          //  Toast.makeText(getApplicationContext(), " Need home or office Details", Toast.LENGTH_SHORT).show();


                    }
                }

            }
        });
        sendDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             login();

            }
        });
    }

    private void login(){
        Intent intent = new Intent(this,Services.class);
        startActivity(intent);
    }
}
