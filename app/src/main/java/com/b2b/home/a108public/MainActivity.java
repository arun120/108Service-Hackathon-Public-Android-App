package com.b2b.home.a108public;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.skyfishjy.library.RippleBackground;

import io.codetail.animation.ViewAnimationUtils;

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
        String type="fontb.otf";
        final Typeface typeface=Typeface.createFromAsset(getAssets(),type);
        YoYo.with(Techniques.Landing).duration(1500).playOn(findViewById(R.id.logo));
        final RippleBackground rippleBackground= (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        final Boolean bol=rippleBackground.isRippleAnimationRunning();
        if(bol==true){
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rippleBackground.stopRippleAnimation();
                }
            },2500);
        }
        final TextView v1,v2;
        v1= (TextView) findViewById(R.id.textView5);
        v2= (TextView) findViewById(R.id.usrs);
        final RelativeLayout rl= (RelativeLayout) findViewById(R.id.rel);

        if( sharedPreferences.getInt("user",0)!=0){
            //  Toast.makeText(getApplicationContext(),"Proceed to home",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,HomePage.class);
            i.putExtra("name",sharedPreferences.getString("name",""));
            startActivity(i);

        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rippleBackground.startRippleAnimation();
                Boolean bols=rippleBackground.isRippleAnimationRunning();
                if(bols==true){
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rippleBackground.stopRippleAnimation();
                        }
                    },1500);
                }
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
                            c.setCust_id(id);

                        /*    edit.putInt("user",id);
                            edit.putString("name","abishek");
                            edit.commit();
                            // Toast.makeText(getApplicationContext(),String.valueOf(c.getCust_id()),Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),HomePage.class);
                            i.putExtra("name","abishek");
                            startActivity(i);*/
                            return;
                        }
                        c.setCust_id(id);
                        YoYo.with(Techniques.SlideOutUp).duration(1000).playOn(findViewById(R.id.rel));
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rl.setVisibility(View.INVISIBLE);
                                v1.setTypeface(typeface);
                                v2.setTypeface(typeface);
                                v1.setVisibility(View.VISIBLE);
                                v2.setVisibility(View.VISIBLE);
                                v2.setText(c.getName());
                                YoYo.with(Techniques.BounceIn).duration(1500).playOn(findViewById(R.id.textView5));
                                YoYo.with(Techniques.BounceIn).duration(1500).playOn(findViewById(R.id.usrs));
                            }
                        },900);


                        edit.putInt("user",id);
                        edit.putString("name",c.getName());
                        edit.commit();
                        // Toast.makeText(getApplicationContext(),String.valueOf(c.getCust_id()),Toast.LENGTH_SHORT).show();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i=new Intent(getApplicationContext(),HomePage.class);
                                i.putExtra("name",c.getName());
                                startActivity(i);
                            }
                        },4000);

                    }

                    @Override
                    protected Integer doInBackground(Customer_Details... params) {
                        return Database.registerPublic(params[0]);

                    }
                }.execute(c);



            }
        });
    }
//
//    public  void hand(int i){
//        View myView=findViewById(R.id.reveal);
//        int cx = (myView.getLeft() + myView.getRight()) / 2;
//        int cy = (myView.getTop() + myView.getBottom()) / 2;
//
//        // get the final radius for the clipping circle
//        int dx = Math.max(cx, myView.getWidth() - cx);
//        int dy = Math.max(cy, myView.getHeight() - cy);
//        float finalRadius = (float) Math.hypot(dx, dy);
//
//        // Android native animator
//        Animator animator =
//                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.setDuration(i);
//        animator.start();
//    }
}
