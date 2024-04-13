package com.example.myapplication.acti;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.comm.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {
    private DatabaseHelper myDB;
    public ListView movieList;
    private static ArrayList<String> bunchOfMovies;
    private static ArrayAdapter<String> arrayAdapter;
    private static ArrayList<String> movieID;
    private String actii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        myDB = new DatabaseHelper(this);
        movieList = findViewById(R.id.listOfFav);
        actii = getIntent().getStringExtra("acti");
        bunchOfMovies = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bunchOfMovies);
        movieID = new ArrayList<>();

        if (actii.equalsIgnoreCase("movie")) {

            Cursor cursor = myDB.getAllData();
            if (cursor.getCount() == 0) {
                return;
            }
            StringBuffer buffer;
            int i = 0;
            while (cursor.moveToNext()) {
                buffer = new StringBuffer();
                buffer.append("ID: " + cursor.getString(0) + "\n");
                buffer.append("Movie Title: " + cursor.getString(1) + "\n");
                buffer.append("Year: " + cursor.getString(2) + "\n");
                buffer.append("Plot: " + cursor.getString(3) + "\n");
                String temp = buffer.toString();
                bunchOfMovies.add(i, temp);
                movieID.add(i, cursor.getString(0));
                i++;
            }
        } else if (actii.equalsIgnoreCase("pixel")) {

            Cursor cursor = myDB.getAllData2();
            if (cursor.getCount() == 0) {
                return;
            }
            StringBuffer buffer;
            int i = 0;
            while (cursor.moveToNext()) {
                buffer = new StringBuffer();
                buffer.append("ID: " + cursor.getString(0) + "\n");
                buffer.append("Photographer: " + cursor.getString(1) + "\n");
                buffer.append("Height: " + cursor.getString(2) + "\n");
                buffer.append("Weight: " + cursor.getString(3) + "\n");
                String temp = buffer.toString();
                bunchOfMovies.add(i, temp);
                movieID.add(i, cursor.getString(0));
                i++;
            }

        }
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetails.this);
                builder.setMessage("Do you want to delete the movie?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Integer deleteRow = 0;
                                if (actii.equalsIgnoreCase("movie")) {
                                    deleteRow = myDB.deleteData(movieID.get(position));
                                } else if (actii.equalsIgnoreCase("pixel")) {
                                    deleteRow = myDB.deleteData1(movieID.get(position));
                                }
                                if (deleteRow > 0) {
                                    Snackbar.make(view, "The row was successfully deleted", Snackbar.LENGTH_LONG).show();
                                    arrayAdapter.notifyDataSetChanged();
                                } else {
                                    Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
                                }
                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        }).show();
                // Create the AlertDialog object and return it

            }
        });

        if (arrayAdapter != null) {
            movieList.setAdapter(arrayAdapter);
        }

    }
}
