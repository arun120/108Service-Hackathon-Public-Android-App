package com.b2b.home.a108public;

import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class DriverView extends AppCompatActivity  {

    public static Customer_Case c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view);


        ViewPager mViewPager;
        mViewPager=(ViewPager) findViewById(R.id.pager);
        c=new Customer_Case();
        c.setType(getIntent().getExtras().getString("caseType"));
        c.setNo_ppl_affected(getIntent().getExtras().getString("caseNum"));
        SharedPreferences sharedPreferences;
        sharedPreferences= getSharedPreferences("User", Context.MODE_PRIVATE);
        c.setCust_id(String.valueOf(sharedPreferences.getInt("user",0)));



        MyPagerAdapter adapterViewPager;
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(),null,DriverView.this);
        mViewPager.setAdapter(adapterViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }



    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final int NUM_ITEMS = 2;
        Bundle args;
        private Context context;

        public MyPagerAdapter(FragmentManager fragmentManager, Bundle a, Context context) {

            super(fragmentManager);
            this.context=context;
            args=a;
        }

        // Returns total number of pages
        @Override public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override   public android.support.v4.app.Fragment getItem(int position) {

            android.support.v4.app.Fragment fragment;

            switch (position) {


                case 0:
                    fragment=new DriverMap();
                    break;
                case 1:
                    fragment=new addDetails();
                    break;
                default:
                    return null;
            }
            fragment.setArguments(args);

            return fragment;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {


                case 0:
                    return "Driver View";

                case 1:
                    return "Add Details";

                default:
                    return null;
            }
        }

    }


}
