package com.example.engo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class addevent extends AppCompatActivity {


    CalendarView sdate , edate;
    TimePicker tp;
    EditText name , sponser;
    String nid;
    Button btn;
    String startdate , enddate , stime ,uid;
    ImageView epic;
    String encodedImageString;
    byte b[];
    User user;
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);



        User user = SharedPrefManager.getInstance(this).getUser();
        uid = user.getU_id();
        sdate = findViewById(R.id.jdate);
        edate = findViewById(R.id.ldate);
        tp = findViewById(R.id.tpicker);
        name = findViewById(R.id.etEname);
        sponser = findViewById(R.id.etsname);
        btn = findViewById(R.id.btReg);
        epic = findViewById(R.id.imageviewprofile);

        epic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPictureDialog();
            }
        });

        sdate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                startdate = i + "-" + i1 + "-" + i2;

            }
        });
        edate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                enddate = i + "-" + i1 + "-" + i2;

            }
        });

        stime = tp.getCurrentHour()+":"+tp.getCurrentMinute()+":00";

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addevent();


            }
        });

    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        Log.e("hello","log here");
        Toast.makeText(addevent.this, "inmage Failed!", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(addevent.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    epic.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    b = baos.toByteArray();
                    encodedImageString = Base64.encodeToString(b, Base64.DEFAULT);

                    byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
                    Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                            bytarray.length);






                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(addevent.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            epic.setImageBitmap(thumbnail);
            //saveImage(thumbnail);

            //image to sting
            Toast.makeText(addevent.this, "Image Saved!", Toast.LENGTH_SHORT).show();
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



    public  void addevent()
    {
        final String ename = name.getText().toString();
        final String sname = sponser.getText().toString();
        stime = tp.getCurrentHour()+":"+tp.getCurrentMinute()+":00";

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(s!=null) {
                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);

                        //if no error in response
                        if (obj.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            // Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(addevent.this, "Null Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();



                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("u_id",uid);
                params.put("e_name",ename);
                params.put("e_time",stime);
                params.put("e_pic",encodedImageString);
                params.put("e_startdate",startdate);
                params.put("e_enddate",enddate);
                params.put("e_sponser",sname);

                Log.e("s",uid+ename+stime+startdate+enddate+sname);
                Log.e("sadsd",encodedImageString);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ADDEVENT, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




    }
}
