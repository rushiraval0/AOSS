package com.example.engo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class viewevent extends AppCompatActivity {


    TextView ename , nname , sdate , stime , sponser , link;
    ImageView epic;
    String nid , ngoname , evname , date , time , sponsers , imagestring , flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewevent);

        ename = findViewById(R.id.ename);
        nname = findViewById(R.id.ngoname);
        sdate = findViewById(R.id.startdate);
        stime = findViewById(R.id.stime);
        sponser = findViewById(R.id.sponser);
        epic = findViewById(R.id.eicon);
        link = findViewById(R.id.person);

        Intent i = getIntent();

        getimage();
        evname = i.getStringExtra("ename");
        ngoname = i.getStringExtra("nname");
        date = i.getStringExtra("sdate");
        time = i.getStringExtra("stime");
        sponsers = i.getStringExtra("sponser");
        flag = i.getStringExtra("role");

        if(flag.equals("2"))
        {
            link.setText("Remove Event");
        }

        nid = i.getStringExtra("nid");
        Log.e("jj",nid);
        Log.e("dsjj",ngoname);
        Log.e("jsdfj",date);
        Log.e("dfs",time);
        Log.e("fds",nid);

        date = date.substring(0,10);
        ename.setText(evname);
        nname.setText("NGO: "+ngoname);
        sdate.setText("date: "+date);
        stime.setText("Time: "+time);
        sponser.setText("Sponser: "+sponsers);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag.equals("2"))
                {
                   remove();
                }
                else {
                    Intent i = new Intent(viewevent.this, add_vol.class);
                    i.putExtra("ngoid", nid);
                    startActivity(i);
                }
            }
        });



    }

    public  void remove()
    {

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(viewevent.this,Display_data.class);
                        i.putExtra("flag","nevents");
                        startActivity(i);


                    } else {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();



                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id",nid);



                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REMOVE, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




    }

    public  void getimage()
    {

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        imagestring = obj.getString("e_pic");
                        byte[] bytarray = Base64.decode(imagestring, Base64.DEFAULT);
                        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0, bytarray.length);
                        epic.setImageBitmap(bmimage);





                    } else {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();



                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id",nid);



                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_VIEWEVEBYID, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




    }
}
