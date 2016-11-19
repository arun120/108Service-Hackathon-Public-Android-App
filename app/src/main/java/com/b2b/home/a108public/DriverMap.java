package com.b2b.home.a108public;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_driver_map, container, false);


    }

    @Override
    public void onStart() {
        super.onStart();
        mProgressDialog=new ProgressDialog(getActivity());
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

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
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
        new AsyncTask<Void,Void,String>(){

            @Override
            protected void onPostExecute(String driver) {
                super.onPostExecute(driver);

                tracklocation(driver);
            }

            @Override
            protected String doInBackground(Void... params) {
                String driver=Database.findDriver(DriverView.c);
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

        Toast.makeText(getActivity(),"Tracking Started",Toast.LENGTH_SHORT).show();

            new AsyncTask<Void, Void, Driver_Location>() {

                @Override
                protected void onPostExecute(Driver_Location loc) {
                    super.onPostExecute(loc);
                    if (loc != null) {
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(loc.getLatitude()), Double.valueOf(loc.getLongitude()))));

                        Log.i("Driver Loc", "Marked");

                    }
                    tracklocation(driverid);
                }

                @Override
                protected Driver_Location doInBackground(Void... params) {
                    String driver = Database.findDriver(DriverView.c);


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
                mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(Integer id) {
                super.onPostExecute(id);
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(),DriverView.c.getCase_id(),Toast.LENGTH_SHORT).show();



            }

            @Override
            protected Integer doInBackground(Customer_Case... params) {
                String caseid=Database.addCase(params[0]);
                DriverView.c.setCase_id(caseid);

                return 0;

            }
        }.execute(DriverView.c);


        return false;
    }

}