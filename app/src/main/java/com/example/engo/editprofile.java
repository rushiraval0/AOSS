package com.example.engo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class editprofile extends AppCompatActivity {



    EditText uemail , uname , uphone , upass;
    ImageView uprofile;
    String   email , name , phone , pass , uid;
    Button btn;
    String encodedImageString;
    byte b[];
    User user;
    private int GALLERY = 1, CAMERA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        User user = SharedPrefManager.getInstance(this).getUser();


        uemail = findViewById(R.id.etEmail);
        uname = findViewById(R.id.etUsername);
        uphone = findViewById(R.id.etPhone);
        upass = findViewById(R.id.etPassword);
        uprofile = findViewById(R.id.imageviewprofile);
        btn = findViewById(R.id.btu);

        uid = user.getU_id();

        uname.setText(user.getU_name());
        uphone.setText(user.getPhone_no());
        upass.setText(user.getU_password());
        uemail.setText(user.getU_email());

        encodedImageString = user.getProfile_pic();

        byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                bytarray.length);

        Bitmap nimg = getCircleBitmap(bmimage);
        uprofile.setImageBitmap(nimg);

        uprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });



  }

    public void login() {


        if (!validate()) {
            onLoginFailed();
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(editprofile.this,
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

        email = uemail.getText().toString();
        name = uname.getText().toString();
        phone = uphone.getText().toString();
        pass = upass.getText().toString().trim();


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
                        finish();
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
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
                params.put("u_password", pass);
                params.put("u_name", name);
                params.put("u_profile", encodedImageString);
                params.put("u_phone",phone);
                params.put("uid",uid);



                Log.e("jjkj",phone);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_PROFILE, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();






    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();


    }

    public boolean validate() {


        email = uemail.getText().toString();
        name = uname.getText().toString();
        phone = uphone.getText().toString();
        pass = upass.getText().toString().trim();

        boolean valid = true;


        if (name.isEmpty() || name.length() < 3) {
            uname.setError("at least 3 characters");
            valid = false;
            Log.e("error in",name);
        } else {
            uname.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            uemail.setError("enter a valid email address");
            valid = false;
            Log.e("error in",email);
        } else {
            uemail.setError(null);
        }

        if (phone.isEmpty() || phone.length()!=10) {
            uphone.setError("Enter Valid Mobile Number");
            valid = false;
            Log.e("error in",phone);
        } else {
            uphone.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            upass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
            Log.e("error in",pass);
        } else {
            upass.setError(null);
        }
        return valid;
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        Log.e("hello","log here");
        Toast.makeText(editprofile.this, "inmage Failed!", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(editprofile.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    uprofile.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    b = baos.toByteArray();
                    encodedImageString = Base64.encodeToString(b, Base64.DEFAULT);

                    byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
                    Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                            bytarray.length);






                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(editprofile.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            uprofile.setImageBitmap(thumbnail);
            //saveImage(thumbnail);

            //image to sting
            Toast.makeText(editprofile.this, "Image Saved!", Toast.LENGTH_SHORT).show();
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
    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap circuleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(circuleBitmap);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return circuleBitmap;
    }
}
