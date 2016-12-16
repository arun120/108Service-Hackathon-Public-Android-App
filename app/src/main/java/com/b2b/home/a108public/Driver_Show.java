package com.b2b.home.a108public;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Driver_Show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__show);
        TextView type=(TextView) findViewById(R.id.type);
        type.setText(DriverView.c.getType());
        TextView name=(TextView) findViewById(R.id.driver_name);
        name.setText(DriverMap.driver_details.getName());
        TextView mobile=(TextView) findViewById(R.id.mobileshow);
        mobile.setText(DriverMap.driver_details.getMobile());
        TextView driverid=(TextView) findViewById(R.id.driverid);
        driverid.setText("DRI"+DriverMap.driver_details.getDriver_id());

    }
}
