package com.example.engo;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btRegister;
    private ImageView circle1;
    TextView tvLogin;
    Button login;
    EditText _emailText,_passwordText;
    String email,password;
    DatabaseHelper db;

    String FetchName,Fetchid;
    SharedPreferences sharedpreferences;
    Intent in;
    String encodedImageString;


    public static final String MyPREFERENCES = "MyPrefss" ;
    public static final String SName = "nameKey";
    public static final String SImage = "imageKey";
    public static final String SEmail = "emailKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btRegister  = findViewById(R.id.btRegister);
        tvLogin     = findViewById(R.id.tvLogin);
        circle1     = findViewById(R.id.circle1);
        login = findViewById(R.id.btLogin);

        _emailText = findViewById(R.id.etUsername);
        _passwordText = findViewById(R.id.etPassword);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();

            User user = SharedPrefManager.getInstance(this).getUser();

            String role = user.getU_role();

            if(role.equals("3")) {
                startActivity(new Intent(this, Dashboard.class));
                return;
            }
            else if(role.equals("2"))
            {
                startActivity(new Intent(this, ngo_dash.class));
                return;

            }
            else
            {
                startActivity(new Intent(this, admin.class));
                return;
            }
        }

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        btRegister.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v==btRegister){
            Intent a = new Intent(LoginActivity.this, RegisterActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(tvLogin,"login");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(a,activityOptions.toBundle());
        }




    }


    public void login() {


        if (!validate()) {
            onLoginFailed();
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();



        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }



    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess()
    {


        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();

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
                        // Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");
                        JSONObject userJson1 = obj.getJSONObject("userdetail");
//                        //creating a new user object
                        User user = new User(
                                userJson.getString("u_id"),
                                userJson.getString("u_email"),
                                userJson.getString("u_password"),
                                userJson.getString("u_status"),
                                userJson.getString("u_role"),
                                userJson1.getString("u_name"),
                                userJson1.getString("profile_pic"),
                                userJson1.getString("phone_no"),
                                userJson1.getString("proof"),
                                userJson1.getString("flag")


                        );

                        String role = userJson.getString("u_role");
                        //Log.e("email",userJson.getString("L_email"));
                        //Log.e("dffdg",userJson1.getString("address"));
                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);


                        if(role.equals("2")) {

                            Intent Page = new Intent(LoginActivity.this,ngo_dash.class);

                            startActivity(Page);
                        }
                        else if(role.equals("3"))
                        {
                            Intent Page = new Intent(LoginActivity.this, Dashboard.class);

                            startActivity(Page);

                        }
                        else
                        {

                            Intent Page = new Intent(LoginActivity.this, admin.class);

                            startActivity(Page);

                        }
                        //starting the profile activity


                    } else {

                        Toast.makeText(getApplicationContext(), String.valueOf(obj.getBoolean("error")), Toast.LENGTH_SHORT).show();
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
                params.put("email", email);
                params.put("password", password);

                Log.e("pass",password);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();



    }

    public  void checkdate()
    {
        String valid_until = "31/12/2020";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_until);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (System.currentTimeMillis() > strDate.getTime()) {

//            finish();
//            finishAffinity();
//            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

        }
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login.setEnabled(true);
    }

    public boolean validate() {


        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        boolean valid = true;


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
