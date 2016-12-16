package com.b2b.home.a108public;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.ViewAnimationUtils;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {
    String reqType;
    String reqNumber;
    GPSTracker gps;
    private GoogleMap mMap;
    Button cancelon,cancelof;
    RelativeLayout rl1,rl2,rl3;
    int TOGGLE=0;
    FloatingActionButton fab;
    Selection sel=new Selection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Toast.makeText(getApplicationContext(),getIntent().getExtras().getString("name"),Toast.LENGTH_SHORT).show();

        final Spinner num=(Spinner) findViewById(R.id.number);
        Button submit=(Button) findViewById(R.id.submit);
        Button sendsms=(Button) findViewById(R.id.sms);
        cancelon= (Button) findViewById(R.id.cancel_online);
        cancelof= (Button) findViewById(R.id.cancel_offline);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        ImageView androidImageView = (ImageView) findViewById(R.id.img);
//        Drawable drawable = androidImageView.getDrawable();
//        if (drawable instanceof Animatable) {
//            ((Animatable) drawable).start();
//            Snackbar.make(fab, "Welcome "+getIntent().getExtras().getString("name"), Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }
        cancelon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                widget(R.id.widgetlayout);
            }
        });
        cancelof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                widget(R.id.widgetlayout_offline);
            }
        });
        rl1= (RelativeLayout) findViewById(R.id.myrels1);
        rl2= (RelativeLayout) findViewById(R.id.myrels2);
        rl3= (RelativeLayout) findViewById(R.id.myrels3);

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sel.move(R.id.police_show,R.id.ambulance_show,R.id.fire_show,HomePage.this);
                reqType="Police";
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sel.move(R.id.ambulance_show,R.id.police_show,R.id.fire_show,HomePage.this);
                reqType="Ambulance";
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sel.move(R.id.fire_show,R.id.ambulance_show,R.id.police_show,HomePage.this);
                reqType="Fire";
            }
        });




        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            widget(R.id.widgetlayout);
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                widget(R.id.widgetlayout_offline);
                return true;
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
                smsManager.sendTextMessage(Dbdetails.number, null, Database.getSMSurl(c), null, null);


                Toast.makeText(getApplicationContext(),"SMS Sent",Toast.LENGTH_SHORT).show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(isNetworkAvailable(getApplicationContext())){
                Intent i=new Intent(HomePage.this,DriverView.class);

                i.putExtra("caseType",reqType);
                i.putExtra("caseNum",reqNumber);
                startActivity(i);}else{

                        widget(R.id.widgetlayout);
                        widget(R.id.widgetlayout_offline);

                    }

            }
        });


        List<String> numlist=new ArrayList<>();
        numlist.add("1-2");
        numlist.add("3-5");
        numlist.add("5-10");
        numlist.add("10+");

        ArrayAdapter<String> narrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,numlist);
        num.setAdapter(narrayAdapter);


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



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {

        } else if (id == R.id.profile) {
            Intent i=new Intent(HomePage.this,Profile.class);
            startActivity(i);
        } else if (id == R.id.history) {
            Intent i=new Intent(HomePage.this,History.class);
            startActivity(i);
        }  else if (id == R.id.share) {

        } else if (id == R.id.settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

            }
        });
    }

    public void widget(int id) {

        RelativeLayout widgetayout= (RelativeLayout) findViewById(id);

        switch (widgetayout.getVisibility()) {
            case View.GONE: {

                int cx, cy;
                RelativeLayout widgetlayout= (RelativeLayout) findViewById(id);
                widgetlayout.setVisibility(View.VISIBLE);
                cx = widgetlayout.getWidth() / 2;
                cy = widgetlayout.getHeight() / 2;
                float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)


                final Animator anim =
                        ViewAnimationUtils.createCircularReveal(widgetlayout, cx, cy, 0, finalRadius);

                        fab.setVisibility(View.GONE);

                        anim.start();


                break;

            }
            case View.VISIBLE: {
                // Do something for lollipop and above versions
                final RelativeLayout widgetlayout= (RelativeLayout) findViewById(id);

                final int cx, cy;
                cx = widgetlayout.getWidth() / 2;
                cy = widgetlayout.getHeight() / 2;
                float finalRadius = (float) Math.hypot(cx, cy);

                Animator anim =
                        ViewAnimationUtils.createCircularReveal(widgetlayout, cx, cy, finalRadius, 0);
                anim.start();
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        widgetlayout.setVisibility(View.GONE);
                        fab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                break;
            }
        }

    }
}
