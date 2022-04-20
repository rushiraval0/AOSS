package com.example.engo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class donation extends AppCompatActivity {



    EditText amount , cvv , card , vd;
    Button btn;
    String nid , uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        User user = SharedPrefManager.getInstance(this).getUser();
        uid = user.getU_id();
        amount = findViewById(R.id.etdesc);
        card = findViewById(R.id.etcard);
        cvv = findViewById(R.id.etcvv);
        vd = findViewById(R.id.etd);
        btn = findViewById(R.id.btpay);

        Intent i = getIntent();
        nid = i.getStringExtra("ngoid");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pay();
            }
        });
    }

    public  void pay()
    {
        boolean flag = false;
        final String amt = amount.getText().toString();
        final String crd = card.getText().toString();
        final String cv = cvv.getText().toString();
        final String vdd = vd.getText().toString();

        if(cv.length()!=3)
        {
            flag=true;
            cvv.setError("Enter Valid Number of 3 digits");
        }

        if(crd.length()!= 12)
        {
            flag=true;
            card.setError("Enter Valid Number of 12 digits");
        }

        if(vdd.length() > 5)
        {
            flag=true;
            card.setError("Enter Valid Date");
        }





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
                params.put("ngo_id",nid);
                params.put("u_id",uid);
                params.put("amount",amt);




                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_DONATE, params);
            }
        }

        if(!flag) {
            UserLogin ul = new UserLogin();
            ul.execute();


        }

    }


}
