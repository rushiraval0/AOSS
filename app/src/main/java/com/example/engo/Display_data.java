package com.example.engo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Display_data extends AppCompatActivity {

    List<ExampleModel> list;
    RecyclerView recyclerView;
    DatabaseHelper dbAdapter;
    ExampleModel eAdapter;
    SQLiteDatabase mdatabase;
    RecyclerAdapter radapter;
    User user;
    URLs url;
    String uid,catid,imagestring , flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

         user = SharedPrefManager.getInstance(this).getUser();
         uid = String.valueOf(user.getU_id());


         Intent i = getIntent();
         flag = i.getStringExtra("flag");




        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        radapter = new RecyclerAdapter(getAllData(),this);
        recyclerView.setAdapter(radapter);



    }



    private List<ExampleModel> getAllData()
    {

        final List<ExampleModel> data=new ArrayList<>();

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

                        //getting the user from the response
                        // JSONObject userJson = obj.getJSONObject("vehicle");


                        JSONArray jsonArray=obj.getJSONArray("event");

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            String rs_id=jsonObject1.getString("e_id").trim();
                            String rname=jsonObject1.getString("ngo_id");
                            String rngo=jsonObject1.getString("ngo");
                            String rdescription=jsonObject1.getString("e_name");
                            String rimage=jsonObject1.getString("e_time");
                            String raddress=jsonObject1.getString("e_pic");
                            String rcity=jsonObject1.getString("e_startdate").trim();
                            String rstate=jsonObject1.getString("e_enddate");
                            String rarea=jsonObject1.getString("e_sponser");

                            ExampleModel current = new ExampleModel();
                            current.e_id = rs_id;
                            current.ngo_id = rname;
                            current.e_name = rdescription;
                            current.e_time = rimage;
                            current.e_pic = raddress;
                            current.e_startdate = rcity;
                            current.e_enddate = rstate;
                            current.e_sponser = rarea;
                            current.ngo_name = rngo;
                            data.add(current);
                        }


                        radapter.notifyDataSetChanged();



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
                params.put("uid",uid);


                if(flag.equals("nevents"))
                {
                    return requestHandler.sendPostRequest(URLs.URL_NGOID, params);

                }else
                {
                    return requestHandler.sendPostRequest(URLs.URL_VIEWEVENTS, params);

                }
                //returing the response


            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




        return data;
    }



    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {


        List<ExampleModel> list;
        Context context;

        public RecyclerAdapter(List<ExampleModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerAdapter.MyviewHolder holder,final int position) {

            ExampleModel current = list.get(position);
            final String evname = current.getE_name();
            final String ngoid = current.getE_id();
            final String ngo = current.getNgo_name();
            final String sponser = current.getE_sponser();
            final String date = current.getE_startdate();
            final String time = current.getE_time();




            Log.e("sdcdcdsdffr",ngo);
            holder.name.setText("Name: "+current.getE_name().toUpperCase());
            holder.number.setText("Sponser: "+current.getE_sponser());
            holder.emailid.setText("time: "+current.getE_startdate());

            imagestring = current.getE_pic();

            if(!imagestring.equals(null)) {
                byte[] bytarray = Base64.decode(imagestring, Base64.DEFAULT);
                Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                        bytarray.length);
                Bitmap nimg = getCircleBitmap(bmimage);
                holder.si.setImageBitmap(nimg);
            }

            holder.cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Display_data.this, viewevent.class);
                    intent.putExtra("ename",evname);
                    intent.putExtra("nname",ngo);
                    intent.putExtra("sdate",date);
                    intent.putExtra("stime",time);
                    intent.putExtra("sponser",sponser);
                    intent.putExtra("nid",ngoid);
                    intent.putExtra("role",user.getU_role());
                    startActivity(intent);

                }
            });

            holder.cardView1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context,list.get(position).getE_name(),Toast.LENGTH_SHORT).show();

                    return true;
                }
            });

        }



        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder {

            private TextView name , number , emailid , rat;
            CardView cardView1;
            ImageView si;


            public MyviewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name_text);
                number = itemView.findViewById(R.id.number_text);
                emailid = itemView.findViewById(R.id.email_text);
                si = itemView.findViewById(R.id.shopimage);


                cardView1=(CardView) itemView.findViewById(R.id.card_view);
            }
        }
    }


    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap circuleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(circuleBitmap);

        final int color = Color.RED;
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

    public static Bitmap cropToSquare(Bitmap bitmap){
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width)? height - ( height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0)? 0: cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0)? 0: cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
                    false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
                finalBitmap.getHeight());

        final RectF rectf = new RectF(0, 0, finalBitmap.getWidth(),
                finalBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        //Set Required Radius Here
        int yourRadius = 7;
        canvas.drawRoundRect(rectf, yourRadius, yourRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);

        return output;
    }




}
