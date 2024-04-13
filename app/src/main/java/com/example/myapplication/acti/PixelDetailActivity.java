package com.example.myapplication.acti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.comm.DatabaseHelper;

import org.json.JSONException;

public class PixelDetailActivity extends AppCompatActivity {

    ImageView myimg;
    TextView name_txt, height_txt, width_txt;
    private Button addToFavorites, databaseButton;
    DatabaseHelper myDB;
    private int mypos;
    private String myimgposi, myphname, myhei, mywid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pixel_detail);

        myimg = findViewById(R.id.myimg);
        name_txt = findViewById(R.id.name_txt);
        height_txt = findViewById(R.id.height_txt);
        width_txt = findViewById(R.id.width_txt);
        addToFavorites = findViewById(R.id.addToFavorites);
        databaseButton = findViewById(R.id.databaseButton);
        myDB = new DatabaseHelper(this);

        mypos = getIntent().getIntExtra("posi", 0);
        myimgposi = getIntent().getStringExtra("photo");
        myphname = getIntent().getStringExtra("phname");
        myhei = getIntent().getStringExtra("hhhh");
        mywid = getIntent().getStringExtra("www");
        Glide.with(this).load(myimgposi).into(myimg);

        name_txt.setText("photographer: " + getIntent().getStringExtra("phname"));
        width_txt.setText("width: " + getIntent().getStringExtra("www"));
        height_txt.setText("height: " + getIntent().getStringExtra("hhhh"));

        myimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myimgposi));
                startActivity(browserIntent);
            }
        });
        addToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted;
                try {

                    isInserted = myDB.insertData1(mypos, myphname, myhei, mywid);
                    if (isInserted == true) {
                        Toast.makeText(PixelDetailActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        databaseButton.setOnClickListener(e -> {
            Intent intentDetails = new Intent(PixelDetailActivity.this, MovieDetails.class);
            intentDetails.putExtra("acti", "pixel");
            startActivity(intentDetails);
        });


    }
}