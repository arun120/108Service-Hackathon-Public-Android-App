package com.b2b.home.a108public;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class Edit_Profile extends AppCompatActivity {

    EditText name,mob,address,email,dob,adhar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_edit__profile);
        name= (EditText) findViewById(R.id.name_profile);
        mob= (EditText) findViewById(R.id.mob_profile);
        address= (EditText) findViewById(R.id.address_profile);
        email= (EditText) findViewById(R.id.email_profile);
        dob= (EditText) findViewById(R.id.dob_profile);
        adhar= (EditText) findViewById(R.id.adhar_profile);
    }
}
