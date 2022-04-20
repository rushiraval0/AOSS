package com.example.engo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class managev extends AppCompatActivity {


    List<VolModel> list;
    RecyclerView recyclerView;
    VolModel eAdapter;
    RecyclerAdapter radapter;
    User user;
    String uid,catid,imagestring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managev);

        user = SharedPrefManager.getInstance(this).getUser();
        uid = String.valueOf(user.getU_id());


        recyclerView=findViewById(R.id.recycleview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        list=new ArrayList<>();

        radapter = new RecyclerAdapter(getAllData(),this);
        recyclerView.setAdapter(radapter);

    }


    private List<VolModel> getAllData()
    {

        final List<VolModel> data=new ArrayList<>();

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


                        JSONArray jsonArray=obj.getJSONArray("users");

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            String rs_id=jsonObject1.getString("s_id").trim();
                            String rname=jsonObject1.getString("u_id");
                            String rngo=jsonObject1.getString("username");
                            String rdescription=jsonObject1.getString("userphone");
                            String rimage=jsonObject1.getString("join_date");
                            String raddress=jsonObject1.getString("leave_date");
                            String rcity=jsonObject1.getString("description").trim();
                            String rstate=jsonObject1.getString("staff_status");
                            String rarea=jsonObject1.getString("ngo_id");

                            VolModel current = new VolModel();
                            current.s_id = rs_id;
                            current.u_id = rname;
                            current.user_name = rngo;
                            current.userphone = rdescription;
                            current.join_date = rimage;
                            current.leave_date = raddress;
                            current.description = rcity;
                            current.staff_status = rstate;
                            current.ngo_id = rarea;
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

                Log.e("uid",uid);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_MANAGEV, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




        return data;
    }



    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {


        List<VolModel> list;
        Context context;

        public RecyclerAdapter(List<VolModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view,parent,false);
            return new RecyclerAdapter.MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerAdapter.MyviewHolder holder, final int position) {

            VolModel current = list.get(position);
            final String uname = current.getUser_name();
            final String uphone = current.getUserphone();
            final String jdate = current.getJoin_date();
            final String ldate = current.getLeave_date();
            final String desc = current.getDescription();
            final String uid = current.getU_id();
            final String status = current.getStaff_status();

            Log.e("saasdsd",status);

            if(status.equals("1"))
            {
                holder.cardView1.setBackgroundColor(Color.GREEN);
            }




            Log.e("sdcdcdsdffr",uname);
            holder.name.setText("Name: "+current.getUser_name().toUpperCase());
            holder.number.setText("Phone: "+current.getUserphone());
            holder.emailid.setText("Description: "+current.getDescription());



            holder.cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(managev.this, viewv.class);
                    intent.putExtra("uname",uname);
                    intent.putExtra("uphone",uphone);
                    intent.putExtra("jdate",jdate);
                    intent.putExtra("ldate",ldate);
                    intent.putExtra("desc",desc);
                    intent.putExtra("uid",uid);
                    intent.putExtra("s",status);

                    startActivity(intent);

                }
            });

            holder.cardView1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context,list.get(position).getUser_name(),Toast.LENGTH_SHORT).show();
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
