package com.example.engo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class viewv extends AppCompatActivity {


    TextView tname , tphone , tjdate , tldate , tdesc ;
    String uname , uphone , jdate , ldate , desc , uid ,s ;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewv);


        tname = findViewById(R.id.uname);
        tphone = findViewById(R.id.uphone);
        tjdate = findViewById(R.id.jdate);
        tldate = findViewById(R.id.ldate);
        tdesc = findViewById(R.id.desc);
        btn = findViewById(R.id.addp);

        Intent i = getIntent();

        uname = i.getStringExtra("uname");
        uphone = i.getStringExtra("uphone");
        jdate = i.getStringExtra("jdate");
        ldate = i.getStringExtra("ldate");
        desc = i.getStringExtra("desc");
        uid = i.getStringExtra("uid");
        s = i.getStringExtra("s");

        if(s.equals("1"))
        {
            btn.setVisibility(View.INVISIBLE);
        }


        tname.setText(uname);
        tphone.setText(uphone);
        tjdate.setText(jdate);
        tldate.setText(ldate);
        tdesc.setText(desc);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addp();
            }
        });

    }


    public void addp(){


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
                params.put("u_id",uid);
                return requestHandler.sendPostRequest(URLs.URL_ADDV, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();







    }


}
