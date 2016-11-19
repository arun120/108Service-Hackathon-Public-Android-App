package com.b2b.home.a108public;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    SharedPreferences sharedPreferences=null;
    SharedPreferences.Editor edit=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog = new ProgressDialog(this);
        final Customer_Details c=new Customer_Details();
        Button register=(Button) findViewById(R.id.register);
        sharedPreferences= getSharedPreferences("User", Context.MODE_PRIVATE);
        edit=sharedPreferences.edit();



       if( sharedPreferences.getInt("user",0)!=0){
         //  Toast.makeText(getApplicationContext(),"Proceed to home",Toast.LENGTH_SHORT).show();
           Intent i=new Intent(this,HomePage.class);
           i.putExtra("name",sharedPreferences.getString("name",""));
           startActivity(i);

       }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText gname=(EditText) findViewById(R.id.name);
                EditText gphone=(EditText) findViewById(R.id.phone);
                EditText gaddress=(EditText) findViewById(R.id.address);

                c.setName(gname.getText().toString());
                c.setMobile(gphone.getText().toString());
                c.setAddress(gaddress.getText().toString());
                new AsyncTask<Customer_Details,Void,Integer>(){

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();


                        mProgressDialog.setMessage("Registering .....");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        mProgressDialog.show();
                    }

                    @Override
                    protected void onPostExecute(Integer id) {
                        super.onPostExecute(id);
                        mProgressDialog.dismiss();
                        if(id==0) {
                            Toast.makeText(getApplicationContext(), "Login Failed....", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        c.setCust_id(id);

                        edit.putInt("user",id);
                        edit.putString("name",c.getName());
                        edit.commit();
                       // Toast.makeText(getApplicationContext(),String.valueOf(c.getCust_id()),Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),HomePage.class);
                        i.putExtra("name",c.getName());
                        startActivity(i);
                    }

                    @Override
                    protected Integer doInBackground(Customer_Details... params) {
                        return Database.registerPublic(params[0]);

                    }
                }.execute(c);



            }
        });
    }
}
