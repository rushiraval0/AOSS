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

public class viewngo extends AppCompatActivity {


    TextView nname , type ,add;
    Button btn;
    String n , t , ad , nid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewngo);

        nname = findViewById(R.id.nname);
        type = findViewById(R.id.ntype);
        add = findViewById(R.id.nadd);
        btn = findViewById(R.id.addp);

        Intent i = getIntent();

        n = i.getStringExtra("name");
        t = i.getStringExtra("ntype");
        ad = i.getStringExtra("add");
        nid = i.getStringExtra("ngoid");

        nname.setText(n);
        type.setText(t);
        add.setText(ad);

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
                params.put("nid",nid);
                return requestHandler.sendPostRequest(URLs.URL_ADDN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();







    }

}
