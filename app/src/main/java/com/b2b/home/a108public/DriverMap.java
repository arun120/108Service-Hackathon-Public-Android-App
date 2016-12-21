package com.b2b.home.a108public;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ThreadPoolExecutor;

import de.hdodenhof.circleimageview.CircleImageView;


public class DriverMap extends android.support.v4.app.Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ProgressDialog mProgressDialog;
    private FragmentActivity myContext;
    GoogleMap mMap;

    String lat;
    String lon;
    boolean pushed;
    boolean override=false;
    CircleImageView driver;
    RelativeLayout rl1,rl2;
    public static Driver_Details driver_details;

    // TODO: Rename and change types of parameters
    // private String mParam1;
    //private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notes_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriverMap newInstance(String param1, String param2) {
        DriverMap fragment = new DriverMap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DriverMap() {
        // Required empty public constructor
        pushed=false;

    }


    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_driver_map, container, false);


    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mProgressDialog=new ProgressDialog(getActivity());
        String type="fontl.otf";

        rl1= (RelativeLayout) getActivity().findViewById(R.id.search);
        rl2= (RelativeLayout) getActivity().findViewById(R.id.show_map);
        final Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),type);
        TextView txt= (TextView) getActivity().findViewById(R.id.textView8);
        txt.setTypeface(typeface);
        RelativeLayout rl= (RelativeLayout) getActivity().findViewById(R.id.more_driver_details);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Driver_Show.class);
                startActivity(i);
            }
        });
        driver= (CircleImageView) getActivity().findViewById(R.id.search_driver);
        ImageView image1= (ImageView) getActivity().findViewById(R.id.search_rot);
        Animation anima= AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        anima.setInterpolator(new LinearInterpolator());
        image1.startAnimation(anima);
        Handler handler=new Handler();

       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                driver.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomIn).duration(600).playOn(driver);
            }
        },18500);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },20000);*/
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(myContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                if(pushed==false) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 20));
                }
                lat=String.valueOf(location.getLatitude());
                lon=String.valueOf(location.getLongitude());
                DriverView.c.setLatitude(lat);
                DriverView.c.setLongitude(lon);
                if(pushed==false){
                    pushed=true;
                    push();
                    searchdriver();

                }

            }
        });


    }

    void searchdriver(){
        new AsyncTask<Void,Void,Driver_Details>(){

            @Override
            protected void onPostExecute(Driver_Details driver1) {
                super.onPostExecute(driver1);
                driver_details=driver1;
                TextView drivername= (TextView) getActivity().findViewById(R.id.map_driver_name);
                drivername.setText(driver1.getName());
                driver.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomIn).duration(600).playOn(driver);
                tracklocation(driver1.getDriver_id());



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rl1.setVisibility(View.GONE);
                        rl2.setVisibility(View.VISIBLE);
                    }
                },2000);
            }

            @Override
            protected Driver_Details doInBackground(Void... params) {
                Driver_Details driver=Database.findDriver(DriverView.c);
                while(driver==null){
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("Driver","searchAgain");
                    driver=Database.findDriver(DriverView.c);
                }
                return driver;
            }
        }.execute();
        return ;
    }


    void tracklocation(final String driverid){

        Toast.makeText(getActivity(),"Tracking ...",Toast.LENGTH_SHORT).show();

            new AsyncTask<Void, Void, Driver_Location>() {

                @Override
                protected void onPostExecute(Driver_Location loc) {
                    super.onPostExecute(loc);
                    if (loc != null) {
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(loc.getLatitude()), Double.valueOf(loc.getLongitude()))).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance_car)));

                        Log.i("Driver Loc", "Marked");

                    }
                    tracklocation(driverid);
                }

                @Override
                protected Driver_Location doInBackground(Void... params) {
                    //String driver = Database.findDriver(DriverView.c);


                    try {
                        Thread.sleep(5000);
                        return Database.driverLocation(driverid);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);



        }


    boolean push(){
        new AsyncTask<Customer_Case,Void,Integer>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                mProgressDialog.setMessage("Loading .....");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
              //  mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(Integer id) {
                super.onPostExecute(id);
              //  mProgressDialog.dismiss();
                if(id==0) {
                    Toast.makeText(getActivity(), DriverView.c.getCase_id(), Toast.LENGTH_SHORT).show();
                }else{
                    //Handle override
                    Log.i("Similar","Detected");
                    Intent i=new Intent(getContext(),OverrideDialogActivity.class);
                    i.putExtra("Caseid",String.valueOf(id));
                    startActivity(i);
                    override=true;
                    push();

                }


            }

            @Override
            protected Integer doInBackground(Customer_Case... params) {
                Log.i("Case add","Db called");
                //override issimilar
                if(!override) {
                    String res=Database.isSimilar(params[0]);
                    if(!res.equals("false"))
                    return Integer.valueOf(res);
                }
                String caseid=Database.addCase(params[0]);
                DriverView.c.setCase_id(caseid);

                return 0;

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DriverView.c);


        return false;
    }

}