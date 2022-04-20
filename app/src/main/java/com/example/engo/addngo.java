package com.example.engo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class addngo extends AppCompatActivity {


    User user;
    EditText name , type , add;
    Button btn;
    String uid;
    String flag = "insert";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addngo);

        name = findViewById(R.id.etname);
        type = findViewById(R.id.ettype);
        add = findViewById(R.id.etadd);
        btn = findViewById(R.id.btadd);

        User user = SharedPrefManager.getInstance(this).getUser();
        uid = user.getU_id();

        ngo();




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addngo();
            }
        });


    }

    public  void ngo()
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

                    Log.e("as",s);
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);



                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject ngo = obj.getJSONObject("ngos");

                        String rname = ngo.getString("ngo_name");
                        String rtype = ngo.getString("ngo_type");
                        String raddress = ngo.getString("address");


                        name.setText(rname);
                        type.setText(rtype);
                        add.setText(raddress);

                        flag = "update";




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


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_NGOBYID, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




    }

    public  void addngo()
    {
        final String nname = name.getText().toString();
        final String nadd = add.getText().toString();
        final String ntype = type.getText().toString();

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

                    Log.e("as",s);
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
                params.put("name",nname);
                params.put("add",nadd);
                params.put("type",ntype);
                params.put("flag",flag);



                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ADDNGO, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




    }
}
