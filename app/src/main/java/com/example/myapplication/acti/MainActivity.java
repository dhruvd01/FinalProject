package com.example.myapplication.acti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toast.makeText(context, "Home Page", Toast.LENGTH_SHORT).show();
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "The Snackbar  Visible", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
        Button movieBtn = (Button) findViewById(R.id.movie_button);
        movieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Click Movies", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(context, MoviesActivity.class);
                startActivity(intent);

            }
        });

        Button startBusBtn = (Button) findViewById(R.id.pixel_button);
        startBusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Click Pixels", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(context, PixelsActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        int itemId = item.getItemId();
        if (itemId == R.id.icn_help) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Help dialog notification");
            alertDialog.setMessage("Welcome to Final Project \nAuthor: \nDhruv Dobariya");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else if (itemId == R.id.icn_movie) {
            intent = new Intent(this, MoviesActivity.class);
            this.startActivity(intent);
            // do your code
            return true;
        } else if (itemId == R.id.icn_pixel) {
            intent = new Intent(this, PixelsActivity.class);
//            this.startActivity(intent);
            // do your code
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}