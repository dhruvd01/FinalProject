package com.example.myapplication.acti;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.comm.DatabaseHelper;
import com.example.myapplication.comm.MyConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Movies_Info extends AppCompatActivity {
    /**
     * Variables declaration
     */
    private ImageView poster;
    private RatingBar stars;
    public TextView titleMovie;
    private TextView yearMovie;
    private TextView runtime;
    private TextView genre;
    private TextView director;
    private TextView actors;
    private TextView plot;
    private Button addToFavorites;
    public static String allMovieInformation = "";
    public DatabaseHelper myDB;
    public Button dataButton;
    String str;
    private Button fragmentTest;
    private ProgressBar myprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies__info);

        //Variables initialization
        myprogress = findViewById(R.id.myprogress);
        dataButton = findViewById(R.id.databaseButton);
        myDB = new DatabaseHelper(this);
        poster = findViewById(R.id.moviePoster);
        stars = findViewById(R.id.ratingStars);
        titleMovie = findViewById(R.id.movieName);
        yearMovie = findViewById(R.id.movieYear);
        runtime = findViewById(R.id.runtime);
        genre = findViewById(R.id.genre);
        director = findViewById(R.id.director);
        actors = findViewById(R.id.actors);
        plot = findViewById(R.id.plot);
        addToFavorites = findViewById(R.id.addToFavorites);
        str = "";
        new MyConnection1().execute();

        viewAll();
    }


    private void textViewDetails() {
        try {
            JSONObject json = new JSONObject(allMovieInformation);
            String url = json.getString("Poster");
             Glide.with(getApplicationContext()).load(url).into(poster);
            // Picasso.with(getApplicationContext()).setLoggingEnabled(true);
            fragmentTest = findViewById(R.id.fragmentTester);
            titleMovie.setText(json.getString("Title").toString());
            yearMovie.setText("Year: " + json.getString("Year").toString());
            runtime.setText("Runtime: " + json.getString("Runtime").toString());
            genre.setText("Genre: " + json.getString("Genre").toString());
            director.setText("Director: " + json.getString("Director").toString());
            actors.setText("Actors: " + json.getString("Actors").toString());
            plot.setText("Plot: " + json.getString("Plot").toString());
            float rating = Float.parseFloat(json.getString("imdbRating")) / 2.0f;
            stars.setRating(Float.parseFloat(String.format("%.1f", rating).replace(",", ".")));
            addToFavorites.setOnClickListener(e -> {
                boolean isInserted;
                try {

                    String runtime = json.getString("Runtime").replaceAll("\"", "");
                    isInserted = myDB.insertData(json.getString("imdbID"), json.getString("Title").toString(), json.getString("Year").toString(), json.getString("Plot").toString(),
                            Integer.parseInt(runtime.replace(" min", "")));
                    if (isInserted == true) {
                        Toast.makeText(Movies_Info.this, "Data inserted", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            });
            allMovieInformation = "";
            fragmentTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Movie_Fragment mf = new Movie_Fragment();
                    FragmentManager manage = getSupportFragmentManager();
                    FragmentTransaction ft = manage.beginTransaction();
                    ft.add(R.id.fragmentWithText, mf, "TRANSACTION").commit();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void viewAll() {
        dataButton.setOnClickListener(e -> {
            Intent intentDetails = new Intent(Movies_Info.this, MovieDetails.class);
            intentDetails.putExtra("acti","movie");
            startActivity(intentDetails);
        });
    }
    public class MyConnection1 extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            myprogress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                //Establishing the connection
                java.net.URL url;

                url = new URL("http://www.omdbapi.com/?" + "apiKey=" + "6c9862c2" + "&i=" + MyConnection.test);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                return reader(urlConnection);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                myprogress.setVisibility(View.GONE);
                Movies_Info.allMovieInformation = response;
                textViewDetails();

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }
    public String reader(HttpURLConnection urlConnection){
        try {
            //Building the StringBuilder from JSON
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            urlConnection.disconnect();
        }
        return null;
    }
}
