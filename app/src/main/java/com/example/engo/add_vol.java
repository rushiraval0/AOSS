package com.example.engo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class add_vol extends AppCompatActivity {


    CalendarView jdate , ldate;
    EditText desc;
    Button btn;
    String uid , ngoid, jndate , lvdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vol);

        jdate = findViewById(R.id.jdate);
        ldate = findViewById(R.id.ldate);
        desc = findViewById(R.id.etdesc);
        btn = findViewById(R.id.btsub);

        User user = SharedPrefManager.getInstance(this).getUser();
        uid = user.getU_id();

        Intent i = getIntent();
        ngoid = i.getStringExtra("ngoid");
        Log.e("sa",ngoid);



        jdate.setDate(jdate.getDate());
        ldate.setDate(ldate.getDate());
        jndate = String.valueOf(jdate.getDate());
        lvdate = String.valueOf(ldate.getDate());

        Log.e("sadad",jndate);

        jdate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                jndate = i + "-" + i1 + "-" + i2;

            }
        });
        ldate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                lvdate = i + "-" + i1 + "-" + i2;

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enroll();
            }
        });



    }


    public  void enroll()
    {
        final String d = desc.getText().toString();

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
                params.put("join_date",jndate);
                params.put("leave_date",lvdate);
                params.put("description",d);
                params.put("ngo_id",ngoid);

                Log.e("dsfd",uid+jndate+lvdate+d+ngoid);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ADD, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




    }
}
