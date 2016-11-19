package com.b2b.home.a108public;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class addDetails extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentActivity myContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog mProgressDialog,mProgressDialog1;
    Customer_Case  c;


    public addDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static addDetails newInstance(String param1, String param2) {
        addDetails fragment = new addDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        final EditText des=(EditText) getActivity().findViewById(R.id.description);
        Button adddesc=(Button) getActivity().findViewById(R.id.adddesc);
        Button pic=(Button) getActivity().findViewById(R.id.image);

        c=DriverView.c;
        mProgressDialog=new ProgressDialog(getActivity());
        mProgressDialog1=new ProgressDialog(getActivity());

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
              startActivityForResult(cameraIntent, 1888);

            }
        });


        adddesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setDescription(des.getText().toString());

                new AsyncTask<Customer_Case,Void,Integer>(){

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();


                        mProgressDialog.setMessage("Updating .....");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        mProgressDialog.show();
                    }

                    @Override
                    protected void onPostExecute(Integer id) {
                        super.onPostExecute(id);
                        mProgressDialog.dismiss();




                    }

                    @Override
                    protected Integer doInBackground(Customer_Case... params) {
                        return Database.updateDescription(params[0]);

                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,c);



            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_details, container, false);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Toast.makeText(getActivity(),"Image received",Toast.LENGTH_SHORT).show();

            FileOutputStream file = null;
            try {

                file = new FileOutputStream(new File(String.valueOf(Environment.getExternalStorageDirectory()+"/image.png")));
                photo.compress(Bitmap.CompressFormat.PNG, 60, file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

           // FileInputStream f=new FileInputStream();
            new AsyncTask<Void, Void, Void>(){

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();


                    mProgressDialog1.setMessage("Uploading .....");
                    mProgressDialog1.setIndeterminate(true);

                    mProgressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog1.show();
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mProgressDialog1.dismiss();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    Database.updateImage(DriverView.c.getCase_id());
                    mProgressDialog1.dismiss();
                    return null;
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }


}
