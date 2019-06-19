package com.example.sairathan.botttledrop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Services extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Bundle bundle = getIntent().getExtras();
        String selection = bundle.getString("Selection");
        Toast.makeText(Services.this, selection, Toast.LENGTH_SHORT).show();
    }
}
