package com.b2b.home.a108public;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    String reqType;
    String reqNumber;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toast.makeText(getApplicationContext(),getIntent().getExtras().getString("name"),Toast.LENGTH_SHORT).show();

        final Spinner type=(Spinner) findViewById(R.id.request);
        final Spinner num=(Spinner) findViewById(R.id.number);
        Button submit=(Button) findViewById(R.id.submit);
        Button sendsms=(Button) findViewById(R.id.sms);

        sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new GPSTracker(HomePage.this);



                    Customer_Case c=new Customer_Case();

               SharedPreferences sharedPreferences= getSharedPreferences("User", Context.MODE_PRIVATE);
                c.setCust_id(String.valueOf(sharedPreferences.getInt("user",0)));
                c.setType(reqType);
                c.setNo_ppl_affected(reqNumber);

                c.setLatitude(String.valueOf(gps.getLatitude()));
                c.setLongitude(String.valueOf(gps.getLongitude()));

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("1500", null, Database.getSMSurl(c), null, null);


                Toast.makeText(getApplicationContext(),"SMS Sent",Toast.LENGTH_SHORT).show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HomePage.this,DriverView.class);

                i.putExtra("caseType",reqType);
                i.putExtra("caseNum",reqNumber);
                startActivity(i);

            }
        });


        List<String> typelist=new ArrayList<>();
        typelist.add("Ambulance");
        typelist.add("Police");
        typelist.add("Fire");

        List<String> numlist=new ArrayList<>();
        numlist.add("1-2");
        numlist.add("3-5");
        numlist.add("5-10");
        numlist.add("10+");

        ArrayAdapter<String> narrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,numlist);
        num.setAdapter(narrayAdapter);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,typelist);
        type.setAdapter(arrayAdapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reqType=type.getItemAtPosition(position).toString();
                Log.i("Requested type",reqType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        num.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reqNumber=num.getItemAtPosition(position).toString();
                Log.i("Requested type",reqNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}
