package com.example.myapplication.acti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.comm.MyConnection;
import com.example.myapplication.comm.MyPrefrence;
import com.example.myapplication.comm.OkHttpClass;
import com.example.myapplication.comm.RadioModel;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class PixelsActivity extends AppCompatActivity {

    private OkHttpClass okHttpClass;
    private ProgressBar progressBar;
    private RecyclerView reclist;
    private EditText find;
    private AppCompatButton button;
    private String photonamre = "";
    private MyPrefrence myPrefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pixels);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myPrefrence = new MyPrefrence(this);

        reclist = findViewById(R.id.reclist);
        find = findViewById(R.id.find);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        okHttpClass = new OkHttpClass(this);

//api key=dFliwrXD8KLMhqxhMFvRZsrqy3w8P7l3gL15pL6SHWvXIihBePAb4Psx
        progressBar.setVisibility(View.GONE);

        photonamre = myPrefrence.getPixelname();
        if (!photonamre.equalsIgnoreCase("")) {
            find.setText(photonamre);
            new MyConnection1().execute();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!find.getText().toString().equals("")) {
                    myPrefrence.setPixelname(find.getText().toString());
                    photonamre = myPrefrence.getPixelname();
                    ;
                    new MyConnection1().execute();

                } else {
                    Toast.makeText(PixelsActivity.this, "Enter Photo name", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public class MyConnection1 extends AsyncTask<Void, Void, String> {
        String result = "";

        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                //Establishing the connection


                result = okHttpClass.myokdatanew("https://api.pexels.com/v1/search?query=" + photonamre, "dFliwrXD8KLMhqxhMFvRZsrqy3w8P7l3gL15pL6SHWvXIihBePAb4Psx");
                return null;

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            myalllist.clear();
            Log.e("mylist", "" + result);
            try {
                JSONObject json = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(json.getString("photos"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);

                    String width = object.getString("width");
                    String height = object.getString("height");
                    String photographer = object.getString("photographer");

                    JSONObject jsonObject1 = new JSONObject(object.getString("src"));
                    String imgurl = jsonObject1.getString("original");

                    RadioModel radioModel = new RadioModel();
                    radioModel.setName(photographer);
                    radioModel.setPhoto(imgurl);
                    radioModel.setHeight(height);
                    radioModel.setWidth(width);
                    myalllist.add(radioModel);
                }

                progressBar.setVisibility(View.GONE);
                reclist.setLayoutManager(new LinearLayoutManager(PixelsActivity.this));
                reclist.setAdapter(new Moreadapter(PixelsActivity.this, myalllist));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    List<RadioModel> myalllist = new ArrayList<>();

    public class Moreadapter extends RecyclerView.Adapter<Moreadapter.MyViewHolder> {

        private List<RadioModel> catemodels;
        private Context context;
        LayoutInflater inflater = null;

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name_txt;
            public ImageView img_photo;

            public MyViewHolder(View view) {
                super(view);
                img_photo = view.findViewById(R.id.img_photo);
                name_txt = view.findViewById(R.id.name_txt);
            }
        }

        public Moreadapter(Context context, List<RadioModel> modelCategories) {
            this.catemodels = modelCategories;
            this.context = context;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getItemCount() {
            return catemodels.size();
        }

        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            myViewHolder.name_txt.setText(catemodels.get(position).getName());


            Glide.with(context).load(catemodels.get(position).getPhoto()).into(myViewHolder.img_photo);

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(PixelsActivity.this, PixelDetailActivity.class)
                            .putExtra("posi", position)
                            .putExtra("phname", catemodels.get(position).getName())
                            .putExtra("photo", catemodels.get(position).getPhoto())
                            .putExtra("www", catemodels.get(position).getHeight())
                            .putExtra("hhhh", catemodels.get(position).getWidth()));
                }
            });
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(inflater.inflate(R.layout.list_pixels, viewGroup, false));
        }
    }


}