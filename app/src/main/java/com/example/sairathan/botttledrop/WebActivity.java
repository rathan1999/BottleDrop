package com.example.sairathan.botttledrop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WebActivity extends AppCompatActivity{

    private TextView t,addressArea;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Button button,sendDetails;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        addressArea=findViewById(R.id.EditAddressPane);
        spinner = findViewById(R.id.spinner);
        String[] s = {"Home","Office"};
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,s));
        button = findViewById(R.id.UpdateAddress);
        sendDetails=findViewById(R.id.sendDetailsButton);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final DatabaseReference address= databaseReference.child("User").child(user.getUid()).child("position");
        final DatabaseReference home= databaseReference.child("User").child(user.getUid()).child("home");
        final DatabaseReference office= databaseReference.child("User").child(user.getUid()).child("office");
        address.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                home.setValue(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItemPosition()==0) {
                    home.setValue(addressArea.getText().toString().trim());
                    Toast.makeText(WebActivity.this,"Home Updated",Toast.LENGTH_SHORT).show();
                }
                else{
                    office.setValue(addressArea.getText().toString().trim());
                    Toast.makeText(WebActivity.this,"Office Updated",Toast.LENGTH_SHORT).show();
                }

            }
        });
        sendDetails.setOnClickListener(new View.OnClickListener() {
            String data="";
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup=findViewById(R.id.radioGroup);
                RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                String radio= radioButton.getText().toString().trim();
                if(radio.equals("Home")){
                    login("Home");
                }
                else if(radio.equals("Office")){

                    office.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            data=dataSnapshot.getValue(String.class).trim();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    if(data.equals("")) {
                        Toast.makeText(WebActivity.this,"Office Address Not Added!!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    login("Office");
                }
            }
        });
    }
    private void login(String s){
        Intent intent = new Intent(this,Services.class);
        intent.putExtra("Selection",s);
        startActivity(intent);
    }
}
