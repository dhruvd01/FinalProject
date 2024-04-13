package com.example.myapplication.acti;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.comm.MyConnection;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.help) {
            AlertDialog alertDialog = new AlertDialog.Builder(MoviesActivity.this).create();
            alertDialog.setTitle(getString(R.string.title));
            alertDialog.setMessage(getString(R.string.author2) + "\n" +
                    getString(R.string.version));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Button button;
    private static ArrayAdapter<String> arrayAdapter;
    private static ArrayList<String> bunchOfMovies;
    private LayoutInflater inflater;
    private static ArrayList<String> movieID;
    private ListView lv;
    public static String testString = "";
    public static String jsonMovie;


    public static ArrayAdapter<String> getArrayAdapter() {
        return arrayAdapter;
    }

    public static ArrayList<String> getBunchOfMovies() {
        return bunchOfMovies;
    }

    public static ArrayList<String> getMovieID() {
        return movieID;
    }


    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_main_activity);

        //Variables Initialization
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.button);
        inflater = LayoutInflater.from(this);
        bunchOfMovies = new ArrayList<>();
        movieID = new ArrayList<>();
        jsonMovie = "";

        MyConnection.myActivity = this;


        //Fragment Example
        FrameLayout frameLayout = findViewById(R.id.fragmentForSomeText);
        View fragment = inflater.inflate(R.layout.list, frameLayout, false);
        TextView exampleFragmentText = findViewById(R.id.fragmentText_example);
        exampleFragmentText.setText("Some text");
        frameLayout.addView(fragment);

        button.setOnClickListener(e -> {
            bunchOfMovies.clear();
            movieID.clear();
            new MyConnection().execute();
        });

        //Working with ListView
        lv = findViewById(R.id.movieList);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_items, bunchOfMovies) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(R.id.txtdata);


                // Generate ListView Item using TextView
                return view;
            }
        };
        lv.setAdapter(arrayAdapter);

        //Handling event when clicking the item from the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MoviesActivity.this, "" + bunchOfMovies.get(position), Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(MoviesActivity.this, Movies_Info.class);
                intent.putExtra("ID", movieID.get(position));
                MyConnection.test = movieID.get(position);
                startActivity(intent);


            }
        });

    }

}



