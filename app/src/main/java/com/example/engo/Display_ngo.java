package com.example.engo;

import android.content.Context;
import android.content.Intent;
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

public class Display_ngo extends AppCompatActivity {

    List<NgoModel> list;
    RecyclerView recyclerView;
    String role;
    RecyclerAdapter radapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ngo);

        Intent i = getIntent();
        role = i.getStringExtra("role");
        recyclerView=findViewById(R.id.recycleview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        list=new ArrayList<>();


        radapter = new Display_ngo.RecyclerAdapter(getAllData(),this);
        recyclerView.setAdapter(radapter);

    }


    private List<NgoModel> getAllData()
    {

        final List<NgoModel> data=new ArrayList<>();

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


                        JSONArray jsonArray=obj.getJSONArray("ngos");

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            String r_id=jsonObject1.getString("ngo_id").trim();
                            String rname=jsonObject1.getString("ngoname");
                            String radd=jsonObject1.getString("address");
                            String rtype=jsonObject1.getString("type");


                            NgoModel current = new NgoModel();
                            current.ngo_id = r_id;
                            current.ngo_name = rname;
                            current.address = radd;
                            current.type = rtype;
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
                params.put("catid","hh");


                if(role.equals("one"))
                {
                    return requestHandler.sendPostRequest(URLs.URL_NGOBYADMIN, params);
                }
                else{
                    return requestHandler.sendPostRequest(URLs.URL_NGO, params);
                }
                //returing the response

            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




        return data;
    }


    private class RecyclerAdapter extends RecyclerView.Adapter<Display_ngo.RecyclerAdapter.MyviewHolder> {


        List<NgoModel> list;
        Context context;

        public RecyclerAdapter(List<NgoModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public Display_ngo.RecyclerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view,parent,false);
            return new Display_ngo.RecyclerAdapter.MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Display_ngo.RecyclerAdapter.MyviewHolder holder, final int position) {

            NgoModel current = list.get(position);
            final String nname = current.getNgo_name();
            final String ntype = current.getType();
            final String add = current.getAddress();
            final String nid = current.getNgo_id();





            Log.e("sdcdcdsdffr",ntype);
            holder.name.setText("Name: "+current.getNgo_name().toUpperCase());
            holder.number.setText("Type: "+current.getType());
            holder.emailid.setText("Address: "+current.getAddress());



            holder.cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(role.equals("one"))
                    {
                        Intent intent = new Intent(Display_ngo.this, viewngo.class);
                        intent.putExtra("ngoid", nid);
                        intent.putExtra("name",nname);
                        intent.putExtra("add",add);
                        intent.putExtra("ntype",ntype);
                        startActivity(intent);

                    }
                    else if(role.equals("three"))
                    {
                        Intent intent = new Intent(Display_ngo.this, add_vol.class);
                        intent.putExtra("ngoid", nid);
                        startActivity(intent);
                    }
                    else if(role.equals("four"))
                    {
                        Intent intent = new Intent(Display_ngo.this,donation.class);
                        intent.putExtra("ngoid", nid);
                        startActivity(intent);
                    }
                    else
                    {

                    }

                }
            });

            holder.cardView1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context,list.get(position).getType(),Toast.LENGTH_SHORT).show();

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





}
