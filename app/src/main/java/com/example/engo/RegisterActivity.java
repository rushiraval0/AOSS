package com.example.engo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private static final String IMAGE_DIRECTORY = "/gps";
    private int GALLERY = 1, CAMERA = 2;

    private RelativeLayout rlayout;
    private Animation animation;


    EditText _emailText,_mobileText,_nameText,_passwordText,_reEnterPasswordText;
    TextView reglink;
    String encodedImageString;
    RadioButton genderradioButton;
    RadioGroup rg;
    Spinner city,state,country;
    ArrayList<String> citylist,statelist,countrylist;
    ArrayAdapter<String> cityadapter,stateadapter,countryadapter;
    String name,email,mobile,password,reEnterPassword,cityname,statename;
    Button _signupButton,click;
    DatabaseHelper db;
    ImageView profile;
    byte b[];
    int role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        rlayout     = findViewById(R.id.rlayout);
        animation   = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        rlayout.setAnimation(animation);

        _emailText = findViewById(R.id.etEmail);
        _nameText = findViewById(R.id.etUsername);
        _mobileText = findViewById(R.id.etPhone);
        _passwordText = findViewById(R.id.etPassword);
        _reEnterPasswordText = findViewById(R.id.etRePassword);

        reglink = findViewById(R.id.tvLink);

        profile = (ImageView)findViewById(R.id.imageviewprofile);
        _signupButton = findViewById(R.id.btReg);

        rg=(RadioGroup)findViewById(R.id.rg);



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.user:
                        role = 3;
                        break;
                    case R.id.ngo:
                        role= 2;
                        break;

                }
            }
        });

        email = _emailText.getText().toString();
        name = _nameText.getText().toString();
        mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString().trim();
        reEnterPassword = _reEnterPasswordText.getText().toString().trim();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        _signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    //end of oncreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        Log.e("hello","log here");
        Toast.makeText(RegisterActivity.this, "inmage Failed!", Toast.LENGTH_SHORT).show();

        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

      public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    Toast.makeText(RegisterActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profile.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    b = baos.toByteArray();
                    encodedImageString = Base64.encodeToString(b, Base64.DEFAULT);

                    byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
                    Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                            bytarray.length);






                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profile.setImageBitmap(thumbnail);
            //saveImage(thumbnail);

            //image to sting
            Toast.makeText(RegisterActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            b = baos.toByteArray();
            encodedImageString = Base64.encodeToString(b, Base64.DEFAULT);

            //string to image
            byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
            Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                    bytarray.length);

        }
    }


    public void login() {


        if (!validate()) {
            onLoginFailed();
            return;
        }

        _signupButton.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
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
        name = _nameText.getText().toString();
        mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString().trim();
        reEnterPassword = _reEnterPasswordText.getText().toString().trim();

        class UserLogin extends AsyncTask<Void, Void, String> {


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

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
                params.put("u_email", email);
                params.put("u_password", password);
                params.put("u_name", name);
                params.put("u_profile", encodedImageString);
                params.put("u_phone",mobile);
                params.put("role", String.valueOf(role));



                Log.e("jjkj",mobile);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();






    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {


        email = _emailText.getText().toString();
        name = _nameText.getText().toString();
        mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString().trim();
        reEnterPassword = _reEnterPasswordText.getText().toString().trim();

        boolean valid = true;


        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
            Log.e("error in",name);
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
            Log.e("error in",email);
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
            Log.e("error in",mobile);
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
            Log.e("error in",password);
        } else {
            _passwordText.setError(null);
        }

        if(reEnterPassword.isEmpty() || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("password do not match");
            valid = false;
            Log.e("error in",reEnterPassword);

        }
        else
        {

            _reEnterPasswordText.setError(null);
        }
        return valid;
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }



}
