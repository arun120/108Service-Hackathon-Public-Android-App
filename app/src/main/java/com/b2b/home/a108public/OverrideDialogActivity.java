package com.b2b.home.a108public;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OverrideDialogActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_override_dialog);
        String caseid=getIntent().getExtras().getString("Caseid");
        mProgressDialog=new ProgressDialog(this);
        Button buttonsame= (Button) findViewById(R.id.button3);

        Button buttonnotsame= (Button) findViewById(R.id.button5);

        Button viewImage= (Button) findViewById(R.id.viewImage);

        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" +Environment.getExternalStorageDirectory().getAbsolutePath()+"/Image.jpg"), "image/*");
                startActivity(intent);

            }
        });

        buttonnotsame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        buttonsame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new AsyncTask<String,Void,Customer_Case>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                mProgressDialog.setMessage("Similar Issue Detected .....");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                  mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(Customer_Case c) {
                super.onPostExecute(c);
                  mProgressDialog.dismiss();
                TextView des= (TextView) findViewById(R.id.desc);
                des.setText(c.getDescription());
                ImageView image= (ImageView) findViewById(R.id.imageView);


                    Bitmap bMap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Image.jpg");
                    //image.setImageBitmap(bMap);
            }

            @Override
            protected Customer_Case doInBackground(String... params) {
                Log.i("Case add","Db called"+params[0]);


                return Database.getCase(params[0]);

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,caseid);

    }
}
